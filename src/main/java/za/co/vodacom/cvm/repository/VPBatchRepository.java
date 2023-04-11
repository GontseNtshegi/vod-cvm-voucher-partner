package za.co.vodacom.cvm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPBatch;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the VPBatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPBatchRepository extends JpaRepository<VPBatch, Long> {
    @Query(value = "select * from vp_batch  order by id", nativeQuery = true)
    Optional<List<VPBatch>> getAll();
}
