package za.co.vodacom.cvm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPBatch;
import za.co.vodacom.cvm.service.dto.product.Product;

import java.util.Optional;

/**
 * Spring Data SQL repository for the VPBatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPBatchRepository extends JpaRepository<VPBatch, Long> {
    @Query(
        value = "select d.product_id, d.type, d.description, v.description as voucher_desc, f.file_name, count(*) , v.start_date, v.end_date, v.expiry_date from vp_vouchers v, vp_voucher_def d, vp_file_load f where v.product_id=d.product_id and v.file_id=f.id and v.batch_id=:id and issued_date is null and end_date > now() group by product_id,d.type,d.description,v.description, f.file_name,v.start_date,v.end_date,v.expiry_date order by 1",
        nativeQuery = true
    )
    Optional<VPBatch> getVoucherQuantity(@Param("id") Long id);
}
