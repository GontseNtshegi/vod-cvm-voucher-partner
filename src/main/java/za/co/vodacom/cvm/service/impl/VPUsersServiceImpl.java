package za.co.vodacom.cvm.service.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.domain.VPUsers;
import za.co.vodacom.cvm.repository.VPUsersRepository;
import za.co.vodacom.cvm.service.VPUsersService;

/**
 * Service Implementation for managing {@link VPUsers}.
 */
@Service
@Transactional
public class VPUsersServiceImpl implements VPUsersService {

    private final Logger log = LoggerFactory.getLogger(VPUsersServiceImpl.class);

    private final VPUsersRepository vPUsersRepository;

    public VPUsersServiceImpl(VPUsersRepository vPUsersRepository) {
        this.vPUsersRepository = vPUsersRepository;
    }

    @Override
    public VPUsers save(VPUsers vPUsers) {
        log.info("Request to save VPUsers ");
        log.debug("Request to save VPUsers : {}", vPUsers);
        return vPUsersRepository.save(vPUsers);
    }

    @Override
    public Optional<VPUsers> partialUpdate(VPUsers vPUsers) {
        log.info("Request to partially update VPUsers");
        log.debug("Request to partially update VPUsers : {}", vPUsers);

        return vPUsersRepository
            .findById(vPUsers.getId())
            .map(
                existingVPUsers -> {
                    if (vPUsers.getId() != null) {
                        existingVPUsers.setId(vPUsers.getId());
                    }
                    if (vPUsers.getCreateDate() != null) {
                        existingVPUsers.setCreateDate(vPUsers.getCreateDate());
                    }
                    if (vPUsers.getModifiedDate() != null) {
                        existingVPUsers.setModifiedDate(vPUsers.getModifiedDate());
                    }
                    if (vPUsers.getActiveYN() != null) {
                        existingVPUsers.setActiveYN(vPUsers.getActiveYN());
                    }

                    return existingVPUsers;
                }
            )
            .map(vPUsersRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VPUsers> findAll() {
        log.info("Request to get all VPUsers");
        log.debug("Request to get all VPUsers");
        return vPUsersRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VPUsers> findOne(Long id) {
        log.info("Request to get VPUsers");
        log.debug("Request to get VPUsers : {}", id);
        return vPUsersRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.info("Request to delete VPUsers");
        log.debug("Request to delete VPUsers : {}", id);
        vPUsersRepository.deleteById(id);
    }
}
