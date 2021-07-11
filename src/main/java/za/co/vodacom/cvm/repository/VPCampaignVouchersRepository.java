package za.co.vodacom.cvm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPCampaignVouchers;

import java.util.Optional;

/**
 * Spring Data SQL repository for the VPCampaignVouchers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPCampaignVouchersRepository extends JpaRepository<VPCampaignVouchers, Long> {

    Optional<VPCampaignVouchers> findByProductId(String productId);
}
