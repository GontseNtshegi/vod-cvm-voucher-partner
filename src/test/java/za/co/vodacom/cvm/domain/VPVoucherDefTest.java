package za.co.vodacom.cvm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import za.co.vodacom.cvm.web.rest.TestUtil;

class VPVoucherDefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VPVoucherDef.class);
        VPVoucherDef vPVoucherDef1 = new VPVoucherDef();
        vPVoucherDef1.setId("1");
        VPVoucherDef vPVoucherDef2 = new VPVoucherDef();
        vPVoucherDef2.setId(vPVoucherDef1.getId());
        assertThat(vPVoucherDef1).isEqualTo(vPVoucherDef2);
        vPVoucherDef2.setId("2");
        assertThat(vPVoucherDef1).isNotEqualTo(vPVoucherDef2);
        vPVoucherDef1.setId(null);
        assertThat(vPVoucherDef1).isNotEqualTo(vPVoucherDef2);
    }
}
