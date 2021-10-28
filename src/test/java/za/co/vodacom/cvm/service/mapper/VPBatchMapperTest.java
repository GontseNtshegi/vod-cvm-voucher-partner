package za.co.vodacom.cvm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VPBatchMapperTest {

    private VPBatchMapper vPBatchMapper;

    @BeforeEach
    public void setUp() {
        vPBatchMapper = new VPBatchMapperImpl();
    }
}
