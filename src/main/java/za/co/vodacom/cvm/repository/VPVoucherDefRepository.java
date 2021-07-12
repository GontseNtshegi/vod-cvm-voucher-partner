package za.co.vodacom.cvm.repository;

    import org.springframework.data.jpa.repository.*;
    import org.springframework.stereotype.Repository;
    import za.co.vodacom.cvm.domain.VPVoucherDef;

    import java.util.Optional;

/**
 * Spring Data SQL repository for the VPVoucherDef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPVoucherDefRepository extends JpaRepository<VPVoucherDef, String> {
}
