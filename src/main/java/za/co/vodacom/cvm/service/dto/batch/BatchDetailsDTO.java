package za.co.vodacom.cvm.service.dto.batch;

import java.time.ZonedDateTime;

public class BatchDetailsDTO {

    private String id;

    private String type;

    private String description;

    private String voucherDescription;

    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private ZonedDateTime expiryDate;

    private String fileName;

    private Long count;

    public BatchDetailsDTO(String id, String type, String description, String voucherDescription, ZonedDateTime startDate, ZonedDateTime endDate, ZonedDateTime expiryDate, String fileName,Long count) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.voucherDescription = voucherDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expiryDate = expiryDate;
        this.fileName = fileName;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getVoucherDescription() {
        return voucherDescription;
    }

    public void setVoucherDescription(String voucherDescription) {
        this.voucherDescription = voucherDescription;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "BatchDetailsDTO{" +
            "id='" + id + '\'' +
            ", type='" + type + '\'' +
            ", description='" + description + '\'' +
            ", voucherDescription='" + voucherDescription + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", expiryDate=" + expiryDate +
            ", fileName='" + fileName + '\'' +
            ", count=" + count +
            '}';
    }


}
