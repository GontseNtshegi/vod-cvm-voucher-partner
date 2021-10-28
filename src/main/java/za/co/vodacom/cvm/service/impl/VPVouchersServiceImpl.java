package za.co.vodacom.cvm.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.repository.VPVouchersRepository;
import za.co.vodacom.cvm.service.VPVouchersService;
import za.co.vodacom.cvm.service.dto.VPVouchersDTO;
import za.co.vodacom.cvm.service.dto.product.Product;
import za.co.vodacom.cvm.service.mapper.VPVouchersMapper;

/**
 * Service Implementation for managing {@link VPVouchers}.
 */
@Service
@Transactional
public class VPVouchersServiceImpl implements VPVouchersService {

    private final Logger log = LoggerFactory.getLogger(VPVouchersServiceImpl.class);

    private final VPVouchersRepository vPVouchersRepository;

    private final VPVouchersMapper vPVouchersMapper;

    public VPVouchersServiceImpl(VPVouchersRepository vPVouchersRepository, VPVouchersMapper vPVouchersMapper) {
        this.vPVouchersRepository = vPVouchersRepository;
        this.vPVouchersMapper = vPVouchersMapper;
    }

    @Override
    public VPVouchersDTO save(VPVouchersDTO vPVouchersDTO) {
        log.debug("Request to save VPVouchers : {}", vPVouchersDTO);
        VPVouchers vPVouchers = vPVouchersMapper.toEntity(vPVouchersDTO);
        vPVouchers = vPVouchersRepository.save(vPVouchers);
        return vPVouchersMapper.toDto(vPVouchers);
    }

    @Override
    public Optional<VPVouchersDTO> partialUpdate(VPVouchersDTO vPVouchersDTO) {
        log.debug("Request to partially update VPVouchers : {}", vPVouchersDTO);

        return vPVouchersRepository
            .findById(vPVouchersDTO.getId())
            .map(existingVPVouchers -> {
                vPVouchersMapper.partialUpdate(existingVPVouchers, vPVouchersDTO);

                return existingVPVouchers;
            })
            .map(vPVouchersRepository::save)
            .map(vPVouchersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VPVouchersDTO> findAll() {
        log.debug("Request to get all VPVouchers");
        return vPVouchersRepository.findAll().stream().map(vPVouchersMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VPVouchersDTO> findOne(Long id) {
        log.debug("Request to get VPVouchers : {}", id);
        return vPVouchersRepository.findById(id).map(vPVouchersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VPVouchers : {}", id);
        vPVouchersRepository.deleteById(id);
    }

    @Override
    // @Transactional
    public Optional<VPVouchersDTO> getValidVoucher(String productId) {
        log.debug("Request to get Valid VPVouchers : {}", productId);
        return vPVouchersRepository.getValidVoucher(productId).map(vPVouchersMapper::toDto);
    }

    @Override
    // @Transactional
    public List<VPVouchersDTO> getValidVoucherWithLock(String productId) {
        log.debug("Request to get Valid VPVouchers : {}", productId);
        return vPVouchersRepository.getValidVoucherWithLock(productId, (Pageable) PageRequest.of(0,1))
            .stream().map(vPVouchersMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void issueVoucher(String incomingTrxId, Long id) {
        log.debug("Request to issue VPVouchers : {}, {}", incomingTrxId, id);
        vPVouchersRepository.issueVoucher(incomingTrxId, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VPVouchersDTO> getValidVoucherForReturn(String productId, Long id, String trxId) {
        return vPVouchersRepository.getValidVoucherForReturn(productId, id, trxId).map(vPVouchersMapper::toDto);
    }

    @Override
    public void updateReturnedVoucher(Long id) {
        vPVouchersRepository.updateReturnedVoucher(id);
    }

    @Override
    public Optional<Product> getValidVoucherForProduct(String productId) {
        return vPVouchersRepository.getValidVoucherForProduct(productId);
    }

    @Override
    public Optional<Product> getValidVoucherForProductGenericVoucher(String productId) {
        return vPVouchersRepository.getValidVoucherForProductGenericVoucher(productId);
    }
}
