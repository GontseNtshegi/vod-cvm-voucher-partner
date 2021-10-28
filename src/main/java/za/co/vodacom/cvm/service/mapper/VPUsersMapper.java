package za.co.vodacom.cvm.service.mapper;

import org.mapstruct.*;
import za.co.vodacom.cvm.domain.*;
import za.co.vodacom.cvm.service.dto.VPUsersDTO;

/**
 * Mapper for the entity {@link VPUsers} and its DTO {@link VPUsersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VPUsersMapper extends EntityMapper<VPUsersDTO, VPUsers> {}
