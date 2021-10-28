package za.co.vodacom.cvm.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link za.co.vodacom.cvm.domain.VPVouchers} entity.
 */
public class VPVouchersDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer batchId;

    private Integer fileId;

    @NotNull
    @Size(max = 20)
    private String productId;

    @NotNull
    @Size(max = 60)
    private String voucherCode;

    @NotNull
    @Size(max = 100)
    private String description;

    @NotNull
    private ZonedDateTime createDate;

    @NotNull
    private ZonedDateTime startDate;

    @NotNull
    private ZonedDateTime endDate;

    private ZonedDateTime expiryDate;

    @NotNull
    @Size(max = 100)
    private String collectionPoint;

    private ZonedDateTime issuedDate;

    @NotNull
    private ZonedDateTime reversedDate;

    @Size(max = 60)
    private String sourceTrxid;

    @NotNull
    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(String collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    public ZonedDateTime getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(ZonedDateTime issuedDate) {
        this.issuedDate = issuedDate;
    }

    public ZonedDateTime getReversedDate() {
        return reversedDate;
    }

    public void setReversedDate(ZonedDateTime reversedDate) {
        this.reversedDate = reversedDate;
    }

    public String getSourceTrxid() {
        return sourceTrxid;
    }

    public void setSourceTrxid(String sourceTrxid) {
        this.sourceTrxid = sourceTrxid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VPVouchersDTO)) {
            return false;
        }

        VPVouchersDTO vPVouchersDTO = (VPVouchersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vPVouchersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VPVouchersDTO{" +
            "id=" + getId() +
            ", batchId=" + getBatchId() +
            ", fileId=" + getFileId() +
            ", productId='" + getProductId() + "'" +
            ", voucherCode='" + getVoucherCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", collectionPoint='" + getCollectionPoint() + "'" +
            ", issuedDate='" + getIssuedDate() + "'" +
            ", reversedDate='" + getReversedDate() + "'" +
            ", sourceTrxid='" + getSourceTrxid() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
