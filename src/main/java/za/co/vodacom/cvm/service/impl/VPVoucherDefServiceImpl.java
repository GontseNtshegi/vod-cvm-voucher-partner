package za.co.vodacom.cvm.service.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.domain.VPVoucherDef;
import za.co.vodacom.cvm.repository.VPVoucherDefRepository;
import za.co.vodacom.cvm.service.VPVoucherDefService;

/**
 * Service Implementation for managing {@link VPVoucherDef}.
 */
@Service
@Transactional
public class VPVoucherDefServiceImpl implements VPVoucherDefService {

    private final Logger log = LoggerFactory.getLogger(VPVoucherDefServiceImpl.class);

    private final VPVoucherDefRepository vPVoucherDefRepository;

    public VPVoucherDefServiceImpl(VPVoucherDefRepository vPVoucherDefRepository) {
        this.vPVoucherDefRepository = vPVoucherDefRepository;
    }

    @Override
    public VPVoucherDef save(VPVoucherDef vPVoucherDef) {
        log.info("Request to save VPVoucherDef");
        log.debug("Request to save VPVoucherDef : {}", vPVoucherDef);
        return vPVoucherDefRepository.save(vPVoucherDef);
    }

    @Override
    public Optional<VPVoucherDef> partialUpdate(VPVoucherDef vPVoucherDef) {
        log.info("Request to partially update VPVoucherDef");
        log.debug("Request to partially update VPVoucherDef : {}", vPVoucherDef);

        return vPVoucherDefRepository
            .findById(vPVoucherDef.getId())
            .map(
                existingVPVoucherDef -> {
                    if (vPVoucherDef.getId() != null) {
                        existingVPVoucherDef.setId(vPVoucherDef.getId());
                    }
                    if (vPVoucherDef.getDescription() != null) {
                        existingVPVoucherDef.setDescription(vPVoucherDef.getDescription());
                    }
                    if (vPVoucherDef.getType() != null) {
                        existingVPVoucherDef.setType(vPVoucherDef.getType());
                    }
                    if (vPVoucherDef.getCategory() != null) {
                        existingVPVoucherDef.setCategory(vPVoucherDef.getCategory());
                    }
                    if (vPVoucherDef.getVendor() != null) {
                        existingVPVoucherDef.setVendor(vPVoucherDef.getVendor());
                    }
                    if (vPVoucherDef.getExtId() != null) {
                        existingVPVoucherDef.setExtId(vPVoucherDef.getExtId());
                    }
                    if (vPVoucherDef.getExtSystem() != null) {
                        existingVPVoucherDef.setExtSystem(vPVoucherDef.getExtSystem());
                    }
                    if (vPVoucherDef.getTemplateId() != null) {
                        existingVPVoucherDef.setTemplateId(vPVoucherDef.getTemplateId());
                    }
                    if (vPVoucherDef.getValidityPeriod() != null) {
                        existingVPVoucherDef.setValidityPeriod(vPVoucherDef.getValidityPeriod());
                    }
                    if (vPVoucherDef.getCacheQuantity() != null) {
                        existingVPVoucherDef.setCacheQuantity(vPVoucherDef.getCacheQuantity());
                    }
                    if (vPVoucherDef.getEncryptedYN() != null) {
                        existingVPVoucherDef.setEncryptedYN(vPVoucherDef.getEncryptedYN());
                    }
                    if (vPVoucherDef.getModifiedDate() != null) {
                        existingVPVoucherDef.setModifiedDate(vPVoucherDef.getModifiedDate());
                    }

                    return existingVPVoucherDef;
                }
            )
            .map(vPVoucherDefRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VPVoucherDef> findAll() {
        log.info("Request to get all VPVoucherDefs");
        log.debug("Request to get all VPVoucherDefs");
        return vPVoucherDefRepository.findAll();
    }

    @Override
    //@Transactional(readOnly = true)
    public Optional<VPVoucherDef> findOne(String id) {
        log.info("Request to get VPVoucherDef");
        log.debug("Request to get VPVoucherDef : {}", id);
        return vPVoucherDefRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.info("Request to delete VPVoucherDef");
        log.debug("Request to delete VPVoucherDef : {}", id);
        vPVoucherDefRepository.deleteById(id);
    }

    @Override
    public Optional<VPVoucherDef> findById(String id) {
        log.info("Request to findByProductIdForUpdate VPVoucherDef");
        log.debug("Request to findByProductIdForUpdate VPVoucherDef : {}", id);
        return vPVoucherDefRepository.findById(id);
    }
}
