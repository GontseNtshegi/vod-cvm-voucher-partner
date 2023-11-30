package za.co.vodacom.cvm.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.service.dto.campaign.QuantityDetailsDTO;
import za.co.vodacom.cvm.service.dto.product.Product;
import za.co.vodacom.cvm.service.dto.product.ProductQuantityDTO;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;

/**
 * Spring Data SQL repository for the VPVouchers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPVouchersRepository extends JpaRepository<VPVouchers, Long> {

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
        value = "select new za.co.vodacom.cvm.service.dto.product.Product(count(*), min(v.endDate)) from VPVouchers v, VPBatch b" +
            " where v.productId=:productId " +
            "and v.startDate< v.sysdate() " +
            "and v.endDate> v.sysdate() " +
            "and v.issuedDate is null " +
            "and v.batchId = b.id " +
            "and b.status ='A'" +
            "and rownum < 2"
    )
    Optional<Product> getValidVoucherForProduct(@Param("productId") String productId);


    @Query(
        value = "select new za.co.vodacom.cvm.service.dto.product.Product(quantity, endDate) from VPVouchers where productId=:productId and startDate< sysdate() and endDate>sysdate()"
    )
    Optional<Product> getValidVoucherForProductGenericVoucher(@Param("productId") String productId);

    @Query(
        value = "select new za.co.vodacom.cvm.service.dto.campaign.QuantityDetailsDTO(d.id, " +
            "d.type," +
            "d.description," +
            "v.description ," +
            "v.endDate,  " +
            "v.expiryDate, "+
            "count(d.id))"+
            "from VPVouchers v, VPVoucherDef d, VPCampaignVouchers c, VPBatch b " +
            "where v.productId = d.id " +
            "and v.batchId = b.id " +
            "and v.productId = c.productId " +
            "and c.campaignId = :id " +
            "and c.activeYN ='Y' " +
            "and b.status ='A' " +
            "and v.issuedDate is null " +
            "and v.endDate > :sysdate " +
            "group by d.id,d.type,d.description,v.description,v.endDate,v.expiryDate " +
            "order by 1"
    )
    List<QuantityDetailsDTO> getVoucherQuantity(@Param("id") Long id, @Param("sysdate") ZonedDateTime sysdate);
    @Lock(PESSIMISTIC_WRITE)
    @Query(
        value = "select new za.co.vodacom.cvm.service.dto.product.ProductQuantityDTO(v.id, v.productId," +
            "v.description ," +
            " v.quantity ," +
            " v.sourceTrxid , " +
            "v.endDate ," +
            " v.startDate , " +
            "v.reversedDate , " +
            " v.issuedDate," +
            "v.expiryDate, " +
            "v.createDate, " +
            " v.collectionPoint ," +
            " v.voucherCode ," +
            "  v.fileId)" +
            " from VPVouchers v, VPBatch b" +
            " where v.productId=:productId " +
            "and v.startDate< sysdate() " +
            "and v.endDate> sysdate() " +
            "and v.issuedDate is null " +
            "and v.batchId = b.id " +
            "and b.status ='A'"

    )
    List<ProductQuantityDTO> getVouchersWithStatusA(@Param("productId") String productId, Pageable pageable);

    @Query(
        value = "select new za.co.vodacom.cvm.service.dto.product.ProductQuantityDTO(v.id, v.productId," +
            "v.description ," +
            " v.quantity ," +
            " v.sourceTrxid , " +
            "v.endDate ," +
            " v.startDate , " +
            "v.reversedDate , " +
            " v.issuedDate," +
            "v.expiryDate, " +
            "v.createDate, " +
            " v.collectionPoint ," +
            " v.voucherCode ," +
            "  v.fileId)" +
            " from VPVouchers v, VPBatch b" +
            " where v.productId=:productId " +
            "and v.startDate< sysdate() " +
            "and v.endDate> sysdate() " +
            "and v.issuedDate is null " +
            "and v.batchId = b.id " +
            "and b.status ='A'"

    )
    List<ProductQuantityDTO> getValidVoucher(@Param("productId") String productId, Pageable pageable);

}
