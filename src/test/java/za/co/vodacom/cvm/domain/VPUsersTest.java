package za.co.vodacom.cvm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import za.co.vodacom.cvm.web.rest.TestUtil;

class VPUsersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VPUsers.class);
        VPUsers vPUsers1 = new VPUsers();
        vPUsers1.setId("1");
        VPUsers vPUsers2 = new VPUsers();
        vPUsers2.setId(vPUsers1.getId());
        assertThat(vPUsers1).isEqualTo(vPUsers2);
        vPUsers2.setId("2");
        assertThat(vPUsers1).isNotEqualTo(vPUsers2);
        vPUsers1.setId(null);
        assertThat(vPUsers1).isNotEqualTo(vPUsers2);
    }
}
