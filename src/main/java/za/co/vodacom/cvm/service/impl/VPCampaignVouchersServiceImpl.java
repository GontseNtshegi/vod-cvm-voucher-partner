package za.co.vodacom.cvm.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.domain.VPCampaignVouchers;
import za.co.vodacom.cvm.repository.VPCampaignVouchersRepository;
import za.co.vodacom.cvm.service.VPCampaignVouchersService;
import za.co.vodacom.cvm.service.dto.VPCampaignVouchersDTO;
import za.co.vodacom.cvm.service.mapper.VPCampaignVouchersMapper;

/**
 * Service Implementation for managing {@link VPCampaignVouchers}.
 */
@Service
@Transactional
public class VPCampaignVouchersServiceImpl implements VPCampaignVouchersService {

    private final Logger log = LoggerFactory.getLogger(VPCampaignVouchersServiceImpl.class);

    private final VPCampaignVouchersRepository vPCampaignVouchersRepository;

    private final VPCampaignVouchersMapper vPCampaignVouchersMapper;

    public VPCampaignVouchersServiceImpl(
        VPCampaignVouchersRepository vPCampaignVouchersRepository,
        VPCampaignVouchersMapper vPCampaignVouchersMapper
    ) {
        this.vPCampaignVouchersRepository = vPCampaignVouchersRepository;
        this.vPCampaignVouchersMapper = vPCampaignVouchersMapper;
    }

    @Override
    public VPCampaignVouchersDTO save(VPCampaignVouchersDTO vPCampaignVouchersDTO) {
        log.debug("Request to save VPCampaignVouchers : {}", vPCampaignVouchersDTO);
        VPCampaignVouchers vPCampaignVouchers = vPCampaignVouchersMapper.toEntity(vPCampaignVouchersDTO);
        vPCampaignVouchers = vPCampaignVouchersRepository.save(vPCampaignVouchers);
        return vPCampaignVouchersMapper.toDto(vPCampaignVouchers);
    }

    @Override
    public Optional<VPCampaignVouchersDTO> partialUpdate(VPCampaignVouchersDTO vPCampaignVouchersDTO) {
        log.debug("Request to partially update VPCampaignVouchers : {}", vPCampaignVouchersDTO);

        return vPCampaignVouchersRepository
            .findById(vPCampaignVouchersDTO.getId())
            .map(existingVPCampaignVouchers -> {
                vPCampaignVouchersMapper.partialUpdate(existingVPCampaignVouchers, vPCampaignVouchersDTO);

                return existingVPCampaignVouchers;
            })
            .map(vPCampaignVouchersRepository::save)
            .map(vPCampaignVouchersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VPCampaignVouchersDTO> findAll() {
        log.debug("Request to get all VPCampaignVouchers");
        return vPCampaignVouchersRepository
            .findAll()
            .stream()
            .map(vPCampaignVouchersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VPCampaignVouchersDTO> findOne(Long id) {
        log.debug("Request to get VPCampaignVouchers : {}", id);
        return vPCampaignVouchersRepository.findById(id).map(vPCampaignVouchersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VPCampaignVouchers : {}", id);
        vPCampaignVouchersRepository.deleteById(id);
    }

    @Override
    public Optional<VPCampaignVouchersDTO> findByProductIdAndCampaignIdAndActiveYN(String productId, Long campaignId, String activeYN) {
        log.debug("Request to find by productIdin VPCampaignVouchers : {}", productId);
        return vPCampaignVouchersRepository.findByProductIdAndCampaignIdAndActiveYN(productId, campaignId, activeYN).map(vPCampaignVouchersMapper::toDto);
    }
}
