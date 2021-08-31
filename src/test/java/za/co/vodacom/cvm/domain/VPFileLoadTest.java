package za.co.vodacom.cvm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import za.co.vodacom.cvm.web.rest.TestUtil;

class VPFileLoadTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VPFileLoad.class);
        VPFileLoad vPFileLoad1 = new VPFileLoad();
        vPFileLoad1.setId(1L);
        VPFileLoad vPFileLoad2 = new VPFileLoad();
        vPFileLoad2.setId(vPFileLoad1.getId());
        assertThat(vPFileLoad1).isEqualTo(vPFileLoad2);
        vPFileLoad2.setId(2L);
        assertThat(vPFileLoad1).isNotEqualTo(vPFileLoad2);
        vPFileLoad1.setId(null);
        assertThat(vPFileLoad1).isNotEqualTo(vPFileLoad2);
    }
}
