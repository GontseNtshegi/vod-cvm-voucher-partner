package za.co.vodacom.cvm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPFileLoad;

/**
 * Spring Data SQL repository for the VPFileLoad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPFileLoadRepository extends JpaRepository<VPFileLoad, Long> {}
