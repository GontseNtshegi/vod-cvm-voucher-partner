package za.co.vodacom.cvm.service.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.repository.VPVouchersRepository;
import za.co.vodacom.cvm.service.VPVouchersService;
import za.co.vodacom.cvm.service.dto.product.Product;

/**
 * Service Implementation for managing {@link VPVouchers}.
 */
@Service
@Transactional
public class VPVouchersServiceImpl implements VPVouchersService {

    private final Logger log = LoggerFactory.getLogger(VPVouchersServiceImpl.class);

    private final VPVouchersRepository vPVouchersRepository;

    public VPVouchersServiceImpl(VPVouchersRepository vPVouchersRepository) {
        this.vPVouchersRepository = vPVouchersRepository;
    }

    @Override
    public VPVouchers save(VPVouchers vPVouchers) {
        log.debug("Request to save VPVouchers : {}", vPVouchers);
        return vPVouchersRepository.save(vPVouchers);
    }

    @Override
    public Optional<VPVouchers> partialUpdate(VPVouchers vPVouchers) {
        log.debug("Request to partially update VPVouchers : {}", vPVouchers);

        return vPVouchersRepository
            .findById(vPVouchers.getId())
            .map(
                existingVPVouchers -> {
                    if (vPVouchers.getBatchId() != null) {
                        existingVPVouchers.setBatchId(vPVouchers.getBatchId());
                    }
                    if (vPVouchers.getFileId() != null) {
                        existingVPVouchers.setFileId(vPVouchers.getFileId());
                    }
                    if (vPVouchers.getId() != null) {
                        //existingVPVouchers.setProductId(vPVouchers.getId (vPVouchers.getVoucherCode() != null) {
                        // existingVPVouchers.setVoucherCode(vPVouchers.getVoucherCode());
                    }
                    if (vPVouchers.getDescription() != null) {
                        existingVPVouchers.setDescription(vPVouchers.getDescription());
                    }
                    if (vPVouchers.getCreateDate() != null) {
                        existingVPVouchers.setCreateDate(vPVouchers.getCreateDate());
                    }
                    if (vPVouchers.getStartDate() != null) {
                        existingVPVouchers.setStartDate(vPVouchers.getStartDate());
                    }
                    if (vPVouchers.getEndDate() != null) {
                        existingVPVouchers.setEndDate(vPVouchers.getEndDate());
                    }
                    if (vPVouchers.getExpiryDate() != null) {
                        existingVPVouchers.setExpiryDate(vPVouchers.getExpiryDate());
                    }
                    if (vPVouchers.getCollectionPoint() != null) {
                        existingVPVouchers.setCollectionPoint(vPVouchers.getCollectionPoint());
                    }
                    if (vPVouchers.getIssuedDate() != null) {
                        existingVPVouchers.setIssuedDate(vPVouchers.getIssuedDate());
                    }
                    if (vPVouchers.getReversedDate() != null) {
                        existingVPVouchers.setReversedDate(vPVouchers.getReversedDate());
                    }
                    if (vPVouchers.getSourceTrxid() != null) {
                        existingVPVouchers.setSourceTrxid(vPVouchers.getSourceTrxid());
                    }
                    if (vPVouchers.getQuantity() != null) {
                        existingVPVouchers.setQuantity(vPVouchers.getQuantity());
                    }

                    return existingVPVouchers;
                }
            )
            .map(vPVouchersRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VPVouchers> findAll() {
        log.debug("Request to get all VPVouchers");
        return vPVouchersRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VPVouchers> findOne(Long id) {
        log.debug("Request to get VPVouchers : {}", id);
        return vPVouchersRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VPVouchers : {}", id);
        vPVouchersRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VPVouchers> getValidVoucher(String productId) {
        log.debug("Request to get Valid VPVouchers : {}", productId);
        return vPVouchersRepository.getValidVoucher(productId);
    }

    @Override
    public void issueVoucher(String incomingTrxId, Long id) {
        log.debug("Request to issue VPVouchers : {}, {}", incomingTrxId, id);
        vPVouchersRepository.issueVoucher(incomingTrxId, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VPVouchers> getValidVoucherForReturn(String productId, Long id, String trxId) {
        return vPVouchersRepository.getValidVoucherForReturn(productId, id, trxId);
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
