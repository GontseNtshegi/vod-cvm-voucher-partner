package za.co.vodacom.cvm.web.rest;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.IntegrationTest;
import za.co.vodacom.cvm.client.wigroup.api.CouponsApiClient;
import za.co.vodacom.cvm.client.wigroup.api.GiftcardsCampaign10ApiClient;
import za.co.vodacom.cvm.client.wigroup.api.GiftcardsDefaultApiClient;
import za.co.vodacom.cvm.client.wigroup.model.*;
import za.co.vodacom.cvm.config.Constants;
import za.co.vodacom.cvm.domain.*;
import za.co.vodacom.cvm.web.api.model.VoucherAllocationRequest;
import za.co.vodacom.cvm.web.api.model.VoucherRedemptionRequest;
import za.co.vodacom.cvm.web.api.model.VoucherReturnRequest;
import za.co.vodacom.cvm.web.rest.crud.*;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser(roles = { "VOUCHER_PARTNER" })
public class VoucherServiceImplIT {

    private final Logger log = LoggerFactory.getLogger(VoucherServiceImplIT.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss'+'SSSS");

    private static final String DEFAULT_ORIGIN = "AAAAAAAAAA";
    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final Integer DEFAULT_COUPON_ID = 1;
    private static final VoucherAllocationRequest.SubIdTypeEnum SUB_ID_TYPE = VoucherAllocationRequest.SubIdTypeEnum.OM;
    private static final String DEFAULT_SUB_ID = "AAAAAAAAAA";
    private static final String DEFAULT_MSISDN = "27411111111";
    private static final Long DEFAULT_LONG = 1L;
    private static final Integer DEFAULT_INTEGER = 1;
    private static final BigDecimal DEFAULT_BIG_DECIMAL = BigDecimal.valueOf(1);
    private static final String DEFAULT_STRING = "AAAAAAAAAA";
    private static final String DEFAULT_CAMPAIGN_NAME = "AAAAAAAAAA";
    private static final String DEFAULT_TRXID = "AAAAAAAAAA";
    private static final BigDecimal DEFAULT_COST = BigDecimal.ONE;
    private static final BigDecimal DEFAULT_VALUE = BigDecimal.ONE;
    private static final String DEFAULT_VOUCHER_DESCRIPTION = "AAAAAAAAAA";
    private static final String DEFAULT_VOUCHER_CATEGORY = "AAAAAAAAAA";
    private static final String DEFAULT_VOUCHER_VENDOR = "AAAAAAAAAA";
    private static final String DEFAULT_COLLECTION_POINT = "AAAAAAAAAA";
    private static final String DEFAULT_ENCRYPTED_YN = "A";
    private static final String DEFAULT_BATCH_STATUS = "A";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(System.currentTimeMillis() + 240 * 60 * 1000),
        ZoneOffset.systemDefault()
    );

    private static final String DEFAULT_CREATE_DATE = DEFAULT_START_DATE.toLocalDateTime().format(formatter);
    private static final String DEFAULT_REDEEM_FROM_DATE = DEFAULT_START_DATE.toLocalDateTime().format(formatter);
    private static final String DEFAULT_REDEEM_TO_DATE = DEFAULT_END_DATE.toLocalDateTime().format(formatter);

    public static final String VOUCHER = "Voucher";
    public static final String GENERIC_VOUCHER = "GenericVoucher";
    public static final String ONLINE_VOUCHER = "OnlineVoucher";
    public static final String ONLINE_GIFT_CARD = "OnlineGiftcard";
    private static final String DEFAULT_SERVICE = "cp-test";
    private static final String INVALID_SERVICE = "cp-another-test";

    @Autowired
    private MockMvc restVoucherMockMvc;

    @MockBean
    CouponsApiClient couponsApiClient;

    @MockBean
    GiftcardsDefaultApiClient giftcardsDefaultApiClient;

    @MockBean
    GiftcardsCampaign10ApiClient giftcardsCampaign10ApiClient;

    @Autowired
    private EntityManager em;

    public void createDTO(EntityManager em, String type, String campaignName) {
        log.info("Enter createDTO");

        // Ensure default VPBatch exists
        if (TestUtil.findAll(em, VPBatch.class).isEmpty()) {
            VPBatch vpBatch = VPBatchResourceIT.createTestEntity(em, DEFAULT_BATCH_STATUS);
            em.persist(vpBatch);
            em.flush();
        }
        log.info("vpBatch  = {} ", TestUtil.findAll(em, VPBatch.class).toString());

        // Ensure valid VPVouchers for valid batch
        VPBatch vpBatch = TestUtil
            .findAll(em, VPBatch.class)
            .stream()
            .filter(t -> t.getStatus().equals(DEFAULT_BATCH_STATUS))
            .findFirst()
            .get();

        if (TestUtil.findAll(em, VPVouchers.class).isEmpty()) {
            VPVouchers vpVouchers = VPVouchersResourceIT.createTestEntity(em, Math.toIntExact(vpBatch.getId()));
            em.persist(vpVouchers);
            em.flush();
        }
        log.info("vpVouchers  = {} ", TestUtil.findAll(em, VPVouchers.class).toString());

        // Ensure default vpCampaign exists
        Optional<VPCampaign> vpCampaignOptional = TestUtil
            .findAll(em, VPCampaign.class)
            .stream()
            .filter(t -> t.getName().equals(campaignName))
            .findFirst();
        if (!vpCampaignOptional.isPresent()) {
            VPCampaign vpCampaign = VPCampaignResourceIT.createTestEntity(em, campaignName);
            em.persist(vpCampaign);
            em.flush();
        } else {
            em.persist(vpCampaignOptional.get());
            em.flush();
        }

        log.info("vpCampaign  = {} ", TestUtil.findAll(em, VPCampaign.class).toString());
        VPCampaign vpCampaign2 = TestUtil
            .findAll(em, VPCampaign.class)
            .stream()
            .filter(t -> t.getName().equals(campaignName))
            .findFirst()
            .get();

        // Ensure default VPVoucherDef exists
        if (TestUtil.findAll(em, VPVoucherDef.class).isEmpty()) {
            VPVoucherDef vpVoucherDef = VPVoucherDefResourceIT.createTestEntity(em, type);
            em.persist(vpVoucherDef);
            em.flush();
        }

        log.info("VPVoucherDef  = {} ", TestUtil.findAll(em, VPVoucherDef.class).toString());

        // Ensure default VPCampaignVouchers exists
        if (TestUtil.findAll(em, VPCampaignVouchers.class).isEmpty()) {
            VPCampaignVouchers vpCampaignVouchers = VPCampaignVouchersResourceIT.createTestEntity(em, vpCampaign2.getId());
            em.persist(vpCampaignVouchers);
            em.flush();
        }

        log.info("VPCampaignVouchers  = {} ", TestUtil.findAll(em, VPCampaignVouchers.class).toString());
    }

    public static VoucherAllocationRequest createVoucherAllocationRequest(String campaignName) {
        return new VoucherAllocationRequest()
            .origin(DEFAULT_ORIGIN)
            .productId(DEFAULT_PRODUCT_ID)
            .subIdType(SUB_ID_TYPE)
            .subId(DEFAULT_SUB_ID)
            .msisdn(DEFAULT_MSISDN)
            .campaign(campaignName)
            .trxId(DEFAULT_TRXID)
            .cost(DEFAULT_COST)
            .value(DEFAULT_VALUE);
    }

    public static VoucherRedemptionRequest createVoucherRedemptionRequest(String campaignName) {
        return new VoucherRedemptionRequest().origin(DEFAULT_ORIGIN).campaign(campaignName);
    }

    public Long createExistingVoucher(String productId) {
        Long voucherId;
        VPVouchers vpVouchers;
        Optional<VPVouchers> vpVouchersOptional = TestUtil
            .findAll(em, VPVouchers.class)
            .stream()
            .filter(t -> t.getProductId().equals(productId))
            .findFirst();
        if (!vpVouchersOptional.isPresent()) {
            vpVouchers = VPVouchersResourceIT.createTestEntity(em, productId);
            em.persist(vpVouchers);
            em.flush();
            voucherId = vpVouchers.getId();
        } else {
            voucherId = vpVouchersOptional.get().getId();
        }
        log.info("vpVouchers = {} ", TestUtil.findAll(em, VPVouchers.class).toString());
        return voucherId;
    }

    public static VoucherReturnRequest createVoucherReturnRequest() {
        return new VoucherReturnRequest().productId(DEFAULT_PRODUCT_ID).reason(DEFAULT_STRING).msisdn(DEFAULT_MSISDN).trxId(DEFAULT_TRXID);
    }

    public static ResponseEntity<CouponsDelResponse> createCouponsDelResponse() {
        CouponsDelResponse couponsDelResponse = new CouponsDelResponse();
        couponsDelResponse.setResponseCode(Constants.RESPONSE_CODE);
        couponsDelResponse.setResponseDesc(Constants.RESPONSE_DESC);
        return new ResponseEntity<>(couponsDelResponse, HttpStatus.OK);
    }

    public static ResponseEntity<CouponsResponse> createCouponsResponse() {
        CouponsResponseCoupon couponsResponseCoupon = new CouponsResponseCoupon();
        couponsResponseCoupon.setId(DEFAULT_LONG);
        couponsResponseCoupon.setUserRef(DEFAULT_STRING);
        couponsResponseCoupon.setCampaignId(DEFAULT_STRING);
        couponsResponseCoupon.setCampaignName(DEFAULT_STRING);
        couponsResponseCoupon.setCampaignType(DEFAULT_STRING);
        couponsResponseCoupon.setTermsAndConditions(DEFAULT_STRING);
        couponsResponseCoupon.setCreateDate(DEFAULT_CREATE_DATE);
        couponsResponseCoupon.setRedeemFromDate(DEFAULT_REDEEM_FROM_DATE);
        couponsResponseCoupon.setRedeemToDate(DEFAULT_REDEEM_TO_DATE);
        couponsResponseCoupon.setWiCode(DEFAULT_LONG);
        couponsResponseCoupon.setDescription(DEFAULT_STRING);
        couponsResponseCoupon.setVoucherAmount(DEFAULT_VALUE);
        couponsResponseCoupon.setWiQr(DEFAULT_LONG);
        CouponsResponse couponsResponse = new CouponsResponse();
        couponsResponse.setCoupon(couponsResponseCoupon);
        couponsResponse.setResponseCode(Constants.RESPONSE_CODE);
        couponsResponse.setResponseDesc(Constants.RESPONSE_DESC);
        return new ResponseEntity<>(couponsResponse, HttpStatus.OK);
    }

    public static ResponseEntity<CouponsGetResponse> createCouponsGetResponse() {
        CouponsGetResponseCoupon couponsGetResponseCoupon = new CouponsGetResponseCoupon();
        couponsGetResponseCoupon.setId(DEFAULT_COUPON_ID.longValue());
        couponsGetResponseCoupon.setUserRef(DEFAULT_STRING);
        couponsGetResponseCoupon.setCampaignId(DEFAULT_STRING);
        couponsGetResponseCoupon.setCampaignName(DEFAULT_STRING);
        couponsGetResponseCoupon.setCampaignType(DEFAULT_STRING);
        couponsGetResponseCoupon.setTermsAndConditions(DEFAULT_STRING);
        couponsGetResponseCoupon.setCreateDate(DEFAULT_CREATE_DATE);
        couponsGetResponseCoupon.setRedeemFromDate(DEFAULT_REDEEM_FROM_DATE);
        couponsGetResponseCoupon.setRedeemToDate(DEFAULT_REDEEM_TO_DATE);
        couponsGetResponseCoupon.setWiCode(DEFAULT_LONG);
        couponsGetResponseCoupon.setDescription(DEFAULT_STRING);
        couponsGetResponseCoupon.setVoucherAmount(DEFAULT_VALUE);
        couponsGetResponseCoupon.setMobileNumber(DEFAULT_MSISDN);
        couponsGetResponseCoupon.setImageUrl(DEFAULT_STRING);
        couponsGetResponseCoupon.setRedeemedAmount(DEFAULT_BIG_DECIMAL);
        couponsGetResponseCoupon.setStateId(CouponsGetResponseCoupon.StateIdEnum.A);
        CouponsGetResponse couponsGetResponse = new CouponsGetResponse();
        couponsGetResponse.setCoupon(couponsGetResponseCoupon);
        couponsGetResponse.setResponseCode(Constants.RESPONSE_CODE);
        couponsGetResponse.setResponseDesc(Constants.RESPONSE_DESC);
        return new ResponseEntity<>(couponsGetResponse, HttpStatus.OK);
    }

    public static ResponseEntity<GiftCardsBalanceResponse> createGiftCardsBalanceResponse() {
        GiftCardsBalanceResponseGiftcard giftCardsBalanceResponseGiftcard = new GiftCardsBalanceResponseGiftcard();
        giftCardsBalanceResponseGiftcard.setId(DEFAULT_LONG);
        giftCardsBalanceResponseGiftcard.setCampaignId(DEFAULT_INTEGER);
        giftCardsBalanceResponseGiftcard.setInterfaceIssuerId(DEFAULT_STRING);
        giftCardsBalanceResponseGiftcard.setIssuerId(DEFAULT_INTEGER);
        giftCardsBalanceResponseGiftcard.setUserRef(DEFAULT_STRING);
        giftCardsBalanceResponseGiftcard.setMobileNumber(DEFAULT_MSISDN);
        giftCardsBalanceResponseGiftcard.setIssuedAmount(DEFAULT_BIG_DECIMAL);
        giftCardsBalanceResponseGiftcard.setRedeemedAmount(DEFAULT_BIG_DECIMAL);
        giftCardsBalanceResponseGiftcard.setExpiredAmount(DEFAULT_BIG_DECIMAL);
        giftCardsBalanceResponseGiftcard.setBalance(DEFAULT_BIG_DECIMAL);
        giftCardsBalanceResponseGiftcard.setCreateDate(DEFAULT_STRING);
        giftCardsBalanceResponseGiftcard.setExpiryDate(DEFAULT_STRING);
        giftCardsBalanceResponseGiftcard.setCampaignName(DEFAULT_STRING);
        giftCardsBalanceResponseGiftcard.setCampaignType(DEFAULT_STRING);
        giftCardsBalanceResponseGiftcard.setDescription(DEFAULT_STRING);
        giftCardsBalanceResponseGiftcard.setTermsAndConditions(DEFAULT_STRING);
        giftCardsBalanceResponseGiftcard.setWicode(DEFAULT_STRING);
        giftCardsBalanceResponseGiftcard.setStateId(GiftCardsBalanceResponseGiftcard.StateIdEnum.A);
        GiftCardsBalanceResponse giftCardsBalanceResponse = new GiftCardsBalanceResponse();
        giftCardsBalanceResponse.setGiftcard(giftCardsBalanceResponseGiftcard);
        giftCardsBalanceResponse.setResponseCode(Constants.RESPONSE_CODE);
        giftCardsBalanceResponse.setResponseDesc(Constants.RESPONSE_DESC);
        return new ResponseEntity<>(giftCardsBalanceResponse, HttpStatus.OK);
    }

    public static ResponseEntity<GiftCardsRedeemResponse> createGiftCardsRedeemResponse() {
        GiftCardsRedeemResponseToken giftCardsRedeemResponseToken = new GiftCardsRedeemResponseToken();
        giftCardsRedeemResponseToken.setUserRef(DEFAULT_STRING);
        giftCardsRedeemResponseToken.setWiCode(DEFAULT_STRING);
        giftCardsRedeemResponseToken.setCreateDate(DEFAULT_STRING);
        giftCardsRedeemResponseToken.setStateId(GiftCardsRedeemResponseToken.StateIdEnum.A);
        giftCardsRedeemResponseToken.setValidTillDate(DEFAULT_STRING);
        giftCardsRedeemResponseToken.setLastModifiedDate(DEFAULT_STRING);
        GiftCardsRedeemResponse giftCardsRedeemResponse = new GiftCardsRedeemResponse();
        giftCardsRedeemResponse.setToken(giftCardsRedeemResponseToken);
        giftCardsRedeemResponse.setResponseCode(Constants.RESPONSE_CODE);
        giftCardsRedeemResponse.setResponseDesc(Constants.RESPONSE_DESC);
        return new ResponseEntity<>(giftCardsRedeemResponse, HttpStatus.OK);
    }

    public static ResponseEntity<GiftCardsResponse> createGiftCardsResponse() {
        GiftCardsResponseGiftcard giftCardsResponseGiftcard = new GiftCardsResponseGiftcard();
        giftCardsResponseGiftcard.setId(DEFAULT_LONG);
        giftCardsResponseGiftcard.setIssuerId(DEFAULT_INTEGER);
        giftCardsResponseGiftcard.setIssuedAmount(DEFAULT_BIG_DECIMAL);
        giftCardsResponseGiftcard.setRedeemedAmount(DEFAULT_BIG_DECIMAL);
        giftCardsResponseGiftcard.setExpiryAmount(DEFAULT_BIG_DECIMAL);
        giftCardsResponseGiftcard.setBalance(DEFAULT_BIG_DECIMAL);
        giftCardsResponseGiftcard.setCampaignId(DEFAULT_STRING);
        giftCardsResponseGiftcard.setInterfaceIssuerId(DEFAULT_STRING);
        giftCardsResponseGiftcard.setUserRef(DEFAULT_STRING);
        giftCardsResponseGiftcard.setMobileNumber(DEFAULT_STRING);
        giftCardsResponseGiftcard.setCreateDate(DEFAULT_STRING);
        giftCardsResponseGiftcard.setExpiryDate(DEFAULT_STRING);
        giftCardsResponseGiftcard.setCampaignName(DEFAULT_STRING);
        giftCardsResponseGiftcard.setCampaignType(DEFAULT_STRING);
        giftCardsResponseGiftcard.setDescription(DEFAULT_STRING);
        giftCardsResponseGiftcard.setTermsAndConditions(DEFAULT_STRING);
        giftCardsResponseGiftcard.setStateId(GiftCardsResponseGiftcard.StateIdEnum.A);
        giftCardsResponseGiftcard.setWicode(DEFAULT_LONG);
        giftCardsResponseGiftcard.setRedeemFromTime(DEFAULT_STRING);
        giftCardsResponseGiftcard.setRedeemToTime(DEFAULT_STRING);
        GiftCardsResponse giftCardsResponse = new GiftCardsResponse();
        giftCardsResponse.setGiftcard(giftCardsResponseGiftcard);
        giftCardsResponse.setResponseCode(Constants.RESPONSE_CODE);
        giftCardsResponse.setResponseDesc(Constants.RESPONSE_DESC);
        return new ResponseEntity<>(giftCardsResponse, HttpStatus.OK);
    }

    HttpHeaders createHeaders(String serviceName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("allocationService", serviceName);
        return headers;
    }

    @BeforeEach
    public void initTest() {}

    @Transactional
    @Test
    public void issueVoucher() throws Exception {
        createDTO(em, VOUCHER, DEFAULT_CAMPAIGN_NAME);
        restVoucherMockMvc
            .perform(
                post("/api/voucher/allocation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createVoucherAllocationRequest(DEFAULT_CAMPAIGN_NAME)))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.voucherCode").value(DEFAULT_PRODUCT_ID))
            .andExpect(jsonPath("$.voucherDescription").value(DEFAULT_VOUCHER_DESCRIPTION))
            .andExpect(jsonPath("$.voucherType").value(VOUCHER))
            .andExpect(jsonPath("$.voucherCategory").value(DEFAULT_VOUCHER_CATEGORY))
            .andExpect(jsonPath("$.voucherVendor").value(DEFAULT_VOUCHER_VENDOR))
            .andExpect(jsonPath("$.collectPoint").value(DEFAULT_COLLECTION_POINT))
            .andExpect(jsonPath("$.trxId").value(DEFAULT_TRXID))
            .andExpect(jsonPath("$.encryptedYN").value(DEFAULT_ENCRYPTED_YN));
    }

    @Transactional
    @Test
    public void issueVoucherInternal() throws Exception {
        createDTO(em, VOUCHER, DEFAULT_CAMPAIGN_NAME);
        restVoucherMockMvc
            .perform(
                post("/api/voucher/internal/allocation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createVoucherAllocationRequest(DEFAULT_CAMPAIGN_NAME)))
                    .headers(createHeaders(DEFAULT_SERVICE))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.voucherCode").value(DEFAULT_PRODUCT_ID))
            .andExpect(jsonPath("$.voucherDescription").value(DEFAULT_VOUCHER_DESCRIPTION))
            .andExpect(jsonPath("$.voucherType").value(VOUCHER))
            .andExpect(jsonPath("$.voucherCategory").value(DEFAULT_VOUCHER_CATEGORY))
            .andExpect(jsonPath("$.voucherVendor").value(DEFAULT_VOUCHER_VENDOR))
            .andExpect(jsonPath("$.collectPoint").value(DEFAULT_COLLECTION_POINT))
            .andExpect(jsonPath("$.trxId").value(DEFAULT_TRXID))
            .andExpect(jsonPath("$.encryptedYN").value(DEFAULT_ENCRYPTED_YN));
    }

    @Transactional
    @Test
    public void issueVoucherInternalInvalidService() throws Exception {
        createDTO(em, VOUCHER, DEFAULT_CAMPAIGN_NAME);
        restVoucherMockMvc
            .perform(
                post("/api/voucher/internal/allocation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createVoucherAllocationRequest(DEFAULT_CAMPAIGN_NAME)))
                    .headers(createHeaders(INVALID_SERVICE))
            )
            .andExpect(status().isForbidden());
    }

    @Transactional
    @Test
    public void issueVoucherInternalNoHeader() throws Exception {
        createDTO(em, VOUCHER, DEFAULT_CAMPAIGN_NAME);
        restVoucherMockMvc
            .perform(
                post("/api/voucher/internal/allocation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createVoucherAllocationRequest(DEFAULT_CAMPAIGN_NAME)))
            )
            .andExpect(status().isForbidden());
    }

    @Transactional
    @Test
    public void issueGenericVoucher() throws Exception {
        createDTO(em, GENERIC_VOUCHER, DEFAULT_CAMPAIGN_NAME);
        restVoucherMockMvc
            .perform(
                post("/api/voucher/allocation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createVoucherAllocationRequest(DEFAULT_CAMPAIGN_NAME)))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.voucherCode").value(DEFAULT_PRODUCT_ID))
            .andExpect(jsonPath("$.voucherDescription").value(DEFAULT_VOUCHER_DESCRIPTION))
            .andExpect(jsonPath("$.voucherType").value(GENERIC_VOUCHER))
            .andExpect(jsonPath("$.voucherCategory").value(DEFAULT_VOUCHER_CATEGORY))
            .andExpect(jsonPath("$.voucherVendor").value(DEFAULT_VOUCHER_VENDOR))
            .andExpect(jsonPath("$.collectPoint").value(DEFAULT_COLLECTION_POINT))
            .andExpect(jsonPath("$.trxId").value(DEFAULT_TRXID))
            .andExpect(jsonPath("$.encryptedYN").value(DEFAULT_ENCRYPTED_YN));
    }

    @Transactional
    @Test
    public void issueOnlineVoucher() throws Exception {
        createDTO(em, ONLINE_VOUCHER, DEFAULT_CAMPAIGN_NAME);
        given(this.couponsApiClient.issueVoucher(ArgumentMatchers.anyBoolean(), ArgumentMatchers.any()))
            .willReturn(createCouponsResponse());
        restVoucherMockMvc
            .perform(
                post("/api/voucher/allocation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createVoucherAllocationRequest(DEFAULT_CAMPAIGN_NAME)))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.voucherId").value(DEFAULT_LONG))
            .andExpect(jsonPath("$.voucherCode").value(DEFAULT_LONG))
            .andExpect(jsonPath("$.voucherDescription").value(DEFAULT_STRING))
            .andExpect(jsonPath("$.voucherType").value(ONLINE_VOUCHER))
            .andExpect(jsonPath("$.voucherCategory").value(DEFAULT_STRING))
            .andExpect(jsonPath("$.voucherVendor").value(DEFAULT_STRING))
            .andExpect(jsonPath("$.collectPoint").value(DEFAULT_STRING))
            .andExpect(jsonPath("$.trxId").value(DEFAULT_STRING))
            .andExpect(jsonPath("$.encryptedYN").value(DEFAULT_ENCRYPTED_YN));
    }

    @Transactional
    @Test
    public void issueOnlineGiftcard() throws Exception {
        createDTO(em, ONLINE_GIFT_CARD, DEFAULT_CAMPAIGN_NAME);
        given(this.giftcardsDefaultApiClient.updateVoucherToReserved(ArgumentMatchers.anyBoolean(), ArgumentMatchers.any()))
            .willReturn(createGiftCardsResponse());
        restVoucherMockMvc
            .perform(
                post("/api/voucher/allocation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createVoucherAllocationRequest(DEFAULT_CAMPAIGN_NAME)))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.voucherId").value(DEFAULT_LONG))
            .andExpect(jsonPath("$.voucherCode").value(DEFAULT_LONG))
            .andExpect(jsonPath("$.voucherDescription").value(DEFAULT_STRING))
            .andExpect(jsonPath("$.voucherType").value(ONLINE_GIFT_CARD))
            .andExpect(jsonPath("$.voucherCategory").value(DEFAULT_STRING))
            .andExpect(jsonPath("$.voucherVendor").value(DEFAULT_STRING))
            .andExpect(jsonPath("$.collectPoint").value(DEFAULT_STRING))
            .andExpect(jsonPath("$.trxId").value(DEFAULT_STRING))
            .andExpect(jsonPath("$.encryptedYN").value(DEFAULT_ENCRYPTED_YN));
    }

    @Transactional
    @Test
    public void returnVoucher() throws Exception {
        createDTO(em, VOUCHER, DEFAULT_CAMPAIGN_NAME);

        restVoucherMockMvc
            .perform(
                put("/api/voucher/return/{1}", createExistingVoucher(DEFAULT_PRODUCT_ID))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createVoucherReturnRequest()))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.voucherDescription").value(DEFAULT_STRING))
            .andExpect(jsonPath("$.trxId").value(DEFAULT_STRING));
    }

    @Transactional
    @Test
    public void returnGenericVoucher() throws Exception {
        createDTO(em, GENERIC_VOUCHER, DEFAULT_CAMPAIGN_NAME);

        restVoucherMockMvc
            .perform(
                put("/api/voucher/return/{1}", createExistingVoucher(DEFAULT_PRODUCT_ID))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createVoucherReturnRequest()))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.voucherDescription").value(DEFAULT_STRING))
            .andExpect(jsonPath("$.trxId").value(DEFAULT_STRING));
    }

    @Transactional
    @Test
    public void returnOnlineVoucher() throws Exception {
        createDTO(em, ONLINE_VOUCHER, DEFAULT_CAMPAIGN_NAME);
        given(this.couponsApiClient.deleteVoucher(ArgumentMatchers.any())).willReturn(createCouponsDelResponse());

        restVoucherMockMvc
            .perform(
                put("/api/voucher/return/{1}", createExistingVoucher(DEFAULT_PRODUCT_ID))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createVoucherReturnRequest()))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.voucherDescription").value(DEFAULT_STRING))
            .andExpect(jsonPath("$.trxId").value(DEFAULT_STRING));
    }

    @Transactional
    @Test
    public void voucherValidation() throws Exception {
        createDTO(em, VOUCHER, DEFAULT_CAMPAIGN_NAME);
        given(this.couponsApiClient.getVoucher(ArgumentMatchers.any())).willReturn(createCouponsGetResponse());

        restVoucherMockMvc
            .perform(get("/api/voucher/validation/{1}/{2}", DEFAULT_COUPON_ID, DEFAULT_PRODUCT_ID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.couponId").value(DEFAULT_BIG_DECIMAL))
            .andExpect(jsonPath("$.description").value(DEFAULT_STRING))
            .andExpect(jsonPath("$.voucherAmount").value(DEFAULT_BIG_DECIMAL))
            .andExpect(jsonPath("$.status").value("A"))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE))
            .andExpect(jsonPath("$.msisdn").value(DEFAULT_MSISDN));
    }

    @Transactional
    @Test
    public void voucherBalance() throws Exception {
        createDTO(em, VOUCHER, DEFAULT_CAMPAIGN_NAME);
        given(this.giftcardsDefaultApiClient.viewGiftcard(ArgumentMatchers.any())).willReturn(createGiftCardsBalanceResponse());

        restVoucherMockMvc
            .perform(
                get(
                    "/api/voucher/balance/{1}?origin={2}&campaign={3}",
                    createExistingVoucher(DEFAULT_PRODUCT_ID),
                    DEFAULT_ORIGIN,
                    DEFAULT_CAMPAIGN_NAME
                )
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BIG_DECIMAL))
            .andExpect(jsonPath("$.issuedAmount").value(DEFAULT_BIG_DECIMAL))
            .andExpect(jsonPath("$.redeemedAmount").value(DEFAULT_BIG_DECIMAL))
            .andExpect(jsonPath("$.expiredAmount").value(DEFAULT_BIG_DECIMAL))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_STRING));
    }

    @Transactional
    @Test
    public void redeemVoucher() throws Exception {
        createDTO(em, ONLINE_GIFT_CARD, DEFAULT_CAMPAIGN_NAME);
        given(this.giftcardsDefaultApiClient.redeemGiftcard(ArgumentMatchers.any())).willReturn(createGiftCardsRedeemResponse());
        given(this.giftcardsCampaign10ApiClient.redeemGiftcard(ArgumentMatchers.any())).willReturn(createGiftCardsRedeemResponse());

        restVoucherMockMvc
            .perform(
                post("/api/voucher/redemption/{1}", createExistingVoucher(DEFAULT_PRODUCT_ID))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createVoucherRedemptionRequest(DEFAULT_CAMPAIGN_NAME)))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.voucherCode").value(DEFAULT_STRING))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_STRING));
    }
}
