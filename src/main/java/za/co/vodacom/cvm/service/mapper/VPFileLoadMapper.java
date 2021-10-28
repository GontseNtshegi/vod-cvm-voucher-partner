package za.co.vodacom.cvm.service.mapper;

import org.mapstruct.*;
import za.co.vodacom.cvm.domain.*;
import za.co.vodacom.cvm.service.dto.VPFileLoadDTO;

/**
 * Mapper for the entity {@link VPFileLoad} and its DTO {@link VPFileLoadDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VPFileLoadMapper extends EntityMapper<VPFileLoadDTO, VPFileLoad> {}
