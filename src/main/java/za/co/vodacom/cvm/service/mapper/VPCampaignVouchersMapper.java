package za.co.vodacom.cvm.service.mapper;

import org.mapstruct.*;
import za.co.vodacom.cvm.domain.*;
import za.co.vodacom.cvm.service.dto.VPCampaignVouchersDTO;

/**
 * Mapper for the entity {@link VPCampaignVouchers} and its DTO {@link VPCampaignVouchersDTO}.
 */
@Mapper(componentModel = "spring", uses = { VPCampaignMapper.class, VPVoucherDefMapper.class })
public interface VPCampaignVouchersMapper extends EntityMapper<VPCampaignVouchersDTO, VPCampaignVouchers> {
    //@Mapping(target = "campaignId", source = "campaignId", qualifiedByName = "id")
    //@Mapping(target = "productId", source = "productId", qualifiedByName = "id")
    VPCampaignVouchersDTO toDto(VPCampaignVouchers s);
}
