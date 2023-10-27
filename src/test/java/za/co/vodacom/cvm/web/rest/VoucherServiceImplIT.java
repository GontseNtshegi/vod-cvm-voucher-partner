package za.co.vodacom.cvm.web.rest;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static za.co.vodacom.cvm.web.rest.TestUtil.sameInstant;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.IntegrationTest;
import za.co.vodacom.cvm.domain.VPCampaign;
import za.co.vodacom.cvm.domain.VPCampaignVouchers;
import za.co.vodacom.cvm.domain.VPVoucherDef;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.repository.VPCampaignRepository;
import za.co.vodacom.cvm.repository.VPCampaignVouchersRepository;
import za.co.vodacom.cvm.repository.VPVoucherDefRepository;
import za.co.vodacom.cvm.repository.VPVouchersRepository;
import za.co.vodacom.cvm.web.api.model.VoucherAllocationRequest;
import za.co.vodacom.cvm.web.api.model.VoucherReturnRequest;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class VoucherServiceImplIT {

    private static final String ORIGIN = "Unit Test";
    private static final String PRODUCT_ID = "MPI_224";
    private static final String PRODUCT_ID2 = "VCO_018";
    private static final VoucherAllocationRequest.SubIdTypeEnum SUB_ID_TYPE = VoucherAllocationRequest.SubIdTypeEnum.OM;
    private static final String SUB_ID = "/api/voucher";
    private static final String MSISDN = "276545900006";
    private static final String CAMPAIGN = "MegaShake";
    private static final String TRXID = "Gontse-x-x-1-p";
    private static final BigDecimal COST = BigDecimal.TEN;
    private static final BigDecimal VALUE = BigDecimal.ONE;

    private static final String VOUCHER_API_URL = "/api/voucher";
    private static final String ALLOCATION_API_URL = VOUCHER_API_URL + "/allocation";
    private static final String RETURN_API_URL = VOUCHER_API_URL + "/return/{voucherId}";
    private static final String PRODUCT_API_URL = "/api/product";
    private static final String VALIDATION_API_URL = PRODUCT_API_URL + "/validation/{productId}";

    private VoucherAllocationRequest voucherAllocationRequest1;
    private VoucherReturnRequest voucherReturnRequest;

    private VPVouchers voucher1;
    private VPVouchers voucher2;

    private VPCampaign vpCampaign;

    private VPCampaignVouchers vpCampaignVouchers1;
    private VPCampaignVouchers vpCampaignVouchers2;

    private VPVoucherDef vpVoucherDef1;
    private VPVoucherDef vpVoucherDef2;

    @Autowired
    VPCampaignRepository vpCampaignRepository;

    @Autowired
    VPVouchersRepository vpVouchersRepository;

    @Autowired
    VPCampaignVouchersRepository vpCampaignVouchersRepository;

    @Autowired
    VPVoucherDefRepository vpVoucherDefRepository;

    @Autowired
    private MockMvc restVoucherMockMvc;

    public static VoucherAllocationRequest createVoucherAllocationRequest() {
        VoucherAllocationRequest voucherAllocationRequest = new VoucherAllocationRequest()
            .origin(ORIGIN)
            .productId(PRODUCT_ID)
            .subIdType(SUB_ID_TYPE)
            .subId(SUB_ID)
            .msisdn(MSISDN)
            .campaign(CAMPAIGN)
            .trxId(TRXID)
            .cost(COST)
            .value(VALUE);

        return voucherAllocationRequest;
    }

    public static VPVouchers createVouchersEntity1() {
        VPVouchers vpVouchers = new VPVouchers()
            .batchId(1)
            .collectionPoint("https://bit.ly/2YrRxuF")
            .createDate(OffsetDateTime.now().toZonedDateTime())
            .description("New Balance Shoes")
            .endDate(OffsetDateTime.now().toZonedDateTime().plusDays(7L))
            .expiryDate(OffsetDateTime.now().toZonedDateTime().plusDays(7L))
            .fileId(1)
            .issuedDate(null)
            .productId("MPI_224")
            .quantity(1)
            .reversedDate(null)
            .sourceTrxid(null)
            .startDate(OffsetDateTime.now().toZonedDateTime())
            .voucherCode("856237846");

        return vpVouchers;
    }

    public static VPVouchers createVouchersEntity2() {
        VPVouchers vpVouchers = new VPVouchers()
            .batchId(1)
            .collectionPoint("https://bit.ly/2YrRxuF")
            .createDate(OffsetDateTime.now().toZonedDateTime())
            .description("New Balance Shoes")
            .endDate(OffsetDateTime.now().toZonedDateTime().plusDays(7L))
            .expiryDate(OffsetDateTime.now().toZonedDateTime().plusDays(7L))
            .fileId(1)
            .issuedDate(null)
            .productId("MPI_224")
            .quantity(1)
            .reversedDate(null)
            .sourceTrxid(null)
            .startDate(OffsetDateTime.now().toZonedDateTime())
            .voucherCode("856237846");

        return vpVouchers;
    }

    public static VPCampaign createCampaign() {
        VPCampaign vpCampaign = new VPCampaign()
            .endDate(LocalDateTime.from(OffsetDateTime.now().toZonedDateTime().plusDays(7L)))
            .id(21L)
            .name("MegaShake")
            .startDate(LocalDateTime.from(OffsetDateTime.now().toZonedDateTime()));

        return vpCampaign;
    }

    public static VPVoucherDef createVPVoucherDef1() {
        VPVoucherDef vpVoucherDef = new VPVoucherDef()
            .cacheQuantity(0)
            .category("Lifestyle")
            .description("New Balance Arishi Running Shoes")
            .encryptedYN("N")
            .extId(null)
            .extSystem("Flook")
            .modifiedDate(OffsetDateTime.now().toZonedDateTime())
            .id("MPI_224")
            .templateId("COMMIT_FLOOK")
            .type("Voucher")
            .validityPeriod(30)
            .vendor("Flook");

        return vpVoucherDef;
    }

    public static VPVoucherDef createVPVoucherDef2() {
        VPVoucherDef vpVoucherDef = new VPVoucherDef()
            .cacheQuantity(0)
            .category("Food")
            .description("a Small coffee (Americano or Cappuccino) from Kauai")
            .encryptedYN("N")
            .extId(null)
            .extSystem("Kauai")
            .modifiedDate(OffsetDateTime.now().toZonedDateTime())
            .id("VCO_018")
            .templateId("COMMIT_FLOOK")
            .type("OnlineVoucher")
            .validityPeriod(7)
            .vendor("Kauai");

        return vpVoucherDef;
    }

    public static VPCampaignVouchers createVPCampaignVouchers1() {
        VPCampaignVouchers vpCampaignVouchers = new VPCampaignVouchers()
            .campaignId(21L)
            .activeYN("Y")
            .createDate(OffsetDateTime.now().toZonedDateTime())
            .modifiedDate(OffsetDateTime.now().toZonedDateTime())
            .productId("MPI_224");

        return vpCampaignVouchers;
    }

    public static VPCampaignVouchers createVPCampaignVouchers2() {
        VPCampaignVouchers vpCampaignVouchers = new VPCampaignVouchers()
            .campaignId(21L)
            .activeYN("Y")
            .createDate(OffsetDateTime.now().toZonedDateTime())
            .modifiedDate(OffsetDateTime.now().toZonedDateTime())
            .productId("VCO_018");

        return vpCampaignVouchers;
    }

    @BeforeEach
    public void initTest() {
        voucherAllocationRequest1 = createVoucherAllocationRequest();
        voucher1 = createVouchersEntity1();
        voucher2 = createVouchersEntity2();
        vpCampaign = createCampaign();
        vpCampaignVouchers1 = createVPCampaignVouchers1();
        vpCampaignVouchers2 = createVPCampaignVouchers2();
        vpVoucherDef1 = createVPVoucherDef1();
        vpVoucherDef2 = createVPVoucherDef2();
    }

    @Transactional
    @Test
    public void issueVoucher() throws Exception {
        //Initialize database
        vpCampaignRepository.saveAndFlush(vpCampaign);

        VPVoucherDef vpVouchersDef1 = vpVoucherDefRepository.saveAndFlush(vpVoucherDef1);
        //vpVoucherDefRepository.saveAndFlush(vpVoucherDef2);

        vpCampaignVouchersRepository.saveAndFlush(vpCampaignVouchers1);
        vpCampaignVouchersRepository.saveAndFlush(vpCampaignVouchers2);

        VPVouchers vPVouchers1 = vpVouchersRepository.saveAndFlush(voucher1);
        //vpVouchersRepository.saveAndFlush(voucher2);

        restVoucherMockMvc
            .perform(
                post(ALLOCATION_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voucherAllocationRequest1))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].voucherId").value(hasItem(vPVouchers1.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherCode").value(hasItem(vPVouchers1.getVoucherCode())))
            .andExpect(jsonPath("$.[*].voucherDescription").value(hasItem(vPVouchers1.getDescription())))
            .andExpect(jsonPath("$.[*].voucherType").value(hasItem(vpVouchersDef1.getType())))
            .andExpect(jsonPath("$.[*].voucherCategory").value(hasItem(vpVouchersDef1.getCategory())))
            .andExpect(jsonPath("$.[*].voucherVendor").value(hasItem(vpVouchersDef1.getVendor())))
            .andExpect(jsonPath("$.[*].collectPoint").value(hasItem(vPVouchers1.getCollectionPoint())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(sameInstant(vPVouchers1.getExpiryDate()))))
            .andExpect(jsonPath("$.[*].trxId").value(hasItem(voucherAllocationRequest1.getTrxId())));
    }

    @Test
    public void issueOnlineVoucher() throws Exception {
        voucherAllocationRequest1.setProductId(PRODUCT_ID2);
        restVoucherMockMvc
            .perform(
                post(ALLOCATION_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voucherAllocationRequest1))
            )
            .andExpect(status().isOk());
    }

    @Test
    public void returnVoucher() throws Exception {
        restVoucherMockMvc
            .perform(
                post(ALLOCATION_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voucherAllocationRequest1))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        //            .andExpect(jsonPath("$.[*].voucherId").value(hasItem(vPVouchers.getId().intValue())))
        //            .andExpect(jsonPath("$.[*].voucherDescription").value(hasItem(DEFAULT_BATCH_ID)))
        //            .andExpect(jsonPath("$.[*].trxId").value(hasItem(DEFAULT_FILE_ID)))

    }
}
