package za.co.vodacom.cvm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPFileLoad;

import java.util.Optional;

/**
 * Spring Data SQL repository for the VPFileLoad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPFileLoadRepository extends JpaRepository<VPFileLoad, Long> {
    Optional<VPFileLoad> findByBatchIdAndAndFileName(Integer batchId , String fileName);
}
