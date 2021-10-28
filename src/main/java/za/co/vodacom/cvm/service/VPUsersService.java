package za.co.vodacom.cvm.service;

import java.util.List;
import java.util.Optional;
import za.co.vodacom.cvm.service.dto.VPUsersDTO;

/**
 * Service Interface for managing {@link za.co.vodacom.cvm.domain.VPUsers}.
 */
public interface VPUsersService {
    /**
     * Save a vPUsers.
     *
     * @param vPUsersDTO the entity to save.
     * @return the persisted entity.
     */
    VPUsersDTO save(VPUsersDTO vPUsersDTO);

    /**
     * Partially updates a vPUsers.
     *
     * @param vPUsersDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VPUsersDTO> partialUpdate(VPUsersDTO vPUsersDTO);

    /**
     * Get all the vPUsers.
     *
     * @return the list of entities.
     */
    List<VPUsersDTO> findAll();

    /**
     * Get the "id" vPUsers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VPUsersDTO> findOne(Long id);

    /**
     * Delete the "id" vPUsers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
