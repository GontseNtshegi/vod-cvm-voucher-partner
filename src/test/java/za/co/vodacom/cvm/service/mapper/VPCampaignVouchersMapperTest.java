package za.co.vodacom.cvm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VPCampaignVouchersMapperTest {

    private VPCampaignVouchersMapper vPCampaignVouchersMapper;

    @BeforeEach
    public void setUp() {
        vPCampaignVouchersMapper = new VPCampaignVouchersMapperImpl();
    }
}
