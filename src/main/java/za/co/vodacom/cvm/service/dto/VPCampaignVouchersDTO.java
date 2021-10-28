package za.co.vodacom.cvm.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link za.co.vodacom.cvm.domain.VPCampaignVouchers} entity.
 */
public class VPCampaignVouchersDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer campaignId;

    @NotNull
    @Size(max = 30)
    private String productId;

    @NotNull
    private ZonedDateTime createDate;

    @NotNull
    private ZonedDateTime modifiedDate;

    @NotNull
    @Size(max = 1)
    private String activeYN;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getActiveYN() {
        return activeYN;
    }

    public void setActiveYN(String activeYN) {
        this.activeYN = activeYN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VPCampaignVouchersDTO)) {
            return false;
        }

        VPCampaignVouchersDTO vPCampaignVouchersDTO = (VPCampaignVouchersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vPCampaignVouchersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VPCampaignVouchersDTO{" +
            "id=" + getId() +
            ", campaignId=" + getCampaignId() +
            ", productId='" + getProductId() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", activeYN='" + getActiveYN() + "'" +
            "}";
    }
}
