package za.co.vodacom.cvm.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.domain.VPBatch;
import za.co.vodacom.cvm.repository.VPBatchRepository;
import za.co.vodacom.cvm.service.VPBatchService;
import za.co.vodacom.cvm.service.dto.VPBatchDTO;
import za.co.vodacom.cvm.service.mapper.VPBatchMapper;

/**
 * Service Implementation for managing {@link VPBatch}.
 */
@Service
@Transactional
public class VPBatchServiceImpl implements VPBatchService {

    private final Logger log = LoggerFactory.getLogger(VPBatchServiceImpl.class);

    private final VPBatchRepository vPBatchRepository;

    private final VPBatchMapper vPBatchMapper;

    public VPBatchServiceImpl(VPBatchRepository vPBatchRepository, VPBatchMapper vPBatchMapper) {
        this.vPBatchRepository = vPBatchRepository;
        this.vPBatchMapper = vPBatchMapper;
    }

    @Override
    public VPBatchDTO save(VPBatchDTO vPBatchDTO) {
        log.debug("Request to save VPBatch : {}", vPBatchDTO);
        VPBatch vPBatch = vPBatchMapper.toEntity(vPBatchDTO);
        vPBatch = vPBatchRepository.save(vPBatch);
        return vPBatchMapper.toDto(vPBatch);
    }

    @Override
    public Optional<VPBatchDTO> partialUpdate(VPBatchDTO vPBatchDTO) {
        log.debug("Request to partially update VPBatch : {}", vPBatchDTO);

        return vPBatchRepository
            .findById(vPBatchDTO.getId())
            .map(existingVPBatch -> {
                vPBatchMapper.partialUpdate(existingVPBatch, vPBatchDTO);

                return existingVPBatch;
            })
            .map(vPBatchRepository::save)
            .map(vPBatchMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VPBatchDTO> findAll() {
        log.debug("Request to get all VPBatches");
        return vPBatchRepository.findAll().stream().map(vPBatchMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VPBatchDTO> findOne(Long id) {
        log.debug("Request to get VPBatch : {}", id);
        return vPBatchRepository.findById(id).map(vPBatchMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VPBatch : {}", id);
        vPBatchRepository.deleteById(id);
    }
}
