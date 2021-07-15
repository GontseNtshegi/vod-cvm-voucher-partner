package za.co.vodacom.cvm.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import za.co.vodacom.cvm.domain.VPUsers;

/**
 * Spring Data SQL repository for the VPUsers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VPUsersRepository extends JpaRepository<VPUsers, Long> {}
