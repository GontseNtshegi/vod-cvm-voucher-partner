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
        value = "select * from vp_file_load where batch_id=:batchId and file_name=:fileName",
        nativeQuery = true
    )
    Optional<VPFileLoad> getFileByNameAndId(@Param("batchId") Integer batchId, @Param("fileName") String fileName);
}
