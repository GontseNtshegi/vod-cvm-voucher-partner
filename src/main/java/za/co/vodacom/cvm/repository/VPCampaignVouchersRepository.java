package za.co.vodacom.cvm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPCampaignVouchers;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the VPCampaignVouchers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPCampaignVouchersRepository extends JpaRepository<VPCampaignVouchers, Long> {
    Optional<VPCampaignVouchers> findByProductIdAndCampaignIdAndActiveYN(String productId, Long campaignId, String activeYN);

    @Query(
        value = "select v from VPCampaignVouchers v where v.campaignId = :campaignId")
    List<VPCampaignVouchers> findProductsByCampaignId(@Param("campaignId")Long campaignID);

    @Query(
        value = " select v from VPCampaignVouchers v where v.campaignId =:campaignId and v.productId =:productId"
    )
    Optional<VPCampaignVouchers> findVoucherByProductIdandCampaignId(@Param("campaignId") Long campaignid, @Param("productId")String productid);



}
