package za.co.vodacom.cvm.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.domain.VPUsers;
import za.co.vodacom.cvm.repository.VPUsersRepository;
import za.co.vodacom.cvm.service.VPUsersService;
import za.co.vodacom.cvm.service.dto.VPUsersDTO;
import za.co.vodacom.cvm.service.mapper.VPUsersMapper;

/**
 * Service Implementation for managing {@link VPUsers}.
 */
@Service
@Transactional
public class VPUsersServiceImpl implements VPUsersService {

    private final Logger log = LoggerFactory.getLogger(VPUsersServiceImpl.class);

    private final VPUsersRepository vPUsersRepository;

    private final VPUsersMapper vPUsersMapper;

    public VPUsersServiceImpl(VPUsersRepository vPUsersRepository, VPUsersMapper vPUsersMapper) {
        this.vPUsersRepository = vPUsersRepository;
        this.vPUsersMapper = vPUsersMapper;
    }

    @Override
    public VPUsersDTO save(VPUsersDTO vPUsersDTO) {
        log.debug("Request to save VPUsers : {}", vPUsersDTO);
        VPUsers vPUsers = vPUsersMapper.toEntity(vPUsersDTO);
        vPUsers = vPUsersRepository.save(vPUsers);
        return vPUsersMapper.toDto(vPUsers);
    }

    @Override
    public Optional<VPUsersDTO> partialUpdate(VPUsersDTO vPUsersDTO) {
        log.debug("Request to partially update VPUsers : {}", vPUsersDTO);

        return vPUsersRepository
            .findById(vPUsersDTO.getId())
            .map(existingVPUsers -> {
                vPUsersMapper.partialUpdate(existingVPUsers, vPUsersDTO);

                return existingVPUsers;
            })
            .map(vPUsersRepository::save)
            .map(vPUsersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VPUsersDTO> findAll() {
        log.debug("Request to get all VPUsers");
        return vPUsersRepository.findAll().stream().map(vPUsersMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VPUsersDTO> findOne(Long id) {
        log.debug("Request to get VPUsers : {}", id);
        return vPUsersRepository.findById(id).map(vPUsersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VPUsers : {}", id);
        vPUsersRepository.deleteById(id);
    }
}
