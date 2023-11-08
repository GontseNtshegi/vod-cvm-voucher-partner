package za.co.vodacom.cvm.service.dto.product;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class ProductQuantityDTO {
    String productId;
    Integer quantity;
    String sourceTrxid;
    String description;
    ZonedDateTime endDate;
    ZonedDateTime startDate;
    ZonedDateTime reversedDate;
    ZonedDateTime issuedDate;
    String collectionPoint;
    ZonedDateTime expiryDate;
    ZonedDateTime createDate;
    String voucherCode;
    Integer fileId;

    public ProductQuantityDTO(String productId,  String description, Integer quantity, String sourceTrxid, ZonedDateTime endDate, ZonedDateTime startDate, ZonedDateTime reversedDate, ZonedDateTime issuedDate,  ZonedDateTime expiryDate, ZonedDateTime createDate,String collectionPoint, String voucherCode, Integer fileId) {
        this.productId = productId;
        this.quantity = quantity;
        this.sourceTrxid = sourceTrxid;
        this.description = description;
        this.endDate = endDate;
        this.startDate = startDate;
        this.reversedDate = reversedDate;
        this.issuedDate = issuedDate;
        this.collectionPoint = collectionPoint;
        this.expiryDate = expiryDate;
        this.createDate = createDate;
        this.voucherCode = voucherCode;
        this.fileId = fileId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSourceTrxid() {
        return sourceTrxid;
    }

    public void setSourceTrxid(String sourceTrxid) {
        this.sourceTrxid = sourceTrxid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getReversedDate() {
        return reversedDate;
    }

    public void setReversedDate(ZonedDateTime reversedDate) {
        this.reversedDate = reversedDate;
    }

    public ZonedDateTime getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(ZonedDateTime issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(String collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "ProductQuantityDTO{" +
            "productId='" + productId + '\'' +
            ", quantity=" + quantity +
            ", sourceTrxid='" + sourceTrxid + '\'' +
            ", description='" + description + '\'' +
            ", endDate=" + endDate +
            ", startDate=" + startDate +
            ", reversedDate=" + reversedDate +
            ", issuedDate=" + issuedDate +
            ", collectionPoint='" + collectionPoint + '\'' +
            ", expiryDate=" + expiryDate +
            ", createDate=" + createDate +
            ", voucherCode='" + voucherCode + '\'' +
            ", fileId=" + fileId +
            '}';
    }
}
