package za.co.vodacom.cvm.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPVouchers;

/**
 * Spring Data SQL repository for the VPVouchers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPVouchersRepository extends JpaRepository<VPVouchers, Long> {
    @Query(
        value = "select * from vp_vouchers where productId=:productId startDate< sysdate() and endDate>sysdate() and issuedDate is null and rownum<2",
        nativeQuery = true
    )
    Optional<VPVouchers> getValidVoucher(@Param("productId") String productId);

    @Modifying
    @Query(value = "update vp_vouchers set issuedDate = sysdate(), sourceTrxid=:incomingTrxId where id=:id", nativeQuery = true)
    void issueVoucher(@Param("incomingTrxId") String incomingTrxId, @Param("id") Long id);

    @Query(
        value = "select * from vp_vouchers where productId=:productId startDate< sysdate() and endDate>sysdate() and id=:id and source_trxid=:trxId",
        nativeQuery = true
    )
    Optional<VPVouchers> getValidVoucherForReturn(@Param("productId") String productId, @Param("id") Long id, @Param("trxId") String trxId);

    @Modifying
    @Query(value = "update VPVouchers u set issuedDate = null, sourceTrxid= null where id=:id")
    void updateReturnedVoucher(@Param("id") Long id);
}
