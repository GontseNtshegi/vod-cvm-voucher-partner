package za.co.vodacom.cvm.service.dto.product;

import java.time.LocalDateTime;

public class Quantity {
    String productId;
    String description;

    String productDescription;
    int count;
    LocalDateTime startDate;
    LocalDateTime endDate;
    LocalDateTime expiryDate;
    String type;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public Quantity(String productId, String description, String productDescription, int count, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime expiryDate, String type) {
        this.productId = productId;
        this.description = description;
        this.productDescription = productDescription;
        this.count = count;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expiryDate = expiryDate;
        this.type = type;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Quantity{" +
            "productId='" + productId + '\'' +
            ", description='" + description + '\'' +
            ", productDescription='" + productDescription + '\'' +
            ", count=" + count +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", expiryDate=" + expiryDate +
            ", type='" + type + '\'' +
            '}';
    }
}
