package za.co.vodacom.cvm.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.org.yaml.snakeyaml.scanner.Constant;
import za.co.vodacom.cvm.IntegrationTest;
import za.co.vodacom.cvm.config.ApplicationProperties;
import za.co.vodacom.cvm.config.Constants;
import za.co.vodacom.cvm.domain.VPCampaign;
import za.co.vodacom.cvm.domain.VPCampaignVouchers;
import za.co.vodacom.cvm.domain.VPVoucherDef;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.service.dto.product.Product;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;


@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser(roles = { "VOUCHER_PARTNER" })
class ProductServiceIT {


    public static final String MEGA_SHAKE = "MegaShake";
    public static final long DEFAULT_ID = 5L;
    private static final String DEFAULT_CAMPAIGN_ID = "21";
    private static final String DEFAULT_STRING = "AAAAAA";
    private static final String DEFAULT_STRINGS_ADD = "CDA216,EXP626";
    private static final String DEFAULT_STRINGS_RMV = "VCS213,WU450,GFT321";
    private static final Integer DEFAULT_INT = 2;
    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.now();
    private static final String DEFAULT_CAMPAIGN_NAME = "MegaShake";
    @MockBean
    VPCampaignService vpCampaignService;
    @MockBean
    VPCampaignVouchersService vpCampaignVouchersService;
    @MockBean
    VPVoucherDefService voucherDefService;
    @MockBean
    VPVouchersService vpVouchersService;
    @Autowired
    private MockMvc restMockMvc;
    @Autowired
    ApplicationProperties applicationProperties;
/////////////////////////////////////////TEST METHODS
    private VPVoucherDef createVoucher(boolean isOnlineV,boolean  isGeneric) {
        VPVoucherDef vpVoucher = new VPVoucherDef();
        vpVoucher.setCacheQuantity(DEFAULT_INT);
        vpVoucher.setCollectionPoint(DEFAULT_STRING);
        vpVoucher.setCategory(DEFAULT_STRING);
        vpVoucher.setDescription(DEFAULT_STRING);
        vpVoucher.setEncryptedYN(Constants.YES);
        vpVoucher.setExtId(DEFAULT_STRING);
        vpVoucher.setExtSystem(DEFAULT_STRING);
        vpVoucher.setId(String.valueOf(DEFAULT_ID));
        vpVoucher.setModifiedDate(DEFAULT_DATE);
        vpVoucher.setTemplateId(DEFAULT_STRING);
        vpVoucher.setValidityPeriod(DEFAULT_INT);
        vpVoucher.setVendor(DEFAULT_STRING);

        if(isOnlineV){
            vpVoucher.setType(Constants.ONLINE_VOUCHER);
        }else if(isGeneric){
            vpVoucher.setType(Constants.GENERIC_VOUCHER);
        }else {
            vpVoucher.setType(Constants.VOUCHER);
        }
        return vpVoucher;
    }

    private VPCampaign createCampaign() {
        VPCampaign campaign = new VPCampaign();
        campaign.setId(Long.parseLong(DEFAULT_CAMPAIGN_ID));
        campaign.setName(DEFAULT_CAMPAIGN_NAME);
        campaign.setStartDate(LocalDateTime.now().minusMonths(20));
        campaign.setEndDate(LocalDateTime.now().plusMonths(32));
        return campaign;
    }

    private VPCampaignVouchers createVPCampaignVouchers() {
        VPCampaignVouchers campaignVouchers = new VPCampaignVouchers();
        campaignVouchers.setActiveYN(Constants.NO);
        campaignVouchers.setCampaignId(DEFAULT_ID);
        campaignVouchers.setCreateDate(DEFAULT_DATE.minusMonths(12));
        campaignVouchers.setId(DEFAULT_ID);
        campaignVouchers.setModifiedDate(DEFAULT_DATE);
        campaignVouchers.setProductId(DEFAULT_STRING);
        return campaignVouchers;
    }


    private Product createProductDto() {
        Product vProduct = new Product();
        vProduct.setCount(Long.valueOf(DEFAULT_INT));
        vProduct.setMinEndDateTime(DEFAULT_DATE.plusMonths(5));
        return vProduct;
    }

/////////////////////////////////////////TESTS
    @Test
    void getProductList() throws Exception {
        given(this.voucherDefService.getAll())
            .willReturn(Optional.of(List.of(createVoucher(false, false))));
        restMockMvc.perform(
            get("/api/product/list")
        ).andExpect(status().isOk());
    }

    @Test
    void validateVoucher() throws Exception{
        given(this.vpCampaignService.findByName(ArgumentMatchers.anyString()))
            .willReturn(Optional.of(createCampaign()));
        given(this.vpCampaignVouchersService.findByProductIdAndCampaignIdAndActiveYN(ArgumentMatchers.any(),ArgumentMatchers.any(),ArgumentMatchers.any()))
            .willReturn(Optional.of(createVPCampaignVouchers()));
        given(this.voucherDefService.findOne(ArgumentMatchers.any()))
            .willReturn(Optional.of(createVoucher(false,false)));
        given(this.vpVouchersService.getValidVoucherForProduct(ArgumentMatchers.anyString()))
            .willReturn(Optional.of(createProductDto()));
        restMockMvc.perform(
            get("/api/product/validation/{1}?campaign={2}&origin={3}/",DEFAULT_ID,DEFAULT_CAMPAIGN_ID,Constants.SYSTEM)
        ).andExpect(status().isOk());
    }

    @Test
    void validateVoucherOnline() throws Exception{
        given(this.vpCampaignService.findByName(ArgumentMatchers.anyString()))
            .willReturn(Optional.of(createCampaign()));
        given(this.vpCampaignVouchersService.findByProductIdAndCampaignIdAndActiveYN(ArgumentMatchers.any(),ArgumentMatchers.any(),ArgumentMatchers.any()))
            .willReturn(Optional.of(createVPCampaignVouchers()));
        given(this.voucherDefService.findOne(ArgumentMatchers.any()))
            .willReturn(Optional.of(createVoucher(true,false)));
        given(this.vpVouchersService.getValidVoucherForProduct(ArgumentMatchers.anyString()))
            .willReturn(Optional.of(createProductDto()));
        restMockMvc.perform(
            get("/api/product/validation/{1}?campaign={2}&origin={3}/",DEFAULT_ID,DEFAULT_CAMPAIGN_ID,Constants.SYSTEM)
        ).andExpect(status().isOk());
    }

    @Test
    void validateVoucherGeneric() throws Exception{
        given(this.vpCampaignService.findByName(ArgumentMatchers.anyString()))
            .willReturn(Optional.of(createCampaign()));
        given(this.vpCampaignVouchersService.findByProductIdAndCampaignIdAndActiveYN(ArgumentMatchers.any(),ArgumentMatchers.any(),ArgumentMatchers.any()))
            .willReturn(Optional.of(createVPCampaignVouchers()));
        given(this.voucherDefService.findOne(ArgumentMatchers.any()))
            .willReturn(Optional.of(createVoucher(false,true)));
        given(this.vpVouchersService.getValidVoucherForProduct(ArgumentMatchers.anyString()))
            .willReturn(Optional.of(createProductDto()));
        restMockMvc.perform(
            get("/api/product/validation/{1}?campaign={2}&origin={3}/",DEFAULT_ID,DEFAULT_CAMPAIGN_ID,Constants.SYSTEM)
        ).andExpect(status().isOk());
    }

    @Test
    void validateVoucherInvalidCampaign() throws Exception{
        restMockMvc.perform(
            get("/api/product/validation/{1}?campaign={2}&origin={3}/",DEFAULT_ID,DEFAULT_CAMPAIGN_ID,Constants.SYSTEM)
        ).andExpect(status().isNotFound());
    }

    @Test
    void validateVoucherInvalidProduct() throws Exception{
        given(this.vpCampaignService.findByName(ArgumentMatchers.anyString()))
            .willReturn(Optional.of(createCampaign()));
        restMockMvc.perform(
            get("/api/product/validation/{1}?campaign={2}&origin={3}/",DEFAULT_ID,DEFAULT_CAMPAIGN_ID,Constants.SYSTEM)
        ).andExpect(status().isNotFound());
    }

}
