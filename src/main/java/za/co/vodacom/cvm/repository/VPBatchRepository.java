package za.co.vodacom.cvm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPBatch;

import java.time.ZonedDateTime;
import java.util.Optional;

import java.util.List;


/**
 * Spring Data SQL repository for the VPBatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPBatchRepository extends JpaRepository<VPBatch, Long> {
//    @Query(
//        value = "select " +
//            "d.product_id," +
//            " d.type, " +
//            "d.description, " +
//            "v.description as voucher_desc, " +
//            "f.file_name, " +
//            "count(*) ," +
//            " v.start_date," +
//            " v.end_date, " +
//            "v.expiry_date " +
//            "from vp_vouchers v, vp_voucher_def d, vp_file_load f " +
//            "where v.product_id=d.product_id " +
//            "and v.file_id=f.id " +
//            "and v.batch_id=:id " +
//            "and issued_date is null and end_date > now() group by product_id,d.type,d.description,v.description, f.file_name,v.start_date,v.end_date,v.expiry_date order by 1",
//        nativeQuery = true
//    )
    @Query(
        value ="select new za.co.vodacom.cvm.service.dto.batch.BatchDetailsDTO(d.id, " +
            "d.type," +
            "d.description," +
            "v.description ," +
            "v.startDate, "+
            "v.endDate,  "+
            "v.expiryDate , "+
            "f.fileName )"+
            "from VPVouchers v, VPVoucherDef d, VPFileLoad f " +
            "where v.id = d.id " +
            "and v.fileId=f.id " +
            "and v.batchId=:id " +
            "and v.issuedDate is null " +
            "and v.endDate > :sysdate " +
            "group by d.id,d.type,d.description,v.description,f.fileName,v.startDate,v.endDate,v.expiryDate " +
            "order by 1"
    )
    Optional<VPBatch> getVoucherQuantity(@Param("id") Long id,@Param("sysdate") ZonedDateTime sysdate);

    @Query(value = "select * from vp_batch  order by id", nativeQuery = true)
    Optional<List<VPBatch>> getAll();
}
