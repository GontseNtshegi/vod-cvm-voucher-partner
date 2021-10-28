package za.co.vodacom.cvm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import za.co.vodacom.cvm.web.rest.TestUtil;

class VPCampaignDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VPCampaignDTO.class);
        VPCampaignDTO vPCampaignDTO1 = new VPCampaignDTO();
        vPCampaignDTO1.setId(1L);
        VPCampaignDTO vPCampaignDTO2 = new VPCampaignDTO();
        assertThat(vPCampaignDTO1).isNotEqualTo(vPCampaignDTO2);
        vPCampaignDTO2.setId(vPCampaignDTO1.getId());
        assertThat(vPCampaignDTO1).isEqualTo(vPCampaignDTO2);
        vPCampaignDTO2.setId(2L);
        assertThat(vPCampaignDTO1).isNotEqualTo(vPCampaignDTO2);
        vPCampaignDTO1.setId(null);
        assertThat(vPCampaignDTO1).isNotEqualTo(vPCampaignDTO2);
    }
}
