package za.co.vodacom.cvm.service;

import java.util.List;
import java.util.Optional;
import za.co.vodacom.cvm.service.dto.VPBatchDTO;

/**
 * Service Interface for managing {@link za.co.vodacom.cvm.domain.VPBatch}.
 */
public interface VPBatchService {
    /**
     * Save a vPBatch.
     *
     * @param vPBatchDTO the entity to save.
     * @return the persisted entity.
     */
    VPBatchDTO save(VPBatchDTO vPBatchDTO);

    /**
     * Partially updates a vPBatch.
     *
     * @param vPBatchDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VPBatchDTO> partialUpdate(VPBatchDTO vPBatchDTO);

    /**
     * Get all the vPBatches.
     *
     * @return the list of entities.
     */
    List<VPBatchDTO> findAll();

    /**
     * Get the "id" vPBatch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VPBatchDTO> findOne(Long id);

    /**
     * Delete the "id" vPBatch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
