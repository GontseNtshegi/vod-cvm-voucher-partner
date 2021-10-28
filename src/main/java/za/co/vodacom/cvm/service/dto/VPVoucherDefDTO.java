package za.co.vodacom.cvm.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link za.co.vodacom.cvm.domain.VPVoucherDef} entity.
 */
public class VPVoucherDefDTO implements Serializable {

    @NotNull
    @Size(max = 30)
    private String id;

    @NotNull
    @Size(max = 100)
    private String description;

    @NotNull
    @Size(max = 30)
    private String type;

    @NotNull
    @Size(max = 100)
    private String category;

    @NotNull
    @Size(max = 100)
    private String vendor;

    @Size(max = 50)
    private String extId;

    @Size(max = 50)
    private String extSystem;

    @Size(max = 50)
    private String templateId;

    @NotNull
    private Integer validityPeriod;

    @NotNull
    private Integer cacheQuantity;

    @NotNull
    @Size(max = 1)
    private String encryptedYN;

    private ZonedDateTime modifiedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public String getExtSystem() {
        return extSystem;
    }

    public void setExtSystem(String extSystem) {
        this.extSystem = extSystem;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Integer getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(Integer validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public Integer getCacheQuantity() {
        return cacheQuantity;
    }

    public void setCacheQuantity(Integer cacheQuantity) {
        this.cacheQuantity = cacheQuantity;
    }

    public String getEncryptedYN() {
        return encryptedYN;
    }

    public void setEncryptedYN(String encryptedYN) {
        this.encryptedYN = encryptedYN;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VPVoucherDefDTO)) {
            return false;
        }

        VPVoucherDefDTO vPVoucherDefDTO = (VPVoucherDefDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vPVoucherDefDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VPVoucherDefDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", category='" + getCategory() + "'" +
            ", vendor='" + getVendor() + "'" +
            ", extId='" + getExtId() + "'" +
            ", extSystem='" + getExtSystem() + "'" +
            ", templateId='" + getTemplateId() + "'" +
            ", validityPeriod=" + getValidityPeriod() +
            ", cacheQuantity=" + getCacheQuantity() +
            ", encryptedYN='" + getEncryptedYN() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
