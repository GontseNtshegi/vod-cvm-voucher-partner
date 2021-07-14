package za.co.vodacom.cvm.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPCampaignVouchers;

/**
 * Spring Data SQL repository for the VPCampaignVouchers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPCampaignVouchersRepository extends JpaRepository<VPCampaignVouchers, Long> {
    Optional<VPCampaignVouchers> findByProductIdAndCampaignIdAndActiveYN(String productId, Long campaignId, String activeYN);
}
