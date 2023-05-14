package za.co.vodacom.cvm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPFileLoad;

import java.util.Optional;

/**
 * Spring Data SQL repository for the VPFileLoad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPFileLoadRepository extends JpaRepository<VPFileLoad, Long> {
    @Query(
        value = "select v from VPFileLoad v where v.batchId=:batchId and v.fileName=:fileName"
    )
    Optional<VPFileLoad> getFileByNameAndId(@Param("batchId") Integer batchId, @Param("fileName") String fileName);
}
