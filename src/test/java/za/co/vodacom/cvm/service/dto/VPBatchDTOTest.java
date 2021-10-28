package za.co.vodacom.cvm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import za.co.vodacom.cvm.web.rest.TestUtil;

class VPBatchDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VPBatchDTO.class);
        VPBatchDTO vPBatchDTO1 = new VPBatchDTO();
        vPBatchDTO1.setId(1L);
        VPBatchDTO vPBatchDTO2 = new VPBatchDTO();
        assertThat(vPBatchDTO1).isNotEqualTo(vPBatchDTO2);
        vPBatchDTO2.setId(vPBatchDTO1.getId());
        assertThat(vPBatchDTO1).isEqualTo(vPBatchDTO2);
        vPBatchDTO2.setId(2L);
        assertThat(vPBatchDTO1).isNotEqualTo(vPBatchDTO2);
        vPBatchDTO1.setId(null);
        assertThat(vPBatchDTO1).isNotEqualTo(vPBatchDTO2);
    }
}
