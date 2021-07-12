package za.co.vodacom.cvm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPVoucherDef;

import javax.persistence.LockModeType;
import java.util.Optional;

/**
 * Spring Data SQL repository for the VPVoucherDef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPVoucherDefRepository extends JpaRepository<VPVoucherDef, String> {

    @Modifying
    @Query(value = "select u VPVoucherDef u where id=:id for update")
    Optional<VPVoucherDef> findByProductId(String id);
}
