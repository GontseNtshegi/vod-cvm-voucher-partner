package za.co.vodacom.cvm.service;

import java.util.List;
import java.util.Optional;
import za.co.vodacom.cvm.service.dto.VPFileLoadDTO;

/**
 * Service Interface for managing {@link za.co.vodacom.cvm.domain.VPFileLoad}.
 */
public interface VPFileLoadService {
    /**
     * Save a vPFileLoad.
     *
     * @param vPFileLoadDTO the entity to save.
     * @return the persisted entity.
     */
    VPFileLoadDTO save(VPFileLoadDTO vPFileLoadDTO);

    /**
     * Partially updates a vPFileLoad.
     *
     * @param vPFileLoadDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VPFileLoadDTO> partialUpdate(VPFileLoadDTO vPFileLoadDTO);

    /**
     * Get all the vPFileLoads.
     *
     * @return the list of entities.
     */
    List<VPFileLoadDTO> findAll();

    /**
     * Get the "id" vPFileLoad.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VPFileLoadDTO> findOne(Long id);

    /**
     * Delete the "id" vPFileLoad.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
