package za.co.vodacom.cvm.web.rest;

import brave.Tracer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.zalando.problem.Status;
import za.co.vodacom.cvm.client.wigroup.api.*;

import za.co.vodacom.cvm.config.Constants;
import za.co.vodacom.cvm.domain.VPCampaign;
import za.co.vodacom.cvm.domain.VPCampaignVouchers;
import za.co.vodacom.cvm.domain.VPVoucherDef;
import za.co.vodacom.cvm.exception.AllocationException;
import za.co.vodacom.cvm.service.VPCampaignService;
import za.co.vodacom.cvm.service.VPCampaignVouchersService;
import za.co.vodacom.cvm.service.VPVoucherDefService;
import za.co.vodacom.cvm.service.VPVouchersService;
import za.co.vodacom.cvm.web.api.ProductApiDelegate;
import za.co.vodacom.cvm.web.api.model.ProductDetailsResponse;
import za.co.vodacom.cvm.web.api.model.ProductListResponseObject;
import za.co.vodacom.cvm.web.api.model.ProductValidationResponse;
import za.co.vodacom.cvm.web.api.model.Transaction;
import za.co.vodacom.cvm.client.wigroup.model.CouponCampaignResponse;
import za.co.vodacom.cvm.client.wigroup.model.CouponCampaigns;
import za.co.vodacom.cvm.client.wigroup.model.GiftcardCampaigns;
import za.co.vodacom.cvm.client.wigroup.model.GiftcardCampaignsResponse;


import javax.validation.Valid;

@Service
public class ProductServiceImpl implements ProductApiDelegate {

    public static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

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
    GiftcardsCampaign10ApiClient giftcardsCampaign10ApiClient;
    @Autowired
    GiftcardsDefaultApiClient giftcardsDefaultApiClient;
    @Autowired
    CouponscampaignsApiClient couponscampaignsApiClient;
    @Autowired
    GiftcardcampaignsApiClient giftcardcampaignsApiClient;
    @Autowired
    Tracer tracer;

    ProductServiceImpl(
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
    // @HystrixCommand(fallbackMethod = "validateVoucherFallback", ignoreExceptions = AllocationException.class)
    @Override
    public ResponseEntity<ProductValidationResponse> validateVoucher(String productId, String origin, String campaign) {
        ProductValidationResponse productValidationResponse = new ProductValidationResponse();
        log.debug("Product ID: {}, Origin: {}, Campaign: {}", productId, origin, campaign);
        log.info("Product ID: {}, Origin: {}, Campaign: {}", productId, origin, campaign);

        //Validate the incoming campaign
        Optional<VPCampaign> vpCampaign = vpCampaignService.findByName(campaign);
        if (vpCampaign.isPresent()) {
            log.debug(vpCampaign.get().toString());
            log.info(vpCampaign.get().toString());
            //Validate incoming productId
            Optional<VPCampaignVouchers> vpCampaignVouchers = vpCampaignVouchersService.findByProductIdAndCampaignIdAndActiveYN(
                productId,
                vpCampaign.get().getId(),
                Constants.YES
            );
            //ProductId Found
            if (vpCampaignVouchers.isPresent()) {
                log.debug(vpCampaignVouchers.get().toString());
                log.info(vpCampaignVouchers.get().toString());
                vpVoucherDefService
                    .findOne(vpCampaignVouchers.get().getProductId())
                    .ifPresent(vpVoucherDef -> {
                        switch (vpVoucherDef.getType()) {
                            case Constants.VOUCHER:
                                vpVouchersService
                                    .getValidVoucherForProduct(productId)
                                    .ifPresent(vpVouchers -> {
                                        productValidationResponse.setMinExpiry(
                                            vpVouchers == null ? null : vpVouchers.getMinEndDateTime().toOffsetDateTime()
                                        );
                                        productValidationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                        productValidationResponse.setVoucherDescription(vpVoucherDef.getDescription());
                                        productValidationResponse.setVoucherQuantity(vpVouchers.getCount());
                                        productValidationResponse.setVoucherType(vpVoucherDef.getType());
                                        productValidationResponse.setVoucherVendor(vpVoucherDef.getVendor());
                                    });
                                break;
                            case Constants.GENERIC_VOUCHER:
                                vpVouchersService
                                    .getValidVoucherForProductGenericVoucher(productId)
                                    .ifPresent(vpVouchers -> {
                                        productValidationResponse.setMinExpiry(vpVouchers.getMinEndDateTime().toOffsetDateTime());
                                        productValidationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                        productValidationResponse.setVoucherDescription(vpVoucherDef.getDescription());
                                        productValidationResponse.setVoucherQuantity(vpVouchers.getCount());
                                        productValidationResponse.setVoucherType(vpVoucherDef.getType());
                                        productValidationResponse.setVoucherVendor(vpVoucherDef.getVendor());
                                    });
                                if (productValidationResponse == null) {
                                    productValidationResponse.setMinExpiry(null);
                                    productValidationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                    productValidationResponse.setVoucherDescription(vpVoucherDef.getDescription());
                                    productValidationResponse.setVoucherQuantity(Long.valueOf(0));
                                    productValidationResponse.setVoucherType(vpVoucherDef.getType());
                                    productValidationResponse.setVoucherVendor(vpVoucherDef.getVendor());
                                }
                                break;
                            case Constants.ONLINE_VOUCHER:
                                productValidationResponse.setMinExpiry(null);
                                productValidationResponse.setVoucherCategory(vpVoucherDef.getCategory());
                                productValidationResponse.setVoucherDescription(vpVoucherDef.getDescription());
                                productValidationResponse.setVoucherQuantity(Long.valueOf(1));
                                productValidationResponse.setVoucherType(vpVoucherDef.getType());
                                productValidationResponse.setVoucherVendor(vpVoucherDef.getVendor());
                                break;
                        }
                    });
            } else {
                throw new AllocationException("Invalid ProductId", Status.NOT_FOUND);
            }
        } else { //campaign found //campaign not found
            throw new AllocationException("Invalid Campaign", Status.NOT_FOUND);
        }
        return new ResponseEntity<>(productValidationResponse, HttpStatus.OK);
    }

    //retry once
    public ResponseEntity<ProductValidationResponse> validateVoucherFallback(String productId, String origin, String campaign) {
        return validateVoucher(productId, origin, campaign);
    }

    @Override
    public ResponseEntity<List<ProductListResponseObject>> getProductList() {
        List<ProductListResponseObject> productListResponseObjects = new ArrayList<>();

        vpVoucherDefService
            .getAll()
            .ifPresent(vpVoucherDefs -> {
                vpVoucherDefs.forEach(vpVoucherDef -> {
                    ProductListResponseObject productListResponseObject = new ProductListResponseObject();
                    productListResponseObject.setProductId(vpVoucherDef.getId());
                    productListResponseObject.setCacheQuantity(vpVoucherDef.getCacheQuantity().toString());
                    productListResponseObject.setCategory(vpVoucherDef.getCategory());
                    productListResponseObject.setEncryptedYN(vpVoucherDef.getEncryptedYN());
                    productListResponseObject.setExtId(vpVoucherDef.getExtId());
                    productListResponseObject.setDescription(vpVoucherDef.getDescription());
                    productListResponseObject.setExtSystem(vpVoucherDef.getExtSystem());
                    //productListResponseObject.setExtSyncSys(vpVoucherDef.getExtSyncSys());
                    productListResponseObject.setType(vpVoucherDef.getType());
                    productListResponseObject.setValidityPeriod(vpVoucherDef.getValidityPeriod().toString());
                    productListResponseObject.setVendor(vpVoucherDef.getVendor());

                    productListResponseObjects.add(productListResponseObject);
                });
            });

        return new ResponseEntity<>(productListResponseObjects, HttpStatus.OK);
    }

    public ResponseEntity<ProductDetailsResponse> getProductDetails(String productId,
                                                                    String origin,
                                                                    String campaign) {
        ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();

        log.debug("Product ID: {}, Origin: {}, Campaign: {}", productId, origin, campaign);
        log.info("Product ID: {}, Origin: {}, Campaign: {}", productId, origin, campaign);

        Optional<VPVoucherDef> voucherDetails = vpVoucherDefService.getVoucherDetails(campaign, productId);

        if (!voucherDetails.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        else {
            VPVoucherDef voucherDef = voucherDetails.orElseThrow();
            //set product response
            productDetailsResponse.setCategory(voucherDef.getCategory());
            productDetailsResponse.setType(voucherDef.getType());
            productDetailsResponse.setExtSystem(voucherDef.getExtSystem());
            productDetailsResponse.setExtId(voucherDef.getExtId());
            productDetailsResponse.setProductId(voucherDef.getId());
            productDetailsResponse.setValidityPeriod(voucherDef.getValidityPeriod());
            productDetailsResponse.setDescription(voucherDef.getDescription());
            productDetailsResponse.setCollectPoint(voucherDef.getCollectionPoint());
            productDetailsResponse.setVendor(voucherDef.getVendor());

                if(Constants.WIROUP.equalsIgnoreCase(voucherDef.getExtSystem()) &&
                    Constants.ONLINE_VOUCHER.equalsIgnoreCase(voucherDef.getType())) {
                    // Make call to Wigroup For coupon Campaign
                    ResponseEntity<CouponCampaignResponse> res = null;
                    try {
                        res = couponscampaignsApiClient.retrieveCouponCampaign(Long.valueOf(voucherDef.getExtId()));
                    }catch (Exception e){
                        log.error("Error encountered while making request to WiGroup {}", e.getMessage());
                    }
                    assert res != null;
                    Transaction transaction = getTransaction(res);
                    productDetailsResponse.setTransactionSummary(transaction);

                }else if(Constants.ONLINE_GIFT_CARD.equalsIgnoreCase(voucherDef.getType())) {
                    ResponseEntity<GiftcardCampaignsResponse> res = null;
                    try {
                        res = giftcardcampaignsApiClient.retrieveGiftcardCampaign(Long.valueOf(voucherDef.getExtId()));
                    }catch (Exception e){
                        log.error("Error encountered while making request to WiGroup {}", e.getMessage());
                    }
                    assert res != null;
                    Transaction transaction = getGiftCardTransaction(res);

                    productDetailsResponse.setTransactionSummary(transaction);
                }

            }

        return new ResponseEntity<>(productDetailsResponse,HttpStatus.OK);
    }

    private static Transaction getTransaction(ResponseEntity<CouponCampaignResponse> res) {
        Transaction transaction = new Transaction();
        @Valid CouponCampaigns result = Objects.requireNonNull(res.getBody()).getCouponCampaign();
        transaction.setExpiryDays(result.getExpiryDays());
        transaction.setTotalIssuedToday(result.getTotalIssuedToday());
        transaction.setTotalIssued(result.getTotalIssued());
        transaction.setMaxAllowedToIssue(result.getMaxAllowedToIssue());
        transaction.setMaxAllowedToIssueDaily(result.getMaxAllowedToIssueDaily());
        transaction.setTotalLive(result.getTotalLive());
        transaction.setTotalExpired(result.getTotalExpired());
        transaction.setTotalRedeemed(result.getTotalRedeemed());
        return transaction;
    }
    private static Transaction getGiftCardTransaction(ResponseEntity<GiftcardCampaignsResponse> res) {
        var transaction = new Transaction();
        @Valid GiftcardCampaigns result = Objects.requireNonNull(res.getBody()).getGiftcardCampaign();
        transaction.setExpiryDays(result.getExpiryDays());
        transaction.setTotalIssued(result.getTotalIssued());
        transaction.setTotalLive(result.getTotalLive());
        transaction.setTotalExpired(result.getTotalExpired());
        transaction.setTotalRedeemed(result.getTotalRedeemed());
        transaction.setTotalAmountRedeemed(result.getTotalAmountRedeemed());
        return transaction;
    }


}
