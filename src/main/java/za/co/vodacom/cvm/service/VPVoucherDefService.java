package za.co.vodacom.cvm.service;

import java.util.List;
import java.util.Optional;
import za.co.vodacom.cvm.domain.VPVoucherDef;

/**
 * Service Interface for managing {@link VPVoucherDef}.
 */
public interface VPVoucherDefService {
    /**
     * Save a vPVoucherDef.
     *
     * @param vPVoucherDef the entity to save.
     * @return the persisted entity.
     */
    VPVoucherDef save(VPVoucherDef vPVoucherDef);

    /**
     * Partially updates a vPVoucherDef.
     *
     * @param vPVoucherDef the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VPVoucherDef> partialUpdate(VPVoucherDef vPVoucherDef);

    /**
     * Get all the vPVoucherDefs.
     *
     * @return the list of entities.
     */
    List<VPVoucherDef> findAll();

    /**
     * Get the "id" vPVoucherDef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VPVoucherDef> findOne(String id);

    /**
     * Delete the "id" vPVoucherDef.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Acquire a lock for productId
     * @param id
     * @return
     */
    Optional<VPVoucherDef> findById(String id);

    /**
     *
     * @return
     */
    Optional<List<VPVoucherDef>> getAll();
}
