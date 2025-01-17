package za.co.vodacom.cvm.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import za.co.vodacom.cvm.domain.VPBatch;
import za.co.vodacom.cvm.service.dto.batch.BatchDetailsDTO;

/**
 * Service Interface for managing {@link VPBatch}.
 */
public interface VPBatchService {
    /**
     * Save a vPBatch.
     *
     * @param vPBatch the entity to save.
     * @return the persisted entity.
     */
    VPBatch save(VPBatch vPBatch);

    /**
     * Partially updates a vPBatch.
     *
     * @param vPBatch the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VPBatch> partialUpdate(VPBatch vPBatch);

    /**
     * Get all the vPBatches.
     *
     * @return the list of entities.
     */
    List<VPBatch> findAll();

    /**
     * Get the "id" vPBatch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VPBatch> findOne(Long id);

    /**
     * Delete the "id" vPBatch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    /**
     *
     * @return
     */
    Optional<List<VPBatch>> getAll();

    Optional<List<VPBatch>> getAllListWithInterval(Integer period);

    List<BatchDetailsDTO> getVoucherQuantity(Integer id, ZonedDateTime sysdate);
    /**
     * Find by batch name
     * @param name
     * @return
     */
    Optional<VPBatch> findByName(String name);

    Optional<VPBatch> getBatch(Long id);

    Optional<VPBatch> getBatchWithStatus(Long id);

}
