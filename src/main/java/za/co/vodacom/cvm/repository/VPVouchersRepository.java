package za.co.vodacom.cvm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.service.dto.product.Product;
import za.co.vodacom.cvm.service.dto.product.Quantity;

import java.util.List;
import java.util.Optional;

import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;

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

    @Lock(PESSIMISTIC_WRITE)
    @Query(
        value = "select v from VPVouchers v where v.productId=:productId and v.startDate< CURRENT_TIMESTAMP and v.endDate>CURRENT_TIMESTAMP and v.issuedDate is null"
    )
    List<VPVouchers> getValidVoucherWithLock(@Param("productId") String productId, Pageable pageable);

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
        value = "select new za.co.vodacom.cvm.service.dto.product.Product(count(*), min(endDate)) from VPVouchers where productId=:productId and startDate< sysdate() and endDate>sysdate() and issuedDate is null"
    )
    Optional<Product> getValidVoucherForProduct(@Param("productId") String productId);

    @Query(
        value = "select new za.co.vodacom.cvm.service.dto.product.Product(quantity, endDate) from VPVouchers where productId=:productId and startDate< sysdate() and endDate>sysdate()"
    )
    Optional<Product> getValidVoucherForProductGenericVoucher(@Param("productId") String productId);

/*    @Query(
        value = "select new za.co.vodacom.cvm.service.dto.product.quantity(d.product_id, d.type, d.description, v.description as voucher_desc, count(*),v.start_date,v.end_date,v.expiry_date) from \n" +
            "vp_vouchers v, " +
            "vp_voucher_def d," +
            " vp_campaign_vouchers c," +
            " vp_batch b\n" +
            "where v.product_id=d.product_id\n" +
            "and v.batch_id=b.id\n" +
            "and v.product_id=d.product_id\n" +
            "and v.product_id=c.product_id\n" +
            "and c.campaign_id=21\n" +
            "and c.active_yn='Y'\n" +
            "and b.status='O'\n" +
            "and issued_date is null\n" +
            "and end_date > now()\n" +
            "group by product_id,d.type,d.description,v.description,\n" +
            "v.start_date,v.end_date,v.expiry_date order by 1"
    )
    List<Quantity> getVoucherQuantity();*/
}
