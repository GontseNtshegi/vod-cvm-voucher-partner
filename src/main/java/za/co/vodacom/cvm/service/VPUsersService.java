package za.co.vodacom.cvm.service;

import java.util.List;
import java.util.Optional;
import za.co.vodacom.cvm.domain.VPUsers;

/**
 * Service Interface for managing {@link VPUsers}.
 */
public interface VPUsersService {
    /**
     * Save a vPUsers.
     *
     * @param vPUsers the entity to save.
     * @return the persisted entity.
     */
    VPUsers save(VPUsers vPUsers);

    /**
     * Partially updates a vPUsers.
     *
     * @param vPUsers the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VPUsers> partialUpdate(VPUsers vPUsers);

    /**
     * Get all the vPUsers.
     *
     * @return the list of entities.
     */
    List<VPUsers> findAll();

    /**
     * Get the "id" vPUsers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VPUsers> findOne(Long id);

    /**
     * Delete the "id" vPUsers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
