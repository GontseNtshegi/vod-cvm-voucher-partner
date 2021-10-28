package za.co.vodacom.cvm.service;

import java.util.List;
import java.util.Optional;
import za.co.vodacom.cvm.service.dto.VPVouchersDTO;
import za.co.vodacom.cvm.service.dto.product.Product;

/**
 * Service Interface for managing {@link za.co.vodacom.cvm.domain.VPVouchers}.
 */
public interface VPVouchersService {
    /**
     * Save a vPVouchers.
     *
     * @param vPVouchersDTO the entity to save.
     * @return the persisted entity.
     */
    VPVouchersDTO save(VPVouchersDTO vPVouchersDTO);

    /**
     * Partially updates a vPVouchers.
     *
     * @param vPVouchersDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VPVouchersDTO> partialUpdate(VPVouchersDTO vPVouchersDTO);

    /**
     * Get all the vPVouchers.
     *
     * @return the list of entities.
     */
    List<VPVouchersDTO> findAll();

    /**
     * Get the "id" vPVouchers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VPVouchersDTO> findOne(Long id);

    /**
     * Delete the "id" vPVouchers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get a valid voucher
     * @param productId
     * @return
     */
    Optional<VPVouchersDTO> getValidVoucher(String productId);

    /**
     * Get a valid voucher with lock
     * @param productId
     * @return
     */
    List<VPVouchersDTO> getValidVoucherWithLock(String productId);

    /**
     * Issue voucher
     * @param incomingTrxId
     * @param id
     */
    void issueVoucher(String incomingTrxId, Long id);

    /**
     * Get a valid voucher for users who want to return a voucher
     * @param productId
     * @param id
     * @param trxId
     * @return
     */
    Optional<VPVouchersDTO> getValidVoucherForReturn(String productId, Long id, String trxId);

    /**
     * Update a returned voucher
     * @param id
     */
    void updateReturnedVoucher(Long id);

    /**
     *
     * @param productId
     * @return
     */
    Optional<Product> getValidVoucherForProduct(String productId);

    /**
     *
     * @param productId
     * @return
     */
    Optional<Product> getValidVoucherForProductGenericVoucher(String productId);
}
