package za.co.vodacom.cvm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPCampaign;

/**
 * Spring Data SQL repository for the VPCampaign entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPCampaignRepository extends JpaRepository<VPCampaign, Long> {}
