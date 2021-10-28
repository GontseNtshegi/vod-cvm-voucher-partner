package za.co.vodacom.cvm.service;

import java.util.List;
import java.util.Optional;
import za.co.vodacom.cvm.service.dto.VPCampaignVouchersDTO;

/**
 * Service Interface for managing {@link za.co.vodacom.cvm.domain.VPCampaignVouchers}.
 */
public interface VPCampaignVouchersService {
    /**
     * Save a vPCampaignVouchers.
     *
     * @param vPCampaignVouchersDTO the entity to save.
     * @return the persisted entity.
     */
    VPCampaignVouchersDTO save(VPCampaignVouchersDTO vPCampaignVouchersDTO);

    /**
     * Partially updates a vPCampaignVouchers.
     *
     * @param vPCampaignVouchersDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VPCampaignVouchersDTO> partialUpdate(VPCampaignVouchersDTO vPCampaignVouchersDTO);

    /**
     * Get all the vPCampaignVouchers.
     *
     * @return the list of entities.
     */
    List<VPCampaignVouchersDTO> findAll();

    /**
     * Get the "id" vPCampaignVouchers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VPCampaignVouchersDTO> findOne(Long id);

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
    Optional<VPCampaignVouchersDTO> findByProductIdAndCampaignIdAndActiveYN(String productId, Long campaignId, String activeYN);
}
