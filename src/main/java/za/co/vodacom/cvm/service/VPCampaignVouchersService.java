package za.co.vodacom.cvm.service;

import java.util.List;
import java.util.Optional;
import za.co.vodacom.cvm.domain.VPCampaignVouchers;

/**
 * Service Interface for managing {@link VPCampaignVouchers}.
 */
public interface VPCampaignVouchersService {
    /**
     * Save a vPCampaignVouchers.
     *
     * @param vPCampaignVouchers the entity to save.
     * @return the persisted entity.
     */
    VPCampaignVouchers save(VPCampaignVouchers vPCampaignVouchers);

    /**
     * Partially updates a vPCampaignVouchers.
     *
     * @param vPCampaignVouchers the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VPCampaignVouchers> partialUpdate(VPCampaignVouchers vPCampaignVouchers);

    /**
     * Get all the vPCampaignVouchers.
     *
     * @return the list of entities.
     */
    List<VPCampaignVouchers> findAll();

    /**
     * Get the "id" vPCampaignVouchers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VPCampaignVouchers> findOne(Long id);

    /**
     * Delete the "id" vPCampaignVouchers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Find by product Id
     * @param productId
     * @return
     */
    Optional<VPCampaignVouchers> findByProductId(String productId);
}
