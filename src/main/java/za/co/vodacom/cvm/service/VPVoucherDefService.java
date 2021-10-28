package za.co.vodacom.cvm.service;

import java.util.List;
import java.util.Optional;
import za.co.vodacom.cvm.service.dto.VPVoucherDefDTO;

/**
 * Service Interface for managing {@link za.co.vodacom.cvm.domain.VPVoucherDef}.
 */
public interface VPVoucherDefService {
    /**
     * Save a vPVoucherDef.
     *
     * @param vPVoucherDefDTO the entity to save.
     * @return the persisted entity.
     */
    VPVoucherDefDTO save(VPVoucherDefDTO vPVoucherDefDTO);

    /**
     * Partially updates a vPVoucherDef.
     *
     * @param vPVoucherDefDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VPVoucherDefDTO> partialUpdate(VPVoucherDefDTO vPVoucherDefDTO);

    /**
     * Get all the vPVoucherDefs.
     *
     * @return the list of entities.
     */
    List<VPVoucherDefDTO> findAll();

    /**
     * Get the "id" vPVoucherDef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VPVoucherDefDTO> findOne(String id);

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
    Optional<VPVoucherDefDTO> findById(String id);
}
