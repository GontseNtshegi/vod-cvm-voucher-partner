package za.co.vodacom.cvm.service.dto.campaign;

import java.time.ZonedDateTime;

public class QuantityDetailsDTO {
    String productId;
    String type;
    String description;
    String description_1;
    ZonedDateTime endDate;
    ZonedDateTime expiryDate;
    Long count;

    public QuantityDetailsDTO(String productId, String type, String description, String description_1, ZonedDateTime endDate, ZonedDateTime expiryDate, Long count) {
        this.productId = productId;
        this.type = type;
        this.description = description;
        this.description_1 = description_1;
        this.endDate = endDate;
        this.expiryDate = expiryDate;
        this.count = count;
    }

    public QuantityDetailsDTO() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription_1() {
        return description_1;
    }

    public void setDescription_1(String description_1) {
        this.description_1 = description_1;
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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
