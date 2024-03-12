package za.co.vodacom.cvm.web.rest;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

import brave.Tracer;
import feign.FeignException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.persistence.LockTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.zalando.problem.Status;
import za.co.vodacom.cvm.client.wigroup.api.CouponsApiClient;
import za.co.vodacom.cvm.client.wigroup.api.GiftcardsCampaign10ApiClient;
import za.co.vodacom.cvm.client.wigroup.api.GiftcardsDefaultApiClient;
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
import za.co.vodacom.cvm.service.dto.product.ProductQuantityDTO;
import za.co.vodacom.cvm.utils.MSISDNConverter;
import za.co.vodacom.cvm.utils.RSAEncryption;
import za.co.vodacom.cvm.web.api.VoucherApiDelegate;
import za.co.vodacom.cvm.web.api.model.*;
import za.co.vodacom.cvm.web.rest.errors.BadRequestAlertException;

@Service
public class VoucherServiceImpl implements VoucherApiDelegate {

    public static final Logger log = LoggerFactory.getLogger(VoucherServiceImpl.class);

    @Autowired
    VPCampaignService vpCampaignService;

    @Autowired
    VPCampaignVouchersService vpCampaignVouchersService;

    @Autowired
    VPVoucherDefService vpVoucherDefService;

    @Autowired
    VPVouchersService vpVouchersService;

    @Autowired
    CouponsApiClient couponsApiClient;

    @Autowired
    GiftcardsCampaign10ApiClient giftcardsCampaign10ApiClient;

    @Autowired
    GiftcardsDefaultApiClient giftcardsDefaultApiClient;

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

    @Transactional
    //@HystrixCommand(fallbackMethod = "issueVoucherFallback", ignoreExceptions = AllocationException.class)
    @Override
    public ResponseEntity<VoucherAllocationResponse> issueVoucher(VoucherAllocationRequest voucherAllocationRequest) {
        log.debug(voucherAllocationRequest.toString());
        log.info(voucherAllocationRequest.toString());

        CouponsRequest couponsRequest = new CouponsRequest();
        GiftCardsRequest giftCardsRequest = new GiftCardsRequest();

        if (
            voucherAllocationRequest.getMsisdn().length() == 11 &&
            Pattern.matches(applicationProperties.getMsisdn().getExternal(), voucherAllocationRequest.getMsisdn())
        ) {
            voucherAllocationRequest.setMsisdn(msisdnConverter.convertToInternal(voucherAllocationRequest.getMsisdn()));
            couponsRequest.setMobileNumber(voucherAllocationRequest.getMsisdn());
            couponsRequest.setUserRef(voucherAllocationRequest.getMsisdn());
        } else if (
            voucherAllocationRequest.getMsisdn().length() == 15 &&
            Pattern.matches(applicationProperties.getMsisdn().getInternal(), voucherAllocationRequest.getMsisdn())
        ) {
            couponsRequest.setMobileNumber(msisdnConverter.convertToExternal(voucherAllocationRequest.getMsisdn()));
            couponsRequest.setUserRef(msisdnConverter.convertToExternal(voucherAllocationRequest.getMsisdn()));
        } else throw new BadRequestAlertException(
            "Invalid MSISDN format: " + voucherAllocationRequest.getMsisdn(),
            ENTITY_NAME,
            "11 or 15 digits required"
        );

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
                    .ifPresent(vpVoucherDef -> {
                        log.debug(vpVoucherDef.toString());
                        switch (vpVoucherDef.getType()) {
                            case Constants.VOUCHER:
                                VPVouchers voucher = getAndIssueVoucher(
                                    voucherAllocationRequest.getProductId(),
                                    voucherAllocationRequest.getTrxId()
                                );

                                //set response
                                voucherAllocationResponse.setCollectPoint(
                                    voucher.getCollectionPoint() != null && !voucher.getCollectionPoint().isEmpty()
                                        ? voucher.getCollectionPoint()
                                        : vpVoucherDef.getCollectionPoint()
                                );
                                voucherAllocationResponse.setExpiryDate(
                                    voucher.getExpiryDate() != null
                                        ? voucher.getExpiryDate().toLocalDate().atTime(23, 59, 59).atOffset(ZoneOffset.UTC)
                                        : OffsetDateTime
                                            .now()
                                            .toLocalDate()
                                            .atTime(23, 59, 59)
                                            .atOffset(ZoneOffset.UTC)
                                            .plusDays(vpVoucherDef.getValidityPeriod())
                                );
                                voucherAllocationResponse.setTrxId(voucherAllocationRequest.getTrxId());
                                voucherAllocationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                voucherAllocationResponse.setVoucherCode(voucher.getVoucherCode());
                                voucherAllocationResponse.setVoucherDescription(vpVoucherDef.getDescription());
                                voucherAllocationResponse.setVoucherId(voucher.getId());
                                voucherAllocationResponse.setVoucherType(vpVoucherDef.getType());
                                voucherAllocationResponse.setVoucherVendor(vpVoucherDef.getVendor());
                                voucherAllocationResponse.setEncryptedYN(vpVoucherDef.getEncryptedYN());

                                log.debug(voucherAllocationResponse.toString());
                                break;
                            case Constants.GENERIC_VOUCHER:
                                //get valid voucher
                                List<ProductQuantityDTO> vpVouchersGen = vpVouchersService.getValidVoucher(
                                    voucherAllocationRequest.getProductId()
                                );
                                if (!vpVouchersGen.isEmpty()) { //voucher found
                                    ProductQuantityDTO vpVoucher = vpVouchersGen.get(0);
                                    log.debug(vpVoucher.toString());
                                    log.info(vpVoucher.toString());
                                    OffsetDateTime expiryDate = vpVoucher.getExpiryDate() != null
                                        ? vpVoucher.getExpiryDate().toLocalDate().atTime(23, 59, 59).atOffset(ZoneOffset.UTC)
                                        : OffsetDateTime
                                            .now()
                                            .toLocalDate()
                                            .atTime(23, 59, 59)
                                            .atOffset(ZoneOffset.UTC)
                                            .plusDays(vpVoucherDef.getValidityPeriod());

                                    String voucherCode = vpVoucher.getVoucherCode();

                                    //set response
                                    voucherAllocationResponse.setCollectPoint(
                                        vpVoucher.getCollectionPoint() != null && !vpVoucher.getCollectionPoint().isEmpty()
                                            ? vpVoucher.getCollectionPoint()
                                            : vpVoucherDef.getCollectionPoint()
                                    );
                                    voucherAllocationResponse.setExpiryDate(expiryDate);
                                    voucherAllocationResponse.setTrxId(voucherAllocationRequest.getTrxId());
                                    voucherAllocationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                    voucherAllocationResponse.setVoucherCode(voucherCode);
                                    voucherAllocationResponse.setVoucherDescription(vpVoucherDef.getDescription());
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
                                                ? rsaEncryption.encrypt(voucherCode, applicationProperties.getEncryption().getKey())
                                                : voucherCode;
                                    } catch (Exception e) {
                                        log.error(
                                            "There was an error issuing this voucher => ProductId:" +
                                            voucherAllocationRequest.getProductId() +
                                            "," +
                                            "Msisdn:" +
                                            voucherAllocationRequest.getMsisdn() +
                                            "Error Message:" +
                                            e.getMessage()
                                        );
                                    }
                                    //set response
                                    voucherAllocationResponse.setCollectPoint(vpVoucherDef.getCollectionPoint());
                                    voucherAllocationResponse.setExpiryDate(
                                        ZonedDateTime
                                            .parse(
                                                couponsResponse.getCoupon().getRedeemToDate(),
                                                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'+'SSSS").withZone(ZoneId.systemDefault())
                                            )
                                            .toOffsetDateTime()
                                    );
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
                                giftCardsRequest.setBalance(voucherAllocationRequest.getValue().longValue() * 100);
                                giftCardsRequest.setUserRef(msisdnConverter.convertToExternal(voucherAllocationRequest.getMsisdn()));
                                giftCardsRequest.setMobileNumber(msisdnConverter.convertToExternal(voucherAllocationRequest.getMsisdn()));
                                giftCardsRequest.setStateId(GiftCardsRequest.StateIdEnum.A);

                                log.info(giftCardsRequest.toString());
                                //call wi group
                                ResponseEntity<GiftCardsResponse> giftCardsResponseResponseEntity = null;
                                try {
                                    if (vpCampaign.get().getId().equals((long) 10)) {
                                        giftCardsResponseResponseEntity =
                                            giftcardsCampaign10ApiClient.updateVoucherToReserved(true, giftCardsRequest);
                                    } else {
                                        giftCardsResponseResponseEntity =
                                            giftcardsDefaultApiClient.updateVoucherToReserved(true, giftCardsRequest);
                                    }
                                } catch (FeignException ex) {
                                    log.error("Feign client exception message - {}", ex.getMessage());
                                    throw new ResponseStatusException(HttpStatus.valueOf(ex.status()), ex.getMessage());
                                }
                                //success
                                GiftCardsResponse giftCardsResponse = giftCardsResponseResponseEntity.getBody();
                                log.info("Gift Card Response is: {}", giftCardsResponse);
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
                                                ? rsaEncryption.encrypt(voucherCode, applicationProperties.getEncryption().getKey())
                                                : voucherCode;
                                    } catch (Exception e) {
                                        log.error(
                                            "There was an error issuing this voucher => ProductId:" +
                                            voucherAllocationRequest.getProductId() +
                                            "," +
                                            "Msisdn:" +
                                            voucherAllocationRequest.getMsisdn() +
                                            "Error Message:" +
                                            e.getMessage()
                                        );
                                    }
                                    //set response
                                    voucherAllocationResponse.setCollectPoint(vpVoucherDef.getCollectionPoint());
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
                    });
            } else { //productId not found
                throw new AllocationException("Invalid ProductId", Status.NOT_FOUND);
            }
        } else { //campaign not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.info("Voucher issued for {}", voucherAllocationRequest.getMsisdn());

        return new ResponseEntity<>(voucherAllocationResponse, HttpStatus.OK);
    }

    public ResponseEntity<VoucherAllocationResponse> issueVoucherInternal(
        String allocationService,
        VoucherAllocationRequest voucherAllocationRequest
    ) {
        validateAllowed(allocationService);

        return issueVoucher(voucherAllocationRequest);
    }

    //retry once
    public ResponseEntity<VoucherAllocationResponse> issueVoucherFallback(VoucherAllocationRequest voucherAllocationRequest) {
        return issueVoucher(voucherAllocationRequest);
    }

    @Retryable(maxAttempts = 2, value = RuntimeException.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public VPVouchers getAndIssueVoucher(String productId, String transactionId) {
        List<VPVouchers> vbVouchersList;
        vbVouchersList = vpVouchersService.getVouchersWithStatusA(productId);
        VPVouchers vouchers = new VPVouchers();
        List<Long> productIdList = new ArrayList<>();
        List<VPVouchers> vpVouchers;
        // Vouchers found ?
        if (vbVouchersList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Voucher not available");
        }

        productIdList.addAll(vbVouchersList.stream().map(VPVouchers::getId).collect(Collectors.toList()));

        if(productIdList.size() > 3) {
            log.debug("list of 12 product ids: {}", productIdList );
            Collections.shuffle(productIdList);
            int randomSeriesLength = 3;

            List<Long> threeProductIds = productIdList.subList(0, randomSeriesLength);
            log.debug("Selected to 3 in list : {}", threeProductIds);

             vpVouchers = vpVouchersService.getVoucherSkipLocked(
                threeProductIds.toString().replaceAll("\\[", "").replaceAll("\\]",
                    ""));
        }else{

             vpVouchers = vpVouchersService.getVoucherSkipLocked(
                productIdList.toString().replaceAll("\\[", "").replaceAll("\\]",
                    ""));
        }


        if(vpVouchers.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Voucher not available");
        }

        log.debug("Limit 1 query returns: {}",vpVouchers);
        //issue voucher
        vpVouchersService.issueVoucher(transactionId, vbVouchersList.get(0).getId());
        vouchers = vbVouchersList.get(0);
        return  vouchers;
    }

    @Transactional
    //@HystrixCommand(fallbackMethod = "returnVoucherFallback", ignoreExceptions = AllocationException.class)
    @Override
    public ResponseEntity<VoucherReturnResponse> returnVoucher(Long voucherId, VoucherReturnRequest voucherReturnRequest) {
        log.debug(voucherReturnRequest.toString());
        log.info(voucherReturnRequest.toString());
        CouponsRequest couponsRequest = new CouponsRequest();
        if (
            voucherReturnRequest.getMsisdn().length() == 11 &&
            Pattern.matches(applicationProperties.getMsisdn().getExternal(), voucherReturnRequest.getMsisdn())
        ) {
            voucherReturnRequest.setMsisdn(msisdnConverter.convertToInternal(voucherReturnRequest.getMsisdn()));
            couponsRequest.setMobileNumber(voucherReturnRequest.getMsisdn());
            couponsRequest.setUserRef(voucherReturnRequest.getMsisdn());
        } else if (
            voucherReturnRequest.getMsisdn().length() == 15 &&
            Pattern.matches(applicationProperties.getMsisdn().getInternal(), voucherReturnRequest.getMsisdn())
        ) {
            couponsRequest.setMobileNumber(msisdnConverter.convertToExternal(voucherReturnRequest.getMsisdn()));
            couponsRequest.setUserRef(msisdnConverter.convertToExternal(voucherReturnRequest.getMsisdn()));
        } else throw new BadRequestAlertException(
            "Invalid MSISDN format: " + voucherReturnRequest.getMsisdn(),
            ENTITY_NAME,
            "11 or 15 digits required"
        );

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
                case Constants.ONLINE_GIFT_CARD:
                    //call wi group
                    ResponseEntity<GiftCardsDelResponse> giftCardsDelResponseResponseEntity = giftcardsDefaultApiClient.expireGiftCards(voucherId);
                    //success
                    GiftCardsDelResponse giftCardsDelResponse = giftCardsDelResponseResponseEntity.getBody();
                    if (
                        giftCardsDelResponse.getResponseCode().equals(Constants.RESPONSE_CODE) ||
                            giftCardsDelResponse.getResponseDesc().equalsIgnoreCase(Constants.RESPONSE_DESC)
                    ) {
                        //set response
                        voucherReturnResponse.setVoucherDescription(vpVoucherDef.getDescription());
                        voucherReturnResponse.setTrxId(voucherReturnRequest.getTrxId());
                        voucherReturnResponse.setVoucherId(voucherId);
                    } else { //failed
                        throw new WiGroupException(giftCardsDelResponseResponseEntity.getBody().getResponseDesc(), Status.INTERNAL_SERVER_ERROR);
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

    @Override
    public ResponseEntity<VoucherBalanceResponse> voucherbalance(Integer voucherid, String origin, String campaign) {
        VoucherBalanceResponse VoucherBalanceResponse = new VoucherBalanceResponse();

        //Check that the incoming campaign is valid as per VP_CAMPAIGN
        Optional<VPCampaign> vpCampaign = vpCampaignService.findByName(campaign);

        //if VP_Campaign is false throw an exception
        if (!(vpCampaign.isPresent())) {
            throw new AllocationException("Invalid Campaign", Status.NOT_FOUND);
        }
        //if VP_Campaign is succesfully validated
        else {
            //call wi group
            GiftCardsBalanceResponse GiftCardsBalanceResponse = new GiftCardsBalanceResponse();
            if (vpCampaign.get().getId().equals((long) 10)) {
                ResponseEntity<GiftCardsBalanceResponse> GiftCardsBalanceResponseEntity = giftcardsCampaign10ApiClient.viewGiftcard(
                    voucherid.longValue()
                );
                GiftCardsBalanceResponse = GiftCardsBalanceResponseEntity.getBody();
            } else {
                ResponseEntity<GiftCardsBalanceResponse> GiftCardsBalanceResponseEntity = giftcardsDefaultApiClient.viewGiftcard(
                    voucherid.longValue()
                );
                GiftCardsBalanceResponse = GiftCardsBalanceResponseEntity.getBody();
            }

            //logging response
            log.debug("Gift Card Response is: {}", GiftCardsBalanceResponse);

            //Check that the responseDesc field in the response object is set to "Success", if true proceed as follows
            if (
                GiftCardsBalanceResponse.getResponseCode().equals(Constants.RESPONSE_CODE) ||
                GiftCardsBalanceResponse.getResponseDesc().equals(Constants.RESPONSE_DESC)
            ) {
                VoucherBalanceResponse.setBalance(GiftCardsBalanceResponse.getGiftcard().getBalance());
                VoucherBalanceResponse.setExpiredAmount(GiftCardsBalanceResponse.getGiftcard().getExpiredAmount());
                VoucherBalanceResponse.setExpiryDate(GiftCardsBalanceResponse.getGiftcard().getExpiryDate());
                VoucherBalanceResponse.setIssuedAmount(GiftCardsBalanceResponse.getGiftcard().getIssuedAmount());
                VoucherBalanceResponse.setRedeemedAmount(GiftCardsBalanceResponse.getGiftcard().getRedeemedAmount());

                log.debug("Gift Card balance  Response is: {}", VoucherBalanceResponse);
            } // if responseDesc field in the response object is NOT set to "Success"
            else {
                throw new WiGroupException(GiftCardsBalanceResponse.getResponseDesc(), Status.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(VoucherBalanceResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VoucherRedemptionResponse> redeemVoucher(Long voucherId, VoucherRedemptionRequest voucherRedemptionRequest) {
        VoucherRedemptionResponse redemptionResponse = new VoucherRedemptionResponse();
        //Check that the incoming campaign is valid as per VP_CAMPAIGN
        Optional<VPCampaign> vpCampaign = vpCampaignService.findByName(voucherRedemptionRequest.getCampaign());
        if (vpCampaign.isPresent()) {
            //call wi group
            ResponseEntity<GiftCardsRedeemResponse> giftCardsRedeemResponseResponseEntity;
            if (vpCampaign.get().getId().equals((long) 10)) {
                giftCardsRedeemResponseResponseEntity = giftcardsCampaign10ApiClient.redeemGiftcard(voucherId);
            } else {
                giftCardsRedeemResponseResponseEntity = giftcardsDefaultApiClient.redeemGiftcard(voucherId);
            }
            //success
            GiftCardsRedeemResponse giftCardsRedeemResponse = giftCardsRedeemResponseResponseEntity.getBody();
            log.info("Gift Card Response is: {}", giftCardsRedeemResponse);
            if (
                giftCardsRedeemResponse.getResponseCode().equals(Constants.RESPONSE_CODE) ||
                giftCardsRedeemResponse.getResponseDesc().equalsIgnoreCase(Constants.RESPONSE_DESC)
            ) {
                //set response
                redemptionResponse.setVoucherCode(giftCardsRedeemResponse.getToken().getWiCode());
                redemptionResponse.setExpiryDate(giftCardsRedeemResponse.getToken().getValidTillDate());

                log.debug(redemptionResponse.toString());
            } else { //failed
                throw new WiGroupException(giftCardsRedeemResponseResponseEntity.getBody().getResponseDesc(), Status.INTERNAL_SERVER_ERROR);
            }
        } else {
            throw new AllocationException("Invalid Campaign", Status.NOT_FOUND);
        }
        return new ResponseEntity<>(redemptionResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VoucherValidationResponse> voucherValidation(Integer couponId, String productId) {
        VoucherValidationResponse voucherValidationResponse = new VoucherValidationResponse();

        Optional<VPVoucherDef> optionalVPVoucherDef = vpVoucherDefService.findById(productId);
        if (optionalVPVoucherDef.isPresent()) {
            ResponseEntity<CouponsGetResponse> couponsGetResponseResponseEntity = couponsApiClient.getVoucher(couponId.longValue());
            if (couponsGetResponseResponseEntity.getStatusCode().is2xxSuccessful()) {
                CouponsGetResponse couponsGetResponse = couponsGetResponseResponseEntity.getBody();

                if (
                    couponsGetResponse.getResponseCode().equals(Constants.RESPONSE_CODE) ||
                    couponsGetResponse.getResponseDesc().equalsIgnoreCase(Constants.RESPONSE_DESC)
                ) {
                    voucherValidationResponse.setVoucherAmount(couponsGetResponse.getCoupon().getVoucherAmount());
                    voucherValidationResponse.setMsisdn(couponsGetResponse.getCoupon().getMobileNumber());
                    voucherValidationResponse.setDescription(couponsGetResponse.getCoupon().getDescription());
                    voucherValidationResponse.setCouponId(BigDecimal.valueOf(couponsGetResponse.getCoupon().getId()));
                    voucherValidationResponse.setStatus(couponsGetResponse.getCoupon().getStateId().getValue());
                    voucherValidationResponse.setCreateDate(couponsGetResponse.getCoupon().getCreateDate());
                } else throw new AllocationException("Coupon Validation Failed at WiGroup", Status.NOT_FOUND);
            }
        } else throw new AllocationException("Invalid Product ID", Status.NOT_FOUND);

        return new ResponseEntity<>(voucherValidationResponse, HttpStatus.OK);
    }

    private void validateAllowed(String allocationService) {
        log.debug("Validate {} allowed", allocationService);
        if (!applicationProperties.getAllocationAllowed().contains(allocationService)) {
            throw new AccessDeniedException("Request prohibited.");
        }
    }
}
