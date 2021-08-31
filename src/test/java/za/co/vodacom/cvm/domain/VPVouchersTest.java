package za.co.vodacom.cvm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import za.co.vodacom.cvm.web.rest.TestUtil;

class VPVouchersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VPVouchers.class);
        VPVouchers vPVouchers1 = new VPVouchers();
        vPVouchers1.setId(1L);
        VPVouchers vPVouchers2 = new VPVouchers();
        vPVouchers2.setId(vPVouchers1.getId());
        assertThat(vPVouchers1).isEqualTo(vPVouchers2);
        vPVouchers2.setId(2L);
        assertThat(vPVouchers1).isNotEqualTo(vPVouchers2);
        vPVouchers1.setId(null);
        assertThat(vPVouchers1).isNotEqualTo(vPVouchers2);
    }
}
