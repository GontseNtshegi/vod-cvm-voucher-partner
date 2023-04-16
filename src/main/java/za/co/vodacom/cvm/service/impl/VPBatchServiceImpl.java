package za.co.vodacom.cvm.service.impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.domain.VPBatch;
import za.co.vodacom.cvm.repository.VPBatchRepository;
import za.co.vodacom.cvm.service.VPBatchService;
import za.co.vodacom.cvm.service.dto.batch.BatchDetailsDTO;

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
        log.info("Request to save VPBatch");
        log.debug("Request to save VPBatch : {}", vPBatch);
        return vPBatchRepository.save(vPBatch);
    }

    @Override
    public Optional<VPBatch> partialUpdate(VPBatch vPBatch) {
        log.info("Request to partially update VPBatch");
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
        log.info("Request to get all VPBatches");
        log.debug("Request to get all VPBatches");
        return vPBatchRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VPBatch> findOne(Long id) {
        log.info("Request to get VPBatch");
        log.debug("Request to get VPBatch : {}", id);
        return vPBatchRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VPBatch : {}", id);
        vPBatchRepository.deleteById(id);
    }
    @Override
    public Optional<List<VPBatch>> getAll(){
        log.info("Request to getAll VPBatch");
        log.debug("Request to getAll VPBatch :");
        return vPBatchRepository.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BatchDetailsDTO> getVoucherQuantity(Long id , ZonedDateTime sysdate) {
        log.info("Request to get Voucher Quantity");
        log.debug("Request get Voucher Quantity: {}", id);
        return vPBatchRepository.getVoucherQuantity(id , sysdate);
    }

    @Override
    public Optional<VPBatch> findByName(String name) {
        log.info("Request to find by name in VPBatch ");
        log.debug("Request to find by name in VPBatch : {}", name);
        return vPBatchRepository.findByName(name);
    }

    @Override
    public Optional<VPBatch> getBatch(Long id) {
        log.info("Request to find batch in VPBatch  ");
        log.debug("Request to find batch in VPBatch with id : {}", id);
        return vPBatchRepository.getBatch(id);
    }

    @Override
    public Optional<VPBatch> getBatchWithStatus(Long id) {
        log.info("Request to find batch in VPBatch");
        log.debug("Request to find batch in VPBatch with id : {}", id);
        return vPBatchRepository.getBatchWithStatus(id);
    }

    @Override
    public void updateBatch(Long id, String name) {
        vPBatchRepository.updateBatch(id,name);
    }
    @Override
    public void updateReturnedBatch(Long id,String name){
        vPBatchRepository.updateReturnedBatch(id,name);
    }
}
