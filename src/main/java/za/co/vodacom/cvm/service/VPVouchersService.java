package za.co.vodacom.cvm.service;

import java.util.List;
import java.util.Optional;
import za.co.vodacom.cvm.domain.VPVouchers;

/**
 * Service Interface for managing {@link VPVouchers}.
 */
public interface VPVouchersService {
    /**
     * Save a vPVouchers.
     *
     * @param vPVouchers the entity to save.
     * @return the persisted entity.
     */
    VPVouchers save(VPVouchers vPVouchers);

    /**
     * Partially updates a vPVouchers.
     *
     * @param vPVouchers the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VPVouchers> partialUpdate(VPVouchers vPVouchers);

    /**
     * Get all the vPVouchers.
     *
     * @return the list of entities.
     */
    List<VPVouchers> findAll();

    /**
     * Get the "id" vPVouchers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VPVouchers> findOne(Long id);

    /**
     * Delete the "id" vPVouchers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
