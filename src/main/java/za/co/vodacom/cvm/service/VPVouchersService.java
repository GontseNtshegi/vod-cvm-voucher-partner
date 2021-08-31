package za.co.vodacom.cvm.service;

import java.util.List;
import java.util.Optional;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.service.dto.product.Product;

/**
 * Service Interface for managing {@link VPVouchers}.
 */
public interface VPVouchersService {
    /**
     * Save a vPVouchers.
     *
     * @param vPVouchers the entity to save.
     * @return the persisted entity.
     */
    VPVouchers save(VPVouchers vPVouchers);

    /**
     * Partially updates a vPVouchers.
     *
     * @param vPVouchers the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VPVouchers> partialUpdate(VPVouchers vPVouchers);

    /**
     * Get all the vPVouchers.
     *
     * @return the list of entities.
     */
    List<VPVouchers> findAll();

    /**
     * Get the "id" vPVouchers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VPVouchers> findOne(Long id);

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
    Optional<VPVouchers> getValidVoucher(String productId);

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
    Optional<VPVouchers> getValidVoucherForReturn(String productId, Long id, String trxId);

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
