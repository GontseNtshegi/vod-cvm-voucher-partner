package za.co.vodacom.cvm.service.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.domain.VPFileLoad;
import za.co.vodacom.cvm.repository.VPFileLoadRepository;
import za.co.vodacom.cvm.service.VPFileLoadService;

/**
 * Service Implementation for managing {@link VPFileLoad}.
 */
@Service
@Transactional
public class VPFileLoadServiceImpl implements VPFileLoadService {

    private final Logger log = LoggerFactory.getLogger(VPFileLoadServiceImpl.class);

    private final VPFileLoadRepository vPFileLoadRepository;

    public VPFileLoadServiceImpl(VPFileLoadRepository vPFileLoadRepository) {
        this.vPFileLoadRepository = vPFileLoadRepository;
    }

    @Override
    public VPFileLoad save(VPFileLoad vPFileLoad) {
        log.debug("Request to save VPFileLoad : {}", vPFileLoad);
        return vPFileLoadRepository.save(vPFileLoad);
    }

    @Override
    public Optional<VPFileLoad> partialUpdate(VPFileLoad vPFileLoad) {
        log.debug("Request to partially update VPFileLoad : {}", vPFileLoad);

        return vPFileLoadRepository
            .findById(vPFileLoad.getId())
            .map(
                existingVPFileLoad -> {
                    if (vPFileLoad.getFileName() != null) {
                        existingVPFileLoad.setFileName(vPFileLoad.getFileName());
                    }
                    if (vPFileLoad.getBatchId() != null) {
                        existingVPFileLoad.setBatchId(vPFileLoad.getBatchId());
                    }
                    if (vPFileLoad.getCreateDate() != null) {
                        existingVPFileLoad.setCreateDate(vPFileLoad.getCreateDate());
                    }
                    if (vPFileLoad.getCompletedDate() != null) {
                        existingVPFileLoad.setCompletedDate(vPFileLoad.getCompletedDate());
                    }
                    if (vPFileLoad.getNumLoaded() != null) {
                        existingVPFileLoad.setNumLoaded(vPFileLoad.getNumLoaded());
                    }
                    if (vPFileLoad.getNumFailed() != null) {
                        existingVPFileLoad.setNumFailed(vPFileLoad.getNumFailed());
                    }

                    return existingVPFileLoad;
                }
            )
            .map(vPFileLoadRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VPFileLoad> findAll() {
        log.debug("Request to get all VPFileLoads");
        return vPFileLoadRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VPFileLoad> findOne(Long id) {
        log.debug("Request to get VPFileLoad : {}", id);
        return vPFileLoadRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VPFileLoad : {}", id);
        vPFileLoadRepository.deleteById(id);
    }
}
