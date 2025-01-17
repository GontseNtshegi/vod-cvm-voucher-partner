package za.co.vodacom.cvm.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.service.dto.campaign.QuantityDetailsDTO;
import za.co.vodacom.cvm.service.dto.product.Product;
import za.co.vodacom.cvm.service.dto.product.ProductQuantityDTO;

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
     * Get a valid voucher with lock
     * @param productId
     * @return
     */
    List<VPVouchers> getValidVoucherWithLock(String productId);

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

    List<QuantityDetailsDTO> getVoucherQuantity(Long campaignId, ZonedDateTime extDate);

    List<VPVouchers> getVouchersWithStatusA(String productId);

    /**
     * Get a valid voucher
     * @param productId
     * @return
     */
    List<ProductQuantityDTO> getValidVoucher(String productId);

    List<VPVouchers> getVoucherSkipLocked(List<String> ProductIds);
    List<VPVouchers> getBatchValidation(String batchId);
}
