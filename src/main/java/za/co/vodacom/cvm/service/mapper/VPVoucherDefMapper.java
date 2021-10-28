package za.co.vodacom.cvm.service.mapper;

import org.mapstruct.*;
import za.co.vodacom.cvm.domain.*;
import za.co.vodacom.cvm.service.dto.VPVoucherDefDTO;

/**
 * Mapper for the entity {@link VPVoucherDef} and its DTO {@link VPVoucherDefDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VPVoucherDefMapper extends EntityMapper<VPVoucherDefDTO, VPVoucherDef> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VPVoucherDefDTO toDtoId(VPVoucherDef vPVoucherDef);
}
