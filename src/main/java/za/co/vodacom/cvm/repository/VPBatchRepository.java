package za.co.vodacom.cvm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPBatch;

/**
 * Spring Data SQL repository for the VPBatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPBatchRepository extends JpaRepository<VPBatch, Long> {}
