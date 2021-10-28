package za.co.vodacom.cvm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import za.co.vodacom.cvm.web.rest.TestUtil;

class VPUsersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VPUsersDTO.class);
        VPUsersDTO vPUsersDTO1 = new VPUsersDTO();
        vPUsersDTO1.setId(1L);
        VPUsersDTO vPUsersDTO2 = new VPUsersDTO();
        assertThat(vPUsersDTO1).isNotEqualTo(vPUsersDTO2);
        vPUsersDTO2.setId(vPUsersDTO1.getId());
        assertThat(vPUsersDTO1).isEqualTo(vPUsersDTO2);
        vPUsersDTO2.setId(2L);
        assertThat(vPUsersDTO1).isNotEqualTo(vPUsersDTO2);
        vPUsersDTO1.setId(null);
        assertThat(vPUsersDTO1).isNotEqualTo(vPUsersDTO2);
    }
}
