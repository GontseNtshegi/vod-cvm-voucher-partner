package za.co.vodacom.cvm.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.service.dto.product.Product;

/**
 * Spring Data SQL repository for the VPVouchers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPVouchersRepository extends JpaRepository<VPVouchers, Long> {
    @Query(
        value = "select * from vp_vouchers where product_id=:productId and start_date< sysdate() and end_date>sysdate() and issued_date is null limit 1",
        nativeQuery = true
    )
    Optional<VPVouchers> getValidVoucher(@Param("productId") String productId);

    @Modifying
    @Query(value = "update vp_vouchers set issued_date = sysdate() + INTERVAL 2 HOUR, source_trxid=:incomingTrxId where id=:id", nativeQuery = true)
    void issueVoucher(@Param("incomingTrxId") String incomingTrxId, @Param("id") Long id);

    @Query(
        value = "select * from vp_vouchers where product_id=:productId and start_date< sysdate() and end_date>sysdate() and id=:id and source_trxid=:trxId",
        nativeQuery = true
    )
    Optional<VPVouchers> getValidVoucherForReturn(@Param("productId") String productId, @Param("id") Long id, @Param("trxId") String trxId);

    @Modifying
    @Query(value = "update VPVouchers u set issuedDate = null, sourceTrxid= null where id=:id")
    void updateReturnedVoucher(@Param("id") Long id);

    @Query(
        value = "select za.co.vodacom.cvm.service.dto.product.Product(count(*), min(end_date)) from vp_vouchers where product_id=:productId and start_date< sysdate() and end_date>sysdate() and issued_date is null",
        nativeQuery = true
    )
    Optional<Product> getValidVoucherForProduct(@Param("productId") String productId);

    @Query(
        value = "select za.co.vodacom.cvm.service.dto.product.Product(quantity, end_date) from vp_vouchers where product_d=:productId and start_date< sysdate() and end_date>sysdate()",
        nativeQuery = true
    )
    Optional<Product> getValidVoucherForProductGenericVoucher(@Param("productId") String productId);
}
