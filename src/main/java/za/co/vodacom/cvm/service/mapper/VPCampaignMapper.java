package za.co.vodacom.cvm.service.mapper;

import org.mapstruct.*;
import za.co.vodacom.cvm.domain.*;
import za.co.vodacom.cvm.service.dto.VPCampaignDTO;

/**
 * Mapper for the entity {@link VPCampaign} and its DTO {@link VPCampaignDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VPCampaignMapper extends EntityMapper<VPCampaignDTO, VPCampaign> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VPCampaignDTO toDtoId(VPCampaign vPCampaign);
}
