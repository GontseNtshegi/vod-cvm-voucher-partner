package za.co.vodacom.cvm.repository;

import java.util.List;
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

    @Query(
        value = "select u from VPCampaignVouchers u where u.productId IN :list")
    Optional<List<VPCampaignVouchers>> getVouchersByProductId(List<String> list);

}
