package za.co.vodacom.cvm.service;

import java.util.List;
import java.util.Optional;
import za.co.vodacom.cvm.service.dto.VPCampaignDTO;

/**
 * Service Interface for managing {@link za.co.vodacom.cvm.domain.VPCampaign}.
 */
public interface VPCampaignService {
    /**
     * Save a vPCampaign.
     *
     * @param vPCampaignDTO the entity to save.
     * @return the persisted entity.
     */
    VPCampaignDTO save(VPCampaignDTO vPCampaignDTO);

    /**
     * Partially updates a vPCampaign.
     *
     * @param vPCampaignDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VPCampaignDTO> partialUpdate(VPCampaignDTO vPCampaignDTO);

    /**
     * Get all the vPCampaigns.
     *
     * @return the list of entities.
     */
    List<VPCampaignDTO> findAll();

    /**
     * Get the "id" vPCampaign.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VPCampaignDTO> findOne(Long id);

    /**
     * Delete the "id" vPCampaign.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Find by campaign name
     * @param name
     * @return
     */
    Optional<VPCampaignDTO> findByName(String name);
}
