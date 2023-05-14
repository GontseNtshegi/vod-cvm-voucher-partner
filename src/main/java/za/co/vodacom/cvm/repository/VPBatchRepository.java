package za.co.vodacom.cvm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPBatch;
import za.co.vodacom.cvm.service.dto.batch.BatchDetailsDTO;

import java.time.ZonedDateTime;
import java.util.Optional;

import java.util.List;


/**
 * Spring Data SQL repository for the VPBatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPBatchRepository extends JpaRepository<VPBatch, Long> {

    @Query(
        value = "select new za.co.vodacom.cvm.service.dto.batch.BatchDetailsDTO(d.id, " +
            "d.type," +
            "d.description," +
            "v.description ," +
            "v.startDate, " +
            "v.endDate,  " +
            "v.expiryDate , " +
            "f.fileName," +
            "count(d.id))" +
            "from VPVouchers v, VPVoucherDef d, VPFileLoad f " +
            "where v.productId = d.id " +
            "and v.fileId=f.id " +
            "and v.batchId=:id " +
            "and v.issuedDate is null " +
            "and v.endDate > :sysdate " +
            "group by d.id,d.type,d.description,v.description,f.fileName,v.startDate,v.endDate,v.expiryDate " +
            "order by 1"
    )
    List<BatchDetailsDTO> getVoucherQuantity(@Param("id") Long id, @Param("sysdate") ZonedDateTime sysdate);

    @Query(value = "select vp from VPBatch vp order by vp.id")
    Optional<List<VPBatch>> getAll();

    @Query(value = "select * from vp_batch where id=:id and status='O'", nativeQuery = true)
    Optional<VPBatch> getBatch(@Param("id") Long id);

    @Query(value = "select * from vp_batch where id=:id and status in ('O','A')", nativeQuery = true)
    Optional<VPBatch> getBatchWithStatus(@Param("id") Long id);

    Optional<VPBatch> findByName(String name);
    @Query(value = "select * from vp_batch where create_date > sysdate() - INTERVAL :period DAY order by id", nativeQuery = true)
    Optional<List<VPBatch>> getAllListWithInterval(@Param("period") Integer period);
}

