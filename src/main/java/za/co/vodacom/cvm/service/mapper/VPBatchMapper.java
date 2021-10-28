package za.co.vodacom.cvm.service.mapper;

import org.mapstruct.*;
import za.co.vodacom.cvm.domain.*;
import za.co.vodacom.cvm.service.dto.VPBatchDTO;

/**
 * Mapper for the entity {@link VPBatch} and its DTO {@link VPBatchDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VPBatchMapper extends EntityMapper<VPBatchDTO, VPBatch> {}
