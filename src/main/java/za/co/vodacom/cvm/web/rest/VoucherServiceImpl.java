package za.co.vodacom.cvm.web.rest;

import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.zalando.problem.Status;
import za.co.vodacom.cvm.client.GiftingApi;
import za.co.vodacom.cvm.client.gifting.GiftingApiClient;
import za.co.vodacom.cvm.client.wigroup.api.CouponsApiClient;
import za.co.vodacom.cvm.client.wigroup.model.CouponsDelResponse;
import za.co.vodacom.cvm.client.wigroup.model.CouponsRequest;
import za.co.vodacom.cvm.client.wigroup.model.CouponsResponse;
import za.co.vodacom.cvm.config.ApplicationProperties;
import za.co.vodacom.cvm.config.Constants;
import za.co.vodacom.cvm.exception.AllocationException;
import za.co.vodacom.cvm.exception.WiGroupException;
import za.co.vodacom.cvm.service.VPCampaignService;
import za.co.vodacom.cvm.service.VPCampaignVouchersService;
import za.co.vodacom.cvm.service.VPVoucherDefService;
import za.co.vodacom.cvm.service.VPVouchersService;
import za.co.vodacom.cvm.service.dto.VPCampaignDTO;
import za.co.vodacom.cvm.service.dto.VPCampaignVouchersDTO;
import za.co.vodacom.cvm.service.dto.VPVoucherDefDTO;
import za.co.vodacom.cvm.service.dto.VPVouchersDTO;
import za.co.vodacom.cvm.service.dto.gifting.GiftingVoucherRequestDTO;
import za.co.vodacom.cvm.service.dto.gifting.GiftingVoucherResponseDTO;
import za.co.vodacom.cvm.utils.DateConverter;
import za.co.vodacom.cvm.utils.MSISDNConverter;
import za.co.vodacom.cvm.utils.RSAEncryption;
import za.co.vodacom.cvm.web.api.VoucherApiDelegate;
import za.co.vodacom.cvm.web.api.model.VoucherAllocationRequest;
import za.co.vodacom.cvm.web.api.model.VoucherAllocationResponse;
import za.co.vodacom.cvm.web.api.model.VoucherReturnRequest;
import za.co.vodacom.cvm.web.api.model.VoucherReturnResponse;
import za.co.vodacom.cvm.web.rest.errors.BadRequestAlertException;

import java.time.OffsetDateTime;
import java.util.List;
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
    GiftingApiClient giftingApiClient;

    @Autowired
    Tracer tracer;

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
        Optional<VPCampaignDTO> vpCampaignDTO = vpCampaignService.findByName(voucherAllocationRequest.getCampaign());
        if (vpCampaignDTO.isPresent()) { //campaign found
            //Validate incoming productId
            Optional<VPCampaignVouchersDTO> vpCampaignVouchersDTO = vpCampaignVouchersService.findByProductIdAndCampaignIdAndActiveYN(
                voucherAllocationRequest.getProductId(),
                vpCampaignDTO.get().getId(),
                Constants.YES
            );
            if (vpCampaignVouchersDTO.isPresent()) { //productId found
                log.debug(vpCampaignVouchersDTO.get().toString());
                vpVoucherDefService
                    .findOne(voucherAllocationRequest.getProductId())
                    .ifPresent(
                        vpVoucherDef -> {
                            log.debug(vpVoucherDef.toString());
                            switch (vpVoucherDef.getType()) {
                                case Constants.VOUCHER:
                                    if (applicationProperties.isUseGiftingVouchers()){
                                        GiftingVoucherResponseDTO giftingVoucher = getVoucherFromGifting(voucherAllocationRequest.getMsisdn(),
                                            voucherAllocationRequest.getProductId(), voucherAllocationRequest.getTrxId());

                                        //set response
                                        voucherAllocationResponse.setCollectPoint(giftingVoucher.getCollectionPoint());
                                        voucherAllocationResponse.setVoucherDescription(giftingVoucher.getDescription());
                                        voucherAllocationResponse.setExpiryDate(DateConverter.parseStringDateTime(giftingVoucher.getExpiryDate()));
                                        voucherAllocationResponse.setVoucherCode(giftingVoucher.getPin());
                                        voucherAllocationResponse.setVoucherVendor(giftingVoucher.getMerchant());
                                        voucherAllocationResponse.setVoucherId(giftingVoucher.getSeq());
                                    }
                                    else {
                                        VPVouchersDTO voucher = getAndIssueVoucher(voucherAllocationRequest.getProductId(), voucherAllocationRequest.getTrxId());

                                        //set response
                                        voucherAllocationResponse.setCollectPoint(voucher.getCollectionPoint());
                                        voucherAllocationResponse.setExpiryDate(voucher.getExpiryDate() != null
                                            ? voucher
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
                                            .plusDays(vpVoucherDef.getValidityPeriod()));
                                        voucherAllocationResponse.setTrxId(voucherAllocationRequest.getTrxId());
                                        voucherAllocationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                        voucherAllocationResponse.setVoucherCode(voucher.getVoucherCode());
                                        voucherAllocationResponse.setVoucherDescription(voucher.getDescription());
                                        voucherAllocationResponse.setVoucherId(voucher.getId());
                                        voucherAllocationResponse.setVoucherType(vpVoucherDef.getType());
                                        voucherAllocationResponse.setVoucherVendor(vpVoucherDef.getVendor());
                                        voucherAllocationResponse.setEncryptedYN(vpVoucherDef.getEncryptedYN());
                                    }
                                    break;
                                case Constants.GENERIC_VOUCHER:
                                    //get valid voucher
                                    Optional<VPVouchersDTO> vpVouchersDTOGen = vpVouchersService.getValidVoucher(
                                        voucherAllocationRequest.getProductId()
                                    );
                                    if (vpVouchersDTOGen.isPresent()) { //voucher found
                                        VPVouchersDTO vpVoucher = vpVouchersDTOGen.get();
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

                                        //set response
                                        voucherAllocationResponse.setCollectPoint(vpVoucher.getCollectionPoint());
                                        voucherAllocationResponse.setExpiryDate(expiryDate);
                                        voucherAllocationResponse.setTrxId(voucherAllocationRequest.getTrxId());
                                        voucherAllocationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                        voucherAllocationResponse.setVoucherCode(vpVoucher.getVoucherCode());
                                        voucherAllocationResponse.setVoucherDescription(vpVoucher.getDescription());
                                        voucherAllocationResponse.setVoucherId(vpVoucher.getId());
                                        voucherAllocationResponse.setVoucherType(vpVoucherDef.getType());
                                        voucherAllocationResponse.setVoucherVendor(vpVoucherDef.getVendor());
                                        voucherAllocationResponse.setEncryptedYN(vpVoucherDef.getEncryptedYN());
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

        log.debug(voucherAllocationResponse.toString());
        log.info("Voucher issued for {} with trxId {}", voucherAllocationRequest.getMsisdn(), voucherAllocationResponse.getTrxId());

        return new ResponseEntity<>(voucherAllocationResponse, HttpStatus.OK);
    }

    //retry once
    public ResponseEntity<VoucherAllocationResponse> issueVoucherFallback(VoucherAllocationRequest voucherAllocationRequest) {
        return issueVoucher(voucherAllocationRequest);
    }

    private GiftingVoucherResponseDTO getVoucherFromGifting(String msisdn, String productId, String trxId){
        GiftingVoucherRequestDTO giftingVoucherRequestDTO = new GiftingVoucherRequestDTO()
            .msisdn(msisdn)
            .rewardId(productId)
            .trxId(trxId);

        ResponseEntity<GiftingVoucherResponseDTO> response = giftingApiClient.putGiftingVoucher(giftingVoucherRequestDTO);

        if (!HttpStatus.OK.equals(response.getStatusCode())) {
            log.error("Error received from Gifting - {}", response.getBody().getErrorMessage());
            throw new ResponseStatusException(
                response.getStatusCode(),
                "Failed to issue voucher on Gifting - " + response.getBody().getErrorMessage()
            );
        }

        log.debug("response.getBody()");

        return response.getBody();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private VPVouchersDTO getAndIssueVoucher(String productId, String transactionId) {
        List<VPVouchersDTO> vpVouchers = vpVouchersService.getValidVoucherWithLock(
            productId
        );

        // Vouchers found ?
        if (vpVouchers.isEmpty()) {
            throw new AllocationException("Voucher not available", Status.NOT_FOUND);
        }

        log.info("Voucher available to issue for {}.", productId);
        //issue voucher
        vpVouchersService.issueVoucher(transactionId, vpVouchers.get(0).getId());

        return vpVouchers.get(0);
    }

    @Transactional
    //@HystrixCommand(fallbackMethod = "returnVoucherFallback", ignoreExceptions = AllocationException.class)
    @Override
    public ResponseEntity<VoucherReturnResponse> returnVoucher(Long voucherId, VoucherReturnRequest voucherReturnRequest) {
        log.info("Return voucher request received for {} with trxId {}.", voucherReturnRequest.getMsisdn(), voucherReturnRequest.getTrxId());
        log.debug(voucherReturnRequest.toString());
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
        Optional<VPVoucherDefDTO> vpVoucherDefOptional = vpVoucherDefService.findOne(voucherReturnRequest.getProductId());
        if (vpVoucherDefOptional.isPresent()) { //productId found
            VPVoucherDefDTO vpVoucherDef = vpVoucherDefOptional.get();
            log.debug(vpVoucherDef.toString());
            log.info(vpVoucherDef.toString());
            switch (vpVoucherDef.getType()) {
                case Constants.VOUCHER:
                    Optional<VPVouchersDTO> optionalVPVouchers = vpVouchersService.getValidVoucherForReturn(
                        voucherReturnRequest.getProductId(),
                        voucherId,
                        voucherReturnRequest.getTrxId()
                    );
                    if (optionalVPVouchers.isPresent()) {
                        VPVouchersDTO vpVouchers = optionalVPVouchers.get();
                        log.debug(vpVouchers.toString());
                        //Update voucher
                        vpVouchersService.updateReturnedVoucher(voucherId);

                        voucherReturnResponse.setTrxId(voucherReturnRequest.getTrxId());
                        voucherReturnResponse.setVoucherDescription(vpVoucherDef.getDescription());
                        voucherReturnResponse.setVoucherId(vpVouchers.getId());
                    } else {
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

                    if (
                        couponsDelResponse.getResponseCode().equals(Constants.RESPONSE_CODE) ||
                            couponsDelResponse.getResponseDesc().equalsIgnoreCase(Constants.RESPONSE_DESC)
                    ) {
                        log.info("WiGroup voucher returned.");
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
        log.info("Voucher {} returned for trxId {}.", voucherReturnResponse.getVoucherDescription(), voucherReturnResponse.getTrxId());
        return new ResponseEntity<>(voucherReturnResponse, HttpStatus.OK);
    }

    //retry once
    public ResponseEntity<VoucherReturnResponse> returnVoucherFallback(Long voucherId, VoucherReturnRequest voucherReturnRequest) {
        return returnVoucher(voucherId, voucherReturnRequest);
    }
}
