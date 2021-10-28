package za.co.vodacom.cvm.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.domain.VPCampaign;
import za.co.vodacom.cvm.repository.VPCampaignRepository;
import za.co.vodacom.cvm.service.VPCampaignService;
import za.co.vodacom.cvm.service.dto.VPCampaignDTO;
import za.co.vodacom.cvm.service.mapper.VPCampaignMapper;

/**
 * Service Implementation for managing {@link VPCampaign}.
 */
@Service
@Transactional
public class VPCampaignServiceImpl implements VPCampaignService {

    private final Logger log = LoggerFactory.getLogger(VPCampaignServiceImpl.class);

    private final VPCampaignRepository vPCampaignRepository;

    private final VPCampaignMapper vPCampaignMapper;

    public VPCampaignServiceImpl(VPCampaignRepository vPCampaignRepository, VPCampaignMapper vPCampaignMapper) {
        this.vPCampaignRepository = vPCampaignRepository;
        this.vPCampaignMapper = vPCampaignMapper;
    }

    @Override
    public VPCampaignDTO save(VPCampaignDTO vPCampaignDTO) {
        log.debug("Request to save VPCampaign : {}", vPCampaignDTO);
        VPCampaign vPCampaign = vPCampaignMapper.toEntity(vPCampaignDTO);
        vPCampaign = vPCampaignRepository.save(vPCampaign);
        return vPCampaignMapper.toDto(vPCampaign);
    }

    @Override
    public Optional<VPCampaignDTO> partialUpdate(VPCampaignDTO vPCampaignDTO) {
        log.debug("Request to partially update VPCampaign : {}", vPCampaignDTO);

        return vPCampaignRepository
            .findById(vPCampaignDTO.getId())
            .map(existingVPCampaign -> {
                vPCampaignMapper.partialUpdate(existingVPCampaign, vPCampaignDTO);

                return existingVPCampaign;
            })
            .map(vPCampaignRepository::save)
            .map(vPCampaignMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VPCampaignDTO> findAll() {
        log.debug("Request to get all VPCampaigns");
        return vPCampaignRepository.findAll().stream().map(vPCampaignMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VPCampaignDTO> findOne(Long id) {
        log.debug("Request to get VPCampaign : {}", id);
        return vPCampaignRepository.findById(id).map(vPCampaignMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VPCampaign : {}", id);
        vPCampaignRepository.deleteById(id);
    }

    @Override
    public Optional<VPCampaignDTO> findByName(String name) {
        log.debug("Request to find by name in VPCampaign : {}", name);
        return vPCampaignRepository.findByName(name).map(vPCampaignMapper::toDto);
    }
}
