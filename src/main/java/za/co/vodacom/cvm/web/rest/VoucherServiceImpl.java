package za.co.vodacom.cvm.web.rest;

import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;
import za.co.vodacom.cvm.client.wigroup.api.CouponsApiClient;
import za.co.vodacom.cvm.client.wigroup.model.CouponsDelResponse;
import za.co.vodacom.cvm.client.wigroup.model.CouponsRequest;
import za.co.vodacom.cvm.client.wigroup.model.CouponsResponse;
import za.co.vodacom.cvm.config.ApplicationProperties;
import za.co.vodacom.cvm.config.Constants;
import za.co.vodacom.cvm.domain.VPCampaign;
import za.co.vodacom.cvm.domain.VPCampaignVouchers;
import za.co.vodacom.cvm.domain.VPVoucherDef;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.exception.AllocationException;
import za.co.vodacom.cvm.exception.WiGroupException;
import za.co.vodacom.cvm.repository.VPVoucherDefRepository;
import za.co.vodacom.cvm.service.VPCampaignService;
import za.co.vodacom.cvm.service.VPCampaignVouchersService;
import za.co.vodacom.cvm.service.VPVoucherDefService;
import za.co.vodacom.cvm.service.VPVouchersService;
import za.co.vodacom.cvm.utils.MSISDNConverter;
import za.co.vodacom.cvm.utils.RSAEncryption;
import za.co.vodacom.cvm.web.api.VoucherApiDelegate;
import za.co.vodacom.cvm.web.api.model.VoucherAllocationRequest;
import za.co.vodacom.cvm.web.api.model.VoucherAllocationResponse;
import za.co.vodacom.cvm.web.api.model.VoucherReturnRequest;
import za.co.vodacom.cvm.web.api.model.VoucherReturnResponse;
import za.co.vodacom.cvm.web.rest.errors.BadRequestAlertException;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Service
public class VoucherServiceImpl implements VoucherApiDelegate {

    public static final Logger log = LoggerFactory.getLogger(VoucherServiceImpl.class);

    @Autowired
    private final VPCampaignService vpCampaignService;

    @Autowired
    private final VPCampaignVouchersService vpCampaignVouchersService;

    @Autowired
    private final VPVoucherDefService vpVoucherDefService;

    @Autowired
    private final VPVouchersService vpVouchersService;

    @Autowired
    CouponsApiClient couponsApiClient;

    @Autowired
    Tracer tracer;

    @Autowired
    VPVoucherDefRepository vpVoucherDefRepository;

    @Autowired
    RSAEncryption rsaEncryption;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    MSISDNConverter msisdnConverter;


    VoucherServiceImpl(
        VPCampaignService vpCampaignService,
        VPCampaignVouchersService vpCampaignVouchersService,
        VPVoucherDefService vpVoucherDefService,
        VPVouchersService vpVouchersService
    ) {

        this.vpCampaignService = vpCampaignService;
        this.vpCampaignVouchersService = vpCampaignVouchersService;
        this.vpVoucherDefService = vpVoucherDefService;
        this.vpVouchersService = vpVouchersService;
    }

    @Transactional
    //@HystrixCommand(fallbackMethod = "issueVoucherFallback", ignoreExceptions = AllocationException.class)
    @Override
    public ResponseEntity<VoucherAllocationResponse> issueVoucher(VoucherAllocationRequest voucherAllocationRequest) {
        log.debug(voucherAllocationRequest.toString());
        log.info(voucherAllocationRequest.toString());

        CouponsRequest couponsRequest = new CouponsRequest();

        if (voucherAllocationRequest.getMsisdn().length() == 11 && Pattern.matches(applicationProperties.getMsisdn().getExternal(), voucherAllocationRequest.getMsisdn())) {
            voucherAllocationRequest.setMsisdn(msisdnConverter.convertToInternal(voucherAllocationRequest.getMsisdn()));
            couponsRequest.setMobileNumber(voucherAllocationRequest.getMsisdn());
            couponsRequest.setUserRef(voucherAllocationRequest.getMsisdn());
        } else if (voucherAllocationRequest.getMsisdn().length() == 15 && Pattern.matches(applicationProperties.getMsisdn().getInternal(), voucherAllocationRequest.getMsisdn())) {
            couponsRequest.setMobileNumber(msisdnConverter.convertToExternal(voucherAllocationRequest.getMsisdn()));
            couponsRequest.setUserRef(msisdnConverter.convertToExternal(voucherAllocationRequest.getMsisdn()));
        } else
            throw new BadRequestAlertException("Invalid MSISDN format: " + voucherAllocationRequest.getMsisdn(), ENTITY_NAME, "11 or 15 digits required");

        VoucherAllocationResponse voucherAllocationResponse = new VoucherAllocationResponse();
        //Validate the incoming campaign
        Optional<VPCampaign> vpCampaign = vpCampaignService.findByName(voucherAllocationRequest.getCampaign());
        if (vpCampaign.isPresent()) { //campaign found
            //Validate incoming productId
            Optional<VPCampaignVouchers> vpCampaignVouchers = vpCampaignVouchersService.findByProductIdAndCampaignIdAndActiveYN(
                voucherAllocationRequest.getProductId(),
                vpCampaign.get().getId(),
                Constants.YES
            );
            if (vpCampaignVouchers.isPresent()) { //productId found
                log.debug(vpCampaignVouchers.get().toString());
                log.info(vpCampaignVouchers.get().toString());
                vpVoucherDefService
                    .findOne(voucherAllocationRequest.getProductId())
                    .ifPresent(
                        vpVoucherDef -> {
                            log.debug(vpVoucherDef.toString());
                            log.info(vpVoucherDef.toString());
                            switch (vpVoucherDef.getType()) {
                                case Constants.VOUCHER:
                                    vpVoucherDefService
                                        .findById(voucherAllocationRequest.getProductId()) //acquire a lock
                                        .ifPresent(
                                            vpVoucherDef1 -> {
                                                //get valid voucher
                                                Optional<VPVouchers> vpVouchers = vpVouchersService.getValidVoucher(
                                                    voucherAllocationRequest.getProductId()
                                                );
                                                if (vpVouchers.isPresent()) { //voucher found
                                                    VPVouchers vpVoucher = vpVouchers.get();
                                                    log.debug(vpVoucher.toString());
                                                    log.info(vpVoucher.toString());
                                                    //issue voucher
                                                    vpVouchersService.issueVoucher(voucherAllocationRequest.getTrxId(), vpVoucher.getId());

                                                    OffsetDateTime expiryDate = vpVoucher.getExpiryDate() != null
                                                        ? vpVoucher
                                                        .getExpiryDate()
                                                        .toOffsetDateTime()
                                                        .plusHours(23L)
                                                        .plusMinutes(59L)
                                                        .plusSeconds(59L)
                                                        : OffsetDateTime
                                                        .now()
                                                        .plusHours(23L)
                                                        .plusMinutes(59L)
                                                        .plusSeconds(59L)
                                                        .plusDays(vpVoucherDef.getValidityPeriod());
                                                    String voucherCode = vpVoucher.getVoucherCode();

                                                    //vpVoucherDefRepository.flush();//release lock
                                                    //set response
                                                    voucherAllocationResponse.setCollectPoint(vpVoucher.getCollectionPoint());
                                                    voucherAllocationResponse.setExpiryDate(expiryDate);
                                                    voucherAllocationResponse.setTrxId(voucherAllocationRequest.getTrxId());
                                                    voucherAllocationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                                    voucherAllocationResponse.setVoucherCode(voucherCode);
                                                    voucherAllocationResponse.setVoucherDescription(vpVoucher.getDescription());
                                                    voucherAllocationResponse.setVoucherId(vpVoucher.getId());
                                                    voucherAllocationResponse.setVoucherType(vpVoucherDef.getType());
                                                    voucherAllocationResponse.setVoucherVendor(vpVoucherDef.getVendor());
                                                    voucherAllocationResponse.setEncryptedYN(vpVoucherDef.getEncryptedYN());

                                                    log.debug(voucherAllocationResponse.toString());
                                                    log.info(voucherAllocationResponse.toString());
                                                } else { //no valid voucher
                                                    throw new AllocationException("Voucher not available", Status.NOT_FOUND);
                                                }
                                            }
                                        );

                                    break;
                                case Constants.GENERIC_VOUCHER:
                                    //get valid voucher
                                    Optional<VPVouchers> vpVouchersGen = vpVouchersService.getValidVoucher(
                                        voucherAllocationRequest.getProductId()
                                    );
                                    if (vpVouchersGen.isPresent()) { //voucher found
                                        VPVouchers vpVoucher = vpVouchersGen.get();
                                        log.debug(vpVoucher.toString());
                                        log.info(vpVoucher.toString());
                                        OffsetDateTime expiryDate = vpVoucher.getExpiryDate() != null
                                            ? vpVoucher.getExpiryDate().toOffsetDateTime().plusHours(23L).plusMinutes(59L).plusSeconds(59L)
                                            : OffsetDateTime
                                            .now()
                                            .plusHours(23L)
                                            .plusMinutes(59L)
                                            .plusSeconds(59L)
                                            .plusDays(vpVoucherDef.getValidityPeriod());

                                        String voucherCode = vpVoucher.getVoucherCode();

                                        //set response
                                        voucherAllocationResponse.setCollectPoint(vpVoucher.getCollectionPoint());
                                        voucherAllocationResponse.setExpiryDate(expiryDate);
                                        voucherAllocationResponse.setTrxId(voucherAllocationRequest.getTrxId());
                                        voucherAllocationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                        voucherAllocationResponse.setVoucherCode(voucherCode);
                                        voucherAllocationResponse.setVoucherDescription(vpVoucher.getDescription());
                                        voucherAllocationResponse.setVoucherId(vpVoucher.getId());
                                        voucherAllocationResponse.setVoucherType(vpVoucherDef.getType());
                                        voucherAllocationResponse.setVoucherVendor(vpVoucherDef.getVendor());
                                        voucherAllocationResponse.setEncryptedYN(vpVoucherDef.getEncryptedYN());

                                        log.debug(voucherAllocationResponse.toString());
                                        log.info(voucherAllocationResponse.toString());
                                    } else { //no valid voucher
                                        throw new AllocationException("Voucher not available", Status.NOT_FOUND);
                                    }
                                    break;
                                case Constants.ONLINE_VOUCHER:

                                    couponsRequest.setCampaignId(vpVoucherDef.getExtId());
                                    couponsRequest.sendSMS(false);
                                    couponsRequest.setSmsMessage("");

                                    log.info(couponsRequest.toString());
                                    //call wi group
                                    ResponseEntity<CouponsResponse> couponsResponseResponseEntity = couponsApiClient.issueVoucher(
                                        true,
                                        couponsRequest
                                    );
                                    //success
                                    CouponsResponse couponsResponse = couponsResponseResponseEntity.getBody();
                                    if (
                                        couponsResponse.getResponseCode().equals(Constants.RESPONSE_CODE) ||
                                            couponsResponse.getResponseDesc().equalsIgnoreCase(Constants.RESPONSE_DESC)
                                    ) {
                                        OffsetDateTime expiryDate = OffsetDateTime
                                            .now()
                                            .plusHours(23L)
                                            .plusMinutes(59L)
                                            .plusSeconds(59L)
                                            .plusDays(vpVoucherDef.getValidityPeriod());

                                        String voucherCode = couponsResponse.getCoupon().getWiCode() + "";
                                        try { //Encrypt code
                                            voucherCode =
                                                vpVoucherDef.getEncryptedYN() != null &&
                                                    vpVoucherDef.getEncryptedYN().equalsIgnoreCase(Constants.YES)
                                                    ? rsaEncryption.encrypt(
                                                    voucherCode,
                                                    applicationProperties.getEncryption().getKey()
                                                )
                                                    : voucherCode;
                                        } catch (Exception e) {
                                            log.error(e.getMessage());
                                        }
                                        //set response
                                        voucherAllocationResponse.setCollectPoint(vpVoucherDef.getVendor());
                                        voucherAllocationResponse.setExpiryDate(expiryDate);
                                        voucherAllocationResponse.setTrxId(voucherAllocationRequest.getTrxId());
                                        voucherAllocationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                        voucherAllocationResponse.setVoucherCode(voucherCode);
                                        voucherAllocationResponse.setVoucherDescription(vpVoucherDef.getDescription());
                                        voucherAllocationResponse.setVoucherId(couponsResponse.getCoupon().getId());
                                        voucherAllocationResponse.setVoucherType(vpVoucherDef.getType());
                                        voucherAllocationResponse.setVoucherVendor(vpVoucherDef.getVendor());
                                        voucherAllocationResponse.setEncryptedYN(vpVoucherDef.getEncryptedYN());

                                        log.debug(voucherAllocationResponse.toString());
                                        log.info(voucherAllocationResponse.toString());
                                    } else { //failed
                                        throw new WiGroupException(
                                            couponsResponseResponseEntity.getBody().getResponseDesc(),
                                            Status.INTERNAL_SERVER_ERROR
                                        );
                                    }
                                    break;
                            }
                        }
                    );
            } else { //productId not found
                throw new AllocationException("Invalid ProductId", Status.NOT_FOUND);
            }
        } else { //campaign not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(voucherAllocationResponse, HttpStatus.OK);
    }

    //retry once
    public ResponseEntity<VoucherAllocationResponse> issueVoucherFallback(VoucherAllocationRequest voucherAllocationRequest) {
        return issueVoucher(voucherAllocationRequest);
    }

    @Transactional
    //@HystrixCommand(fallbackMethod = "returnVoucherFallback", ignoreExceptions = AllocationException.class)
    @Override
    public ResponseEntity<VoucherReturnResponse> returnVoucher(Long voucherId, VoucherReturnRequest voucherReturnRequest) {
        log.debug(voucherReturnRequest.toString());
        log.info(voucherReturnRequest.toString());
        CouponsRequest couponsRequest = new CouponsRequest();
        if (voucherReturnRequest.getMsisdn().length() == 11 && Pattern.matches(applicationProperties.getMsisdn().getExternal(), voucherReturnRequest.getMsisdn())) {
            voucherReturnRequest.setMsisdn(msisdnConverter.convertToInternal(voucherReturnRequest.getMsisdn()));
            couponsRequest.setMobileNumber(voucherReturnRequest.getMsisdn());
            couponsRequest.setUserRef(voucherReturnRequest.getMsisdn());
        }
        else if (voucherReturnRequest.getMsisdn().length() == 15 && Pattern.matches(applicationProperties.getMsisdn().getInternal(), voucherReturnRequest.getMsisdn())) {
            couponsRequest.setMobileNumber(msisdnConverter.convertToExternal(voucherReturnRequest.getMsisdn()));
            couponsRequest.setUserRef(msisdnConverter.convertToExternal(voucherReturnRequest.getMsisdn()));
        }
        else
            throw new BadRequestAlertException("Invalid MSISDN format: " + voucherReturnRequest.getMsisdn(), ENTITY_NAME, "11 or 15 digits required");

        VoucherReturnResponse voucherReturnResponse = new VoucherReturnResponse();
        Optional<VPVoucherDef> vpVoucherDefOptional = vpVoucherDefService.findOne(voucherReturnRequest.getProductId());
        if (vpVoucherDefOptional.isPresent()) { //productId found
            VPVoucherDef vpVoucherDef = vpVoucherDefOptional.get();
            log.debug(vpVoucherDef.toString());
            log.info(vpVoucherDef.toString());
            switch (vpVoucherDef.getType()) {
                case Constants.VOUCHER:
                    Optional<VPVouchers> optionalVPVouchers = vpVouchersService.getValidVoucherForReturn(
                        voucherReturnRequest.getProductId(),
                        voucherId,
                        voucherReturnRequest.getTrxId()
                    );
                    if (optionalVPVouchers.isPresent()) {
                        VPVouchers vpVouchers = optionalVPVouchers.get();
                        log.debug(vpVouchers.toString());
                        log.info(vpVouchers.toString());
                        //Update voucher
                        vpVouchersService.updateReturnedVoucher(voucherId);

                        voucherReturnResponse.setTrxId(voucherReturnRequest.getTrxId());
                        voucherReturnResponse.setVoucherDescription(vpVoucherDef.getDescription());
                        voucherReturnResponse.setVoucherId(vpVouchers.getId());
                    } else {
                        log.debug("Voucher not found or expired: {}", voucherId);
                        log.info("Voucher not found or expired: {}", voucherId);
                        throw new AllocationException("Voucher not found or expired", Status.NOT_FOUND);
                    }
                    break;
                case Constants.GENERIC_VOUCHER:
                    //get valid voucher
                    //nothing to do just return
                    voucherReturnResponse.setTrxId(voucherReturnRequest.getTrxId());
                    voucherReturnResponse.setVoucherDescription(vpVoucherDef.getDescription());
                    voucherReturnResponse.setVoucherId(voucherId);
                    break;
                case Constants.ONLINE_VOUCHER:

                    couponsRequest.setCampaignId(vpVoucherDef.getExtId());
                    couponsRequest.sendSMS(false);
                    couponsRequest.setSmsMessage("");

                    log.debug(couponsRequest.toString());
                    log.info(couponsRequest.toString());
                    //call wi group
                    ResponseEntity<CouponsDelResponse> couponsResponseResponseEntity = couponsApiClient.deleteVoucher(voucherId);
                    //success
                    CouponsDelResponse couponsDelResponse = couponsResponseResponseEntity.getBody();
                    log.debug(couponsDelResponse.toString());
                    log.info(couponsDelResponse.toString());
                    if (
                        couponsDelResponse.getResponseCode().equals(Constants.RESPONSE_CODE) ||
                            couponsDelResponse.getResponseDesc().equalsIgnoreCase(Constants.RESPONSE_DESC)
                    ) {
                        //set response
                        voucherReturnResponse.setVoucherDescription(vpVoucherDef.getDescription());
                        voucherReturnResponse.setTrxId(voucherReturnRequest.getTrxId());
                        voucherReturnResponse.setVoucherId(voucherId);
                    } else { //failed
                        throw new WiGroupException(couponsResponseResponseEntity.getBody().getResponseDesc(), Status.INTERNAL_SERVER_ERROR);
                    }
                    break;
            }
        } else { //productId not found
            throw new AllocationException("Invalid ProductId", Status.NOT_FOUND);
        }

        log.debug(voucherReturnResponse.toString());
        log.info(voucherReturnResponse.toString());
        return new ResponseEntity<>(voucherReturnResponse, HttpStatus.OK);
    }

    //retry once
    public ResponseEntity<VoucherReturnResponse> returnVoucherFallback(Long voucherId, VoucherReturnRequest voucherReturnRequest) {
        return returnVoucher(voucherId, voucherReturnRequest);
    }
}
