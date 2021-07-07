package za.co.vodacom.cvm.service;

import java.util.List;
import java.util.Optional;
import za.co.vodacom.cvm.domain.VPCampaign;

/**
 * Service Interface for managing {@link VPCampaign}.
 */
public interface VPCampaignService {
    /**
     * Save a vPCampaign.
     *
     * @param vPCampaign the entity to save.
     * @return the persisted entity.
     */
    VPCampaign save(VPCampaign vPCampaign);

    /**
     * Partially updates a vPCampaign.
     *
     * @param vPCampaign the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VPCampaign> partialUpdate(VPCampaign vPCampaign);

    /**
     * Get all the vPCampaigns.
     *
     * @return the list of entities.
     */
    List<VPCampaign> findAll();

    /**
     * Get the "id" vPCampaign.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VPCampaign> findOne(Long id);

    /**
     * Delete the "id" vPCampaign.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
