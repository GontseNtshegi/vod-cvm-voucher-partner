package za.co.vodacom.cvm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import za.co.vodacom.cvm.web.rest.TestUtil;

class VPFileLoadDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VPFileLoadDTO.class);
        VPFileLoadDTO vPFileLoadDTO1 = new VPFileLoadDTO();
        vPFileLoadDTO1.setId(1L);
        VPFileLoadDTO vPFileLoadDTO2 = new VPFileLoadDTO();
        assertThat(vPFileLoadDTO1).isNotEqualTo(vPFileLoadDTO2);
        vPFileLoadDTO2.setId(vPFileLoadDTO1.getId());
        assertThat(vPFileLoadDTO1).isEqualTo(vPFileLoadDTO2);
        vPFileLoadDTO2.setId(2L);
        assertThat(vPFileLoadDTO1).isNotEqualTo(vPFileLoadDTO2);
        vPFileLoadDTO1.setId(null);
        assertThat(vPFileLoadDTO1).isNotEqualTo(vPFileLoadDTO2);
    }
}
