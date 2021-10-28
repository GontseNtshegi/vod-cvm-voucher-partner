package za.co.vodacom.cvm.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.domain.VPFileLoad;
import za.co.vodacom.cvm.repository.VPFileLoadRepository;
import za.co.vodacom.cvm.service.VPFileLoadService;
import za.co.vodacom.cvm.service.dto.VPFileLoadDTO;
import za.co.vodacom.cvm.service.mapper.VPFileLoadMapper;

/**
 * Service Implementation for managing {@link VPFileLoad}.
 */
@Service
@Transactional
public class VPFileLoadServiceImpl implements VPFileLoadService {

    private final Logger log = LoggerFactory.getLogger(VPFileLoadServiceImpl.class);

    private final VPFileLoadRepository vPFileLoadRepository;

    private final VPFileLoadMapper vPFileLoadMapper;

    public VPFileLoadServiceImpl(VPFileLoadRepository vPFileLoadRepository, VPFileLoadMapper vPFileLoadMapper) {
        this.vPFileLoadRepository = vPFileLoadRepository;
        this.vPFileLoadMapper = vPFileLoadMapper;
    }

    @Override
    public VPFileLoadDTO save(VPFileLoadDTO vPFileLoadDTO) {
        log.debug("Request to save VPFileLoad : {}", vPFileLoadDTO);
        VPFileLoad vPFileLoad = vPFileLoadMapper.toEntity(vPFileLoadDTO);
        vPFileLoad = vPFileLoadRepository.save(vPFileLoad);
        return vPFileLoadMapper.toDto(vPFileLoad);
    }

    @Override
    public Optional<VPFileLoadDTO> partialUpdate(VPFileLoadDTO vPFileLoadDTO) {
        log.debug("Request to partially update VPFileLoad : {}", vPFileLoadDTO);

        return vPFileLoadRepository
            .findById(vPFileLoadDTO.getId())
            .map(existingVPFileLoad -> {
                vPFileLoadMapper.partialUpdate(existingVPFileLoad, vPFileLoadDTO);

                return existingVPFileLoad;
            })
            .map(vPFileLoadRepository::save)
            .map(vPFileLoadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VPFileLoadDTO> findAll() {
        log.debug("Request to get all VPFileLoads");
        return vPFileLoadRepository.findAll().stream().map(vPFileLoadMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VPFileLoadDTO> findOne(Long id) {
        log.debug("Request to get VPFileLoad : {}", id);
        return vPFileLoadRepository.findById(id).map(vPFileLoadMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VPFileLoad : {}", id);
        vPFileLoadRepository.deleteById(id);
    }
}
