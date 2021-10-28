package za.co.vodacom.cvm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VPVoucherDefMapperTest {

    private VPVoucherDefMapper vPVoucherDefMapper;

    @BeforeEach
    public void setUp() {
        vPVoucherDefMapper = new VPVoucherDefMapperImpl();
    }
}
