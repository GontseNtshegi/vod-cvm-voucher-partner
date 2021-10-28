package za.co.vodacom.cvm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VPCampaignMapperTest {

    private VPCampaignMapper vPCampaignMapper;

    @BeforeEach
    public void setUp() {
        vPCampaignMapper = new VPCampaignMapperImpl();
    }
}
