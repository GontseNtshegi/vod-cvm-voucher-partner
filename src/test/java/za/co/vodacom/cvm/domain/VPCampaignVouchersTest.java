package za.co.vodacom.cvm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import za.co.vodacom.cvm.web.rest.TestUtil;

class VPCampaignVouchersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VPCampaignVouchers.class);
        VPCampaignVouchers vPCampaignVouchers1 = new VPCampaignVouchers();
        vPCampaignVouchers1.setId(1L);
        VPCampaignVouchers vPCampaignVouchers2 = new VPCampaignVouchers();
        vPCampaignVouchers2.setId(vPCampaignVouchers1.getId());
        assertThat(vPCampaignVouchers1).isEqualTo(vPCampaignVouchers2);
        vPCampaignVouchers2.setId(2L);
        assertThat(vPCampaignVouchers1).isNotEqualTo(vPCampaignVouchers2);
        vPCampaignVouchers1.setId(null);
        assertThat(vPCampaignVouchers1).isNotEqualTo(vPCampaignVouchers2);
    }
}
