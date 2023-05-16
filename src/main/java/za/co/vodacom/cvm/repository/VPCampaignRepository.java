package za.co.vodacom.cvm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPCampaign;
import za.co.vodacom.cvm.service.dto.campaign.CampaignProductDTO;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the VPCampaign entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPCampaignRepository extends JpaRepository<VPCampaign, Long> {

    Optional<VPCampaign> findByName(String name);

    @Query(
        value = "select new za.co.vodacom.cvm.service.dto.campaign.CampaignProductDTO(v.id, " +
            "v.campaignId, " +
            "v.productId, " +
            "v.activeYN, " +
            "d.description) " +
            "from VPCampaignVouchers v, " +
            "VPVoucherDef d " +
            "where campaign_Id = :campaign_Id " +
            "and v.productId = d.id")
    List<CampaignProductDTO> getCampaignProducts(@Param("campaign_Id") Long campaignId);

}
