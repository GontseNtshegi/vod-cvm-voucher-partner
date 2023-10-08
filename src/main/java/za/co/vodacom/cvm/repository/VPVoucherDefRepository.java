package za.co.vodacom.cvm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPVoucherDef;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the VPVoucherDef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPVoucherDefRepository extends JpaRepository<VPVoucherDef, String> {

    // @Query(value = "select * vp_voucher_def  where id=:id", nativeQuery = true)
    Optional<VPVoucherDef> findById(String id);

     @Query(value = "select * from vp_voucher_def  order by product_id", nativeQuery = true)
    Optional<List<VPVoucherDef>> getAll();

    @Query(
        value="select count(id) from VPVoucherDef where id =:productId")
    int getVouchersByProductId(@Param("productId") String productID);
}
