package za.co.vodacom.cvm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPVouchers;

import java.util.Optional;

/**
 * Spring Data SQL repository for the VPVouchers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPVouchersRepository extends JpaRepository<VPVouchers, Long> {

    @Query(value = "select u from VPVouchers u where productId=:productId startDate< sysdate and endDate>sysdate and issuedDate is null and rownum<2")
    Optional<VPVouchers> getValidVoucher(@Param("productId") String productId);
    @Modifying
    @Query(value = "update VPVouchers u set issuedDate = sysdate and sourceTrxid=:incomingTrxId where id=:id")
    void issueVoucher(@Param("incomingTrxId") String incomingTrxId, @Param("id") Long id);
}

