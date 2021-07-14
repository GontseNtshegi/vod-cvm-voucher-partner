package za.co.vodacom.cvm.service.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.domain.VPCampaignVouchers;
import za.co.vodacom.cvm.repository.VPCampaignVouchersRepository;
import za.co.vodacom.cvm.service.VPCampaignVouchersService;

/**
 * Service Implementation for managing {@link VPCampaignVouchers}.
 */
@Service
@Transactional
public class VPCampaignVouchersServiceImpl implements VPCampaignVouchersService {

    private final Logger log = LoggerFactory.getLogger(VPCampaignVouchersServiceImpl.class);

    private final VPCampaignVouchersRepository vPCampaignVouchersRepository;

    public VPCampaignVouchersServiceImpl(VPCampaignVouchersRepository vPCampaignVouchersRepository) {
        this.vPCampaignVouchersRepository = vPCampaignVouchersRepository;
    }

    @Override
    public VPCampaignVouchers save(VPCampaignVouchers vPCampaignVouchers) {
        log.debug("Request to save VPCampaignVouchers : {}", vPCampaignVouchers);
        return vPCampaignVouchersRepository.save(vPCampaignVouchers);
    }

    @Override
    public Optional<VPCampaignVouchers> partialUpdate(VPCampaignVouchers vPCampaignVouchers) {
        log.debug("Request to partially update VPCampaignVouchers : {}", vPCampaignVouchers);

        return vPCampaignVouchersRepository
            .findById(vPCampaignVouchers.getId())
            .map(
                existingVPCampaignVouchers -> {
                    if (vPCampaignVouchers.getCampaignId() != null) {
                        existingVPCampaignVouchers.setCampaignId(vPCampaignVouchers.getCampaignId());
                    }
                    if (vPCampaignVouchers.getProductId() != null) {
                        existingVPCampaignVouchers.setProductId(vPCampaignVouchers.getProductId());
                    }
                    if (vPCampaignVouchers.getCreateDate() != null) {
                        existingVPCampaignVouchers.setCreateDate(vPCampaignVouchers.getCreateDate());
                    }
                    if (vPCampaignVouchers.getModifiedDate() != null) {
                        existingVPCampaignVouchers.setModifiedDate(vPCampaignVouchers.getModifiedDate());
                    }
                    if (vPCampaignVouchers.getActiveYN() != null) {
                        existingVPCampaignVouchers.setActiveYN(vPCampaignVouchers.getActiveYN());
                    }

                    return existingVPCampaignVouchers;
                }
            )
            .map(vPCampaignVouchersRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VPCampaignVouchers> findAll() {
        log.debug("Request to get all VPCampaignVouchers");
        return vPCampaignVouchersRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VPCampaignVouchers> findOne(Long id) {
        log.debug("Request to get VPCampaignVouchers : {}", id);
        return vPCampaignVouchersRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VPCampaignVouchers : {}", id);
        vPCampaignVouchersRepository.deleteById(id);
    }

    @Override
    public Optional<VPCampaignVouchers> findByProductIdAndCampaignIdAndActiveYN(String productId, Long campaignId, String activeYN) {
        log.debug("Request to find by productIdin VPCampaignVouchers : {}", productId);
        return vPCampaignVouchersRepository.findByProductIdAndCampaignIdAndActiveYN(productId, campaignId, activeYN);
    }
}
