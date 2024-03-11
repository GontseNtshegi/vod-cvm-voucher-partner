package za.co.vodacom.cvm.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import za.co.vodacom.cvm.IntegrationTest;
import za.co.vodacom.cvm.config.Constants;
import za.co.vodacom.cvm.domain.VPCampaign;
import za.co.vodacom.cvm.domain.VPCampaignVouchers;
import za.co.vodacom.cvm.service.dto.campaign.CampaignProductDTO;
import za.co.vodacom.cvm.service.dto.campaign.QuantityDetailsDTO;
import za.co.vodacom.cvm.web.api.model.LinkDelinkRequest;
import za.co.vodacom.cvm.web.rest.TestUtil;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser(roles = { "VOUCHER_PARTNER" })
class CampaignServiceIT {


    public static final String MEGA_SHAKE = "MegaShake";
    public static final long DEFAULT_ID = 5L;
    private static final String DEFAULT_CAMPAIGN_ID = "21";
    private static final String DEFAULT_STRING = "AAAAAA";
    private static final String DEFAULT_STRINGS_ADD = "CDA216,EXP626";
    private static final String DEFAULT_STRINGS_RMV = "VCS213,WU450,GFT321";
    private static final Integer DEFAULT_INT = 2;
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


//////////////////////////Tests
    private List<VPCampaign> createCampaignList() {
        VPCampaign vpCampaign = new VPCampaign();
        List<VPCampaign> campaignList = new ArrayList<>();
        vpCampaign.setId(DEFAULT_ID);
        vpCampaign.setName(MEGA_SHAKE);
        vpCampaign.setStartDate(LocalDateTime.now().minusMonths(4));
        vpCampaign.setEndDate(LocalDateTime.now().minusMonths(7));
        campaignList.add(vpCampaign);
        return  campaignList;
    }

    private VPCampaign createCampaign() {
        VPCampaign vpCampaign = new VPCampaign();
        vpCampaign.setId(DEFAULT_ID);
        vpCampaign.setName(MEGA_SHAKE);
        vpCampaign.setStartDate(LocalDateTime.now().minusMonths(4));
        vpCampaign.setEndDate(LocalDateTime.now().minusMonths(7));
        return  vpCampaign;
    }

    private VPCampaignVouchers createCampaignVoucher() {
        VPCampaignVouchers campaignVouchers = new VPCampaignVouchers();
        campaignVouchers.setId(DEFAULT_ID);
        campaignVouchers.setActiveYN(Constants.NO);
        campaignVouchers.setCampaignId(Long.valueOf(DEFAULT_CAMPAIGN_ID));
        campaignVouchers.setCreateDate(ZonedDateTime.now().minusMonths(6));
        campaignVouchers.setModifiedDate(ZonedDateTime.now());
        campaignVouchers.setProductId(DEFAULT_STRING);
        return campaignVouchers;
    }

    private List<CampaignProductDTO> createCampaignProductDTO() {
        CampaignProductDTO productDTO = new CampaignProductDTO();
        List<CampaignProductDTO> list = new ArrayList<>();
        productDTO.setId(DEFAULT_ID);
        productDTO.setActiveYN(Constants.YES);
        productDTO.setCampaignId(Long.valueOf(DEFAULT_CAMPAIGN_ID));
        productDTO.setDescription(DEFAULT_STRING);
        productDTO.setproduct_id(String.valueOf(DEFAULT_ID));
        list.add(productDTO);
        return list;
    }


    private List<QuantityDetailsDTO> createQuantityDetailsDto() {
        QuantityDetailsDTO quantityDetailsDTO = new QuantityDetailsDTO();
        List<QuantityDetailsDTO> dtoList = new ArrayList<>();
        quantityDetailsDTO.setCount(DEFAULT_ID);
        quantityDetailsDTO.setDescription(DEFAULT_STRING);
        quantityDetailsDTO.setEndDate(null);
        quantityDetailsDTO.setExpiryDate(null);
        quantityDetailsDTO.setProductDescription(DEFAULT_STRING);
        quantityDetailsDTO.setProductId(DEFAULT_STRING);
        quantityDetailsDTO.setType(DEFAULT_STRING);
        dtoList.add(quantityDetailsDTO);
        return  dtoList;
    }

    private LinkDelinkRequest createLinkDelinkRequest(boolean isRemoveOnly, boolean isBoth,boolean duplicated) {
        LinkDelinkRequest request = new LinkDelinkRequest();
        if (isRemoveOnly){
            request.setAddProducts(null);
            request.setRemoveProducts(DEFAULT_STRINGS_RMV);
        } else if (isBoth) {
            if(duplicated){
                request.setAddProducts(DEFAULT_STRINGS_ADD);
                request.setRemoveProducts(DEFAULT_STRINGS_ADD);
            }
            request.setAddProducts(DEFAULT_STRINGS_ADD);
            request.setRemoveProducts(DEFAULT_STRINGS_RMV);
        }else {
            request.setAddProducts(DEFAULT_STRINGS_ADD);
            request.setRemoveProducts(null);
        }
        return  request;
    }

    ////////////////////////////////////////////////////////////// TESTS

    @Test
    void getCampaignList() throws Exception {
        given(this.vpCampaignService.findAll())
            .willReturn(createCampaignList());
        restMockMvc.perform(get("/api/campaign/list"))
            .andExpect(jsonPath("$.[*].campaignName").value(hasItem((MEGA_SHAKE))))
            .andExpect(jsonPath("$.[*].campaignId").value(hasItem(("5"))))
            .andExpect(status().isOk());
    }

    @Test
    void getCampaignProducts() throws Exception {
        given(this.vpCampaignService.findOne(ArgumentMatchers.any()))
            .willReturn(Optional.of(createCampaign()));
        given(this.vpCampaignVouchersService.findOne(ArgumentMatchers.any()))
            .willReturn(Optional.of(createCampaignVoucher()));
        given(this.vpCampaignService.getCampaignProducts(ArgumentMatchers.any()))
            .willReturn(createCampaignProductDTO());
        restMockMvc.perform(get("/api/campaign/products/{1}"
                ,DEFAULT_CAMPAIGN_ID))
            .andExpect(status().isOk());
    }

    @Test
    void getCampaignProductsNoProducts() throws Exception {
        given(this.vpCampaignService.findOne(ArgumentMatchers.any()))
            .willReturn(Optional.of(createCampaign()));
        given(this.vpCampaignVouchersService.findOne(ArgumentMatchers.any()))
            .willReturn(Optional.of(createCampaignVoucher()));
        restMockMvc.perform(get("/api/campaign/products/{1}"
                ,DEFAULT_CAMPAIGN_ID))
            .andExpect(status().isNotFound());
    }

    @Test
    void getCampaignProductsNoCampaign() throws Exception {
        restMockMvc.perform(get("/api/campaign/products/{1}"
                ,DEFAULT_CAMPAIGN_ID))
            .andExpect(status().isNotFound());
    }

    @Test
    void getQuantities() throws Exception {
        given(this.vpCampaignService.findOne(ArgumentMatchers.any()))
            .willReturn(Optional.of(createCampaign()));
        given(this.vpVouchersService.getVoucherQuantity(ArgumentMatchers.any(),ArgumentMatchers.any()))
            .willReturn(createQuantityDetailsDto());
        restMockMvc.perform(get("/api/campaign/vouchers/quantities/{1}"
                ,DEFAULT_CAMPAIGN_ID))
            .andExpect(status().isOk());
    }

    @Test
    void getQuantitiesNoCampaign() throws Exception {
        restMockMvc.perform(get("/api/campaign/vouchers/quantities/{1}"
                ,DEFAULT_CAMPAIGN_ID))
            .andExpect(status().isNotFound());
    }


    @Test
    void linkDelinkProduct() throws Exception {
        given(this.vpCampaignService.findOne(ArgumentMatchers.any()))
            .willReturn(Optional.of(createCampaign()));
        given(this.voucherDefService.getVouchersByProductId(ArgumentMatchers.any()))
            .willReturn(DEFAULT_INT);
        given(this.vpCampaignVouchersService.getVouchersByCampaign(ArgumentMatchers.any()))
            .willReturn(List.of(createCampaignVoucher()));
        given(this.vpCampaignVouchersService.findVoucherByProductIdandCampaignId(ArgumentMatchers.any(),ArgumentMatchers.any()))
            .willReturn(Optional.of(createCampaignVoucher()));
        given(this.vpCampaignVouchersService.save(ArgumentMatchers.any()))
            .willReturn(createCampaignVoucher());
        restMockMvc.
            perform(put("/api/campaign/products/{1}"
                ,DEFAULT_CAMPAIGN_ID).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(createLinkDelinkRequest(false,true,false))))
            .andExpect(status().isOk());
    }

    @Test
    void linkDelinkProductInvalidCampaign() throws Exception {
        restMockMvc.
            perform(put("/api/campaign/products/{1}"
                ,DEFAULT_CAMPAIGN_ID).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(createLinkDelinkRequest(false,true,false))))
            .andExpect(status().isForbidden());
    }

    @Test
    void linkDelinkProductEmptyAdd() throws Exception {
        given(this.vpCampaignService.findOne(ArgumentMatchers.any()))
            .willReturn(Optional.of(createCampaign()));
        restMockMvc.
            perform(put("/api/campaign/products/{1}"
                ,DEFAULT_CAMPAIGN_ID).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(createLinkDelinkRequest(true,false,false))))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void linkDelinkProductInvalidProducts() throws Exception {
        given(this.vpCampaignService.findOne(ArgumentMatchers.any()))
            .willReturn(Optional.of(createCampaign()));
        given(this.voucherDefService.getVouchersByProductId(ArgumentMatchers.any()))
            .willReturn(0);
        given(this.vpCampaignVouchersService.getVouchersByCampaign(ArgumentMatchers.any()))
            .willReturn(List.of(createCampaignVoucher()));
        given(this.vpCampaignVouchersService.findVoucherByProductIdandCampaignId(ArgumentMatchers.any(),ArgumentMatchers.any()))
            .willReturn(Optional.of(createCampaignVoucher()));
        given(this.vpCampaignVouchersService.save(ArgumentMatchers.any()))
            .willReturn(createCampaignVoucher());
        restMockMvc.
            perform(put("/api/campaign/products/{1}"
                ,DEFAULT_CAMPAIGN_ID).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(createLinkDelinkRequest(false,true,false))))
            .andExpect(status().isForbidden());
    }

    @Test
    void linkDelinkProductNoVouchers() throws Exception {
        given(this.vpCampaignService.findOne(ArgumentMatchers.any()))
            .willReturn(Optional.of(createCampaign()));
        given(this.voucherDefService.getVouchersByProductId(ArgumentMatchers.any()))
            .willReturn(DEFAULT_INT);
        given(this.vpCampaignVouchersService.save(ArgumentMatchers.any()))
            .willReturn(createCampaignVoucher());
        restMockMvc.
            perform(put("/api/campaign/products/{1}"
                ,DEFAULT_CAMPAIGN_ID).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(createLinkDelinkRequest(false,true,false))))
            .andExpect(status().isNotFound());
    }
}
