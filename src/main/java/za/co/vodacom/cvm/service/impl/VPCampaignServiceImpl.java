package za.co.vodacom.cvm.service.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.domain.VPCampaign;
import za.co.vodacom.cvm.repository.VPCampaignRepository;
import za.co.vodacom.cvm.service.VPCampaignService;

/**
 * Service Implementation for managing {@link VPCampaign}.
 */
@Service
@Transactional
public class VPCampaignServiceImpl implements VPCampaignService {

    private final Logger log = LoggerFactory.getLogger(VPCampaignServiceImpl.class);

    private final VPCampaignRepository vPCampaignRepository;

    public VPCampaignServiceImpl(VPCampaignRepository vPCampaignRepository) {
        this.vPCampaignRepository = vPCampaignRepository;
    }

    @Override
    public VPCampaign save(VPCampaign vPCampaign) {
        log.debug("Request to save VPCampaign : {}", vPCampaign);
        return vPCampaignRepository.save(vPCampaign);
    }

    @Override
    public Optional<VPCampaign> partialUpdate(VPCampaign vPCampaign) {
        log.debug("Request to partially update VPCampaign : {}", vPCampaign);

        return vPCampaignRepository
            .findById(vPCampaign.getId())
            .map(
                existingVPCampaign -> {
                    if (vPCampaign.getName() != null) {
                        existingVPCampaign.setName(vPCampaign.getName());
                    }
                    if (vPCampaign.getStartDate() != null) {
                        existingVPCampaign.setStartDate(vPCampaign.getStartDate());
                    }
                    if (vPCampaign.getEndDate() != null) {
                        existingVPCampaign.setEndDate(vPCampaign.getEndDate());
                    }

                    return existingVPCampaign;
                }
            )
            .map(vPCampaignRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VPCampaign> findAll() {
        log.debug("Request to get all VPCampaigns");
        return vPCampaignRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VPCampaign> findOne(Long id) {
        log.debug("Request to get VPCampaign : {}", id);
        return vPCampaignRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VPCampaign : {}", id);
        vPCampaignRepository.deleteById(id);
    }
}