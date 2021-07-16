package za.co.vodacom.cvm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import za.co.vodacom.cvm.web.rest.TestUtil;

class VPBatchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VPBatch.class);
        VPBatch vPBatch1 = new VPBatch();
        vPBatch1.setId(1L);
        VPBatch vPBatch2 = new VPBatch();
        vPBatch2.setId(vPBatch1.getId());
        assertThat(vPBatch1).isEqualTo(vPBatch2);
        vPBatch2.setId(2L);
        assertThat(vPBatch1).isNotEqualTo(vPBatch2);
        vPBatch1.setId(null);
        assertThat(vPBatch1).isNotEqualTo(vPBatch2);
    }
}
