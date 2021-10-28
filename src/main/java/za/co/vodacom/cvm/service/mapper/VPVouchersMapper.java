package za.co.vodacom.cvm.service.mapper;

import org.mapstruct.*;
import za.co.vodacom.cvm.domain.*;
import za.co.vodacom.cvm.service.dto.VPVouchersDTO;

/**
 * Mapper for the entity {@link VPVouchers} and its DTO {@link VPVouchersDTO}.
 */
@Mapper(componentModel = "spring", uses = { VPVoucherDefMapper.class })
public interface VPVouchersMapper extends EntityMapper<VPVouchersDTO, VPVouchers> {
    //@Mapping(target = "productId", source = "productId", qualifiedByName = "id")
    VPVouchersDTO toDto(VPVouchers s);
}
