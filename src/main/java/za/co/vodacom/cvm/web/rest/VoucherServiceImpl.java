package za.co.vodacom.cvm.web.rest;

import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;
import za.co.vodacom.cvm.client.wigroup.api.CouponsApiClient;
import za.co.vodacom.cvm.client.wigroup.api.GiftcardsApiClient;
import za.co.vodacom.cvm.client.wigroup.model.*;
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

import javax.persistence.LockTimeoutException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
    GiftcardsApiClient giftcardsApiClient;

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
        GiftCardsRequest giftCardsRequest = new GiftCardsRequest();

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
                vpVoucherDefService
                    .findOne(voucherAllocationRequest.getProductId())
                    .ifPresent(
                        vpVoucherDef -> {
                            log.debug(vpVoucherDef.toString());
                            switch (vpVoucherDef.getType()) {
                                case Constants.VOUCHER:
                                    VPVouchers voucher = getAndIssueVoucher(voucherAllocationRequest.getProductId(), voucherAllocationRequest.getTrxId());

                                    //set response
                                    voucherAllocationResponse.setCollectPoint(voucher.getCollectionPoint());
                                    voucherAllocationResponse.setExpiryDate(voucher.getExpiryDate() != null
                                        ? voucher
                                        .getExpiryDate()
                                        .toLocalDate()
                                        .atTime(23, 59, 59)
                                        .atOffset(ZoneOffset.UTC)
                                        : OffsetDateTime
                                        .now()
                                        .toLocalDate()
                                        .atTime(23, 59, 59)
                                        .atOffset(ZoneOffset.UTC)
                                        .plusDays(vpVoucherDef.getValidityPeriod()));
                                    voucherAllocationResponse.setTrxId(voucherAllocationRequest.getTrxId());
                                    voucherAllocationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                    voucherAllocationResponse.setVoucherCode(voucher.getVoucherCode());
                                    voucherAllocationResponse.setVoucherDescription(voucher.getDescription());
                                    voucherAllocationResponse.setVoucherId(voucher.getId());
                                    voucherAllocationResponse.setVoucherType(vpVoucherDef.getType());
                                    voucherAllocationResponse.setVoucherVendor(vpVoucherDef.getVendor());
                                    voucherAllocationResponse.setEncryptedYN(vpVoucherDef.getEncryptedYN());

                                    log.debug(voucherAllocationResponse.toString());
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
                                            ? vpVoucher
                                            .getExpiryDate()
                                            .toLocalDate()
                                            .atTime(23, 59, 59)
                                            .atOffset(ZoneOffset.UTC)
                                            : OffsetDateTime
                                            .now()
                                            .toLocalDate()
                                            .atTime(23, 59, 59)
                                            .atOffset(ZoneOffset.UTC)
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
                                            .toLocalDate()
                                            .atTime(23, 59, 59)
                                            .atOffset(ZoneOffset.UTC)
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
                                    } else { //failed
                                        throw new WiGroupException(
                                            couponsResponseResponseEntity.getBody().getResponseDesc(),
                                            Status.INTERNAL_SERVER_ERROR
                                        );
                                    }
                                    break;

                                case Constants.ONLINE_GIFT_CARD:

                                    giftCardsRequest.setCampaignId(vpVoucherDef.getExtId());
                                    giftCardsRequest.setBalance(voucherAllocationRequest.getValue().longValue());
                                    giftCardsRequest.setUserRef(msisdnConverter.convertToExternal(voucherAllocationRequest.getMsisdn()));
                                    giftCardsRequest.setMobileNumber(msisdnConverter.convertToExternal(voucherAllocationRequest.getMsisdn()));
                                    giftCardsRequest.setStateId(GiftCardsRequest.StateIdEnum.A);

                                    log.info(giftCardsRequest.toString());
                                    //call wi group
                                    ResponseEntity<GiftCardsResponse> giftCardsResponseResponseEntity= giftcardsApiClient.updateVoucherToReserved(
                                        true,
                                        giftCardsRequest
                                    );
                                    //success
                                    GiftCardsResponse giftCardsResponse = giftCardsResponseResponseEntity.getBody();
                                    log.info("Gift Card Response is: {}",giftCardsResponse);
                                    if (
                                        giftCardsResponse.getResponseCode().equals(Constants.RESPONSE_CODE) ||
                                            giftCardsResponse.getResponseDesc().equalsIgnoreCase(Constants.RESPONSE_DESC)
                                    ) {
                                        OffsetDateTime expiryDate = OffsetDateTime
                                            .now()
                                            .toLocalDate()
                                            .atTime(23, 59, 59)
                                            .atOffset(ZoneOffset.UTC)
                                            .plusDays(vpVoucherDef.getValidityPeriod());

                                        String voucherCode = giftCardsResponse.getGiftcard().getWicode() + "";
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
                                        voucherAllocationResponse.setVoucherId(giftCardsResponse.getGiftcard().getId());
                                        voucherAllocationResponse.setVoucherType(vpVoucherDef.getType());
                                        voucherAllocationResponse.setVoucherVendor(vpVoucherDef.getVendor());
                                        voucherAllocationResponse.setEncryptedYN(vpVoucherDef.getEncryptedYN());

                                        log.debug(voucherAllocationResponse.toString());
                                    } else { //failed
                                        throw new WiGroupException(
                                            giftCardsResponseResponseEntity.getBody().getResponseDesc(),
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

        log.info("Voucher issued for {}", voucherAllocationRequest.getMsisdn());

        return new ResponseEntity<>(voucherAllocationResponse, HttpStatus.OK);
    }

    //retry once
    public ResponseEntity<VoucherAllocationResponse> issueVoucherFallback(VoucherAllocationRequest voucherAllocationRequest) {
        return issueVoucher(voucherAllocationRequest);
    }

    @Retryable(maxAttempts = 2, value = RuntimeException.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public VPVouchers getAndIssueVoucher(String productId, String transactionId) {
        List<VPVouchers> vpVouchers = vpVouchersService.getValidVoucherWithLock(
            productId
        );

        // Vouchers found ?
        if (vpVouchers.isEmpty()) {
            throw new LockTimeoutException("Voucher not available", new Throwable());
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
