package za.co.vodacom.cvm.repository;

import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPVoucherDef;

/**
 * Spring Data SQL repository for the VPVoucherDef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPVoucherDefRepository extends JpaRepository<VPVoucherDef, String> {
//    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    // @Query(value = "select * vp_voucher_def  where id=:id", nativeQuery = true)
    Optional<VPVoucherDef> findById(String id);
}
