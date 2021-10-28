package za.co.vodacom.cvm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import za.co.vodacom.cvm.web.rest.TestUtil;

class VPVouchersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VPVouchersDTO.class);
        VPVouchersDTO vPVouchersDTO1 = new VPVouchersDTO();
        vPVouchersDTO1.setId(1L);
        VPVouchersDTO vPVouchersDTO2 = new VPVouchersDTO();
        assertThat(vPVouchersDTO1).isNotEqualTo(vPVouchersDTO2);
        vPVouchersDTO2.setId(vPVouchersDTO1.getId());
        assertThat(vPVouchersDTO1).isEqualTo(vPVouchersDTO2);
        vPVouchersDTO2.setId(2L);
        assertThat(vPVouchersDTO1).isNotEqualTo(vPVouchersDTO2);
        vPVouchersDTO1.setId(null);
        assertThat(vPVouchersDTO1).isNotEqualTo(vPVouchersDTO2);
    }
}
