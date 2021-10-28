package za.co.vodacom.cvm.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.domain.VPVoucherDef;
import za.co.vodacom.cvm.repository.VPVoucherDefRepository;
import za.co.vodacom.cvm.service.VPVoucherDefService;
import za.co.vodacom.cvm.service.dto.VPVoucherDefDTO;
import za.co.vodacom.cvm.service.mapper.VPVoucherDefMapper;

/**
 * Service Implementation for managing {@link VPVoucherDef}.
 */
@Service
@Transactional
public class VPVoucherDefServiceImpl implements VPVoucherDefService {

    private final Logger log = LoggerFactory.getLogger(VPVoucherDefServiceImpl.class);

    private final VPVoucherDefRepository vPVoucherDefRepository;

    private final VPVoucherDefMapper vPVoucherDefMapper;

    public VPVoucherDefServiceImpl(VPVoucherDefRepository vPVoucherDefRepository, VPVoucherDefMapper vPVoucherDefMapper) {
        this.vPVoucherDefRepository = vPVoucherDefRepository;
        this.vPVoucherDefMapper = vPVoucherDefMapper;
    }

    @Override
    public VPVoucherDefDTO save(VPVoucherDefDTO vPVoucherDefDTO) {
        log.debug("Request to save VPVoucherDef : {}", vPVoucherDefDTO);
        VPVoucherDef vPVoucherDef = vPVoucherDefMapper.toEntity(vPVoucherDefDTO);
        vPVoucherDef = vPVoucherDefRepository.save(vPVoucherDef);
        return vPVoucherDefMapper.toDto(vPVoucherDef);
    }

    @Override
    public Optional<VPVoucherDefDTO> partialUpdate(VPVoucherDefDTO vPVoucherDefDTO) {
        log.debug("Request to partially update VPVoucherDef : {}", vPVoucherDefDTO);

        return vPVoucherDefRepository
            .findById(vPVoucherDefDTO.getId())
            .map(existingVPVoucherDef -> {
                vPVoucherDefMapper.partialUpdate(existingVPVoucherDef, vPVoucherDefDTO);

                return existingVPVoucherDef;
            })
            .map(vPVoucherDefRepository::save)
            .map(vPVoucherDefMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VPVoucherDefDTO> findAll() {
        log.debug("Request to get all VPVoucherDefs");
        return vPVoucherDefRepository.findAll().stream().map(vPVoucherDefMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VPVoucherDefDTO> findOne(String id) {
        log.debug("Request to get VPVoucherDef : {}", id);
        return vPVoucherDefRepository.findById(id).map(vPVoucherDefMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete VPVoucherDef : {}", id);
        vPVoucherDefRepository.deleteById(id);
    }

    @Override
    public Optional<VPVoucherDefDTO> findById(String id) {
        log.debug("Request to findByProductIdForUpdate VPVoucherDef : {}", id);
        return vPVoucherDefRepository.findById(id).map(vPVoucherDefMapper::toDto);
    }
}
