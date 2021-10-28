package za.co.vodacom.cvm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import za.co.vodacom.cvm.web.rest.TestUtil;

class VPCampaignVouchersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VPCampaignVouchersDTO.class);
        VPCampaignVouchersDTO vPCampaignVouchersDTO1 = new VPCampaignVouchersDTO();
        vPCampaignVouchersDTO1.setId(1L);
        VPCampaignVouchersDTO vPCampaignVouchersDTO2 = new VPCampaignVouchersDTO();
        assertThat(vPCampaignVouchersDTO1).isNotEqualTo(vPCampaignVouchersDTO2);
        vPCampaignVouchersDTO2.setId(vPCampaignVouchersDTO1.getId());
        assertThat(vPCampaignVouchersDTO1).isEqualTo(vPCampaignVouchersDTO2);
        vPCampaignVouchersDTO2.setId(2L);
        assertThat(vPCampaignVouchersDTO1).isNotEqualTo(vPCampaignVouchersDTO2);
        vPCampaignVouchersDTO1.setId(null);
        assertThat(vPCampaignVouchersDTO1).isNotEqualTo(vPCampaignVouchersDTO2);
    }
}
