package za.co.vodacom.cvm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import za.co.vodacom.cvm.domain.VPFileLoad;

/**
 * Service Interface for managing {@link VPFileLoad}.
 */
public interface VPFileLoadService {
    /**
     * Save a vPFileLoad.
     *
     * @param vPFileLoad the entity to save.
     * @return the persisted entity.
     */
    VPFileLoad save(VPFileLoad vPFileLoad);

    /**
     * Partially updates a vPFileLoad.
     *
     * @param vPFileLoad the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VPFileLoad> partialUpdate(VPFileLoad vPFileLoad);

    /**
     * Get all the vPFileLoads.
     *
     * @return the list of entities.
     */
    List<VPFileLoad> findAll();

    /**
     * Get the "id" vPFileLoad.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VPFileLoad> findOne(Long id);

    /**
     * Delete the "id" vPFileLoad.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<VPFileLoad> getFileByNameAndId(Integer batchId, String fileName);
}
