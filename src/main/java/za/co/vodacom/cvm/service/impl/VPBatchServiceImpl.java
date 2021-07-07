package za.co.vodacom.cvm.service.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.domain.VPBatch;
import za.co.vodacom.cvm.repository.VPBatchRepository;
import za.co.vodacom.cvm.service.VPBatchService;

/**
 * Service Implementation for managing {@link VPBatch}.
 */
@Service
@Transactional
public class VPBatchServiceImpl implements VPBatchService {

    private final Logger log = LoggerFactory.getLogger(VPBatchServiceImpl.class);

    private final VPBatchRepository vPBatchRepository;

    public VPBatchServiceImpl(VPBatchRepository vPBatchRepository) {
        this.vPBatchRepository = vPBatchRepository;
    }

    @Override
    public VPBatch save(VPBatch vPBatch) {
        log.debug("Request to save VPBatch : {}", vPBatch);
        return vPBatchRepository.save(vPBatch);
    }

    @Override
    public Optional<VPBatch> partialUpdate(VPBatch vPBatch) {
        log.debug("Request to partially update VPBatch : {}", vPBatch);

        return vPBatchRepository
            .findById(vPBatch.getId())
            .map(
                existingVPBatch -> {
                    if (vPBatch.getCreateDate() != null) {
                        existingVPBatch.setCreateDate(vPBatch.getCreateDate());
                    }
                    if (vPBatch.getLoadDate() != null) {
                        existingVPBatch.setLoadDate(vPBatch.getLoadDate());
                    }
                    if (vPBatch.getComment() != null) {
                        existingVPBatch.setComment(vPBatch.getComment());
                    }
                    if (vPBatch.getRestrictedYN() != null) {
                        existingVPBatch.setRestrictedYN(vPBatch.getRestrictedYN());
                    }
                    if (vPBatch.getUserId() != null) {
                        existingVPBatch.setUserId(vPBatch.getUserId());
                    }

                    return existingVPBatch;
                }
            )
            .map(vPBatchRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VPBatch> findAll() {
        log.debug("Request to get all VPBatches");
        return vPBatchRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VPBatch> findOne(Long id) {
        log.debug("Request to get VPBatch : {}", id);
        return vPBatchRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VPBatch : {}", id);
        vPBatchRepository.deleteById(id);
    }
}
