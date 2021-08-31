package za.co.vodacom.cvm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VPCampaignVouchers.
 */
@Entity
@Table(name = "vp_campaign_vouchers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VPCampaignVouchers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "campaign_id", nullable = false)
    private Long campaignId;

    @NotNull
    @Size(max = 30)
    @Column(name = "product_id", length = 30, nullable = false)
    private String productId;

    //@NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

    //@NotNull
    @Column(name = "modified_date", nullable = false)
    private ZonedDateTime modifiedDate;

    @NotNull
    @Size(max = 1)
    @Column(name = "active_yn", length = 1, nullable = false)
    private String activeYN;

    /*@ManyToOne
    @JsonIgnoreProperties(value = { "ids" }, allowSetters = true)
    private VPCampaign campaignId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "productIds", "productIds" }, allowSetters = true)
    private VPVoucherDef productId;*/

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VPCampaignVouchers id(Long id) {
        this.id = id;
        return this;
    }

    public Long getCampaignId() {
        return this.campaignId;
    }

    public VPCampaignVouchers campaignId(Long campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getProductId() {
        return this.productId;
    }

    public VPCampaignVouchers productId(String productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ZonedDateTime getCreateDate() {
        return this.createDate;
    }

    public VPCampaignVouchers createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public VPCampaignVouchers modifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getActiveYN() {
        return this.activeYN;
    }

    public VPCampaignVouchers activeYN(String activeYN) {
        this.activeYN = activeYN;
        return this;
    }

    public void setActiveYN(String activeYN) {
        this.activeYN = activeYN;
    }

    /*public VPCampaign getCampaignId() {
        return this.campaignId;
    }

    public VPCampaignVouchers campaignId(VPCampaign vPCampaign) {
        this.setCampaignId(vPCampaign);
        return this;
    }*/

    /*public void setCampaignId(VPCampaign vPCampaign) {
        this.campaignId = vPCampaign;
    }

    public VPVoucherDef getProductId() {
        return this.productId;
    }

    public VPCampaignVouchers productId(VPVoucherDef vPVoucherDef) {
        this.setProductId(vPVoucherDef);
        return this;
    }*/

    /*public void setProductId(VPVoucherDef vPVoucherDef) {
        this.productId = vPVoucherDef;
    }*/

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VPCampaignVouchers)) {
            return false;
        }
        return id != null && id.equals(((VPCampaignVouchers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VPCampaignVouchers{" +
            "id=" + getId() +
            ", campaignId=" + getCampaignId() +
            ", productId='" + getProductId() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", activeYN='" + getActiveYN() + "'" +
            "}";
    }
}
