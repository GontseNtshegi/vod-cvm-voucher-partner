package za.co.vodacom.cvm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import za.co.vodacom.cvm.web.rest.TestUtil;

class VPVoucherDefDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VPVoucherDefDTO.class);
        VPVoucherDefDTO vPVoucherDefDTO1 = new VPVoucherDefDTO();
        vPVoucherDefDTO1.setId("1L");
        VPVoucherDefDTO vPVoucherDefDTO2 = new VPVoucherDefDTO();
        assertThat(vPVoucherDefDTO1).isNotEqualTo(vPVoucherDefDTO2);
        vPVoucherDefDTO2.setId(vPVoucherDefDTO1.getId());
        assertThat(vPVoucherDefDTO1).isEqualTo(vPVoucherDefDTO2);
        vPVoucherDefDTO2.setId("2L");
        assertThat(vPVoucherDefDTO1).isNotEqualTo(vPVoucherDefDTO2);
        vPVoucherDefDTO1.setId(null);
        assertThat(vPVoucherDefDTO1).isNotEqualTo(vPVoucherDefDTO2);
    }
}
