package za.co.vodacom.cvm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import za.co.vodacom.cvm.web.rest.TestUtil;

class VPCampaignTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VPCampaign.class);
        VPCampaign vPCampaign1 = new VPCampaign();
        vPCampaign1.setId(1L);
        VPCampaign vPCampaign2 = new VPCampaign();
        vPCampaign2.setId(vPCampaign1.getId());
        assertThat(vPCampaign1).isEqualTo(vPCampaign2);
        vPCampaign2.setId(2L);
        assertThat(vPCampaign1).isNotEqualTo(vPCampaign2);
        vPCampaign1.setId(null);
        assertThat(vPCampaign1).isNotEqualTo(vPCampaign2);
    }
}
