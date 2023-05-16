package za.co.vodacom.cvm.service.dto.campaign;

import java.time.ZonedDateTime;

public class QuantityDetailsDTO {
    String productId;
    String type;
    String description;
    String description_1;
    ZonedDateTime startDate;
    ZonedDateTime endDate;
    ZonedDateTime voucherExpiryDate;
    Long count;

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public ZonedDateTime getvoucherExpiryDate() {
        return voucherExpiryDate;
    }

    public QuantityDetailsDTO(String productId, String type, String description, String description_1, ZonedDateTime startDate, ZonedDateTime endDate, ZonedDateTime voucherExpiryDate, Long count) {
        this.productId = productId;
        this.type = type;
        this.description = description;
        this.description_1 = description_1;
        this.startDate = startDate;
        this.endDate = endDate;
        this.voucherExpiryDate = voucherExpiryDate;
        this.count = count;
    }

    public QuantityDetailsDTO() {
    }

    public String getProductDescription() {
        return description_1;
    }

    public void setProductDescription(String productDescription) {
        this.description_1 = description_1;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public void setvoucherExpiryDate(ZonedDateTime voucherExpiryDate) {
        this.voucherExpiryDate = voucherExpiryDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription_1() {
        return description_1;
    }

    public void setDescription_1(String description_1) {
        this.description_1 = description_1;
    }

    @Override
    public String toString() {
        return "Quantity{" +
            "productId='" + productId + '\'' +
            ", description='" + description + '\'' +
            ", productDescription='" + description_1 + '\'' +
            ", count=" + count +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", voucherExpiryDate=" + voucherExpiryDate +
            ", type='" + type + '\'' +
            '}';
    }
}
