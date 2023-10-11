package za.co.vodacom.cvm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VPVoucherDef.
 */
@Entity
@Table(name = "vp_voucher_def")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VPVoucherDef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Size(max = 30)
    @Column(name = "product_id", length = 30, nullable = false, unique = true)
    private String id;

    /* @NotNull
    @Size(max = 30)
    @Column(name = "product_id", length = 30, nullable = false, unique = true)
    private String productId;*/

    @NotNull
    @Size(max = 100)
    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @NotNull
    @Size(max = 30)
    @Column(name = "type", length = 30, nullable = false)
    private String type;

    @NotNull
    @Size(max = 100)
    @Column(name = "category", length = 100, nullable = false)
    private String category;

    @NotNull
    @Size(max = 100)
    @Column(name = "vendor", length = 100, nullable = false)
    private String vendor;

    @Size(max = 50)
    @Column(name = "ext_id", length = 50)
    private String extId;

    @Size(max = 50)
    @Column(name = "ext_system", length = 50)
    private String extSystem;

    @Size(max = 50)
    @Column(name = "template_id", length = 50)
    private String templateId;

    @NotNull
    @Column(name = "validity_period", nullable = false)
    private Integer validityPeriod;

    @NotNull
    @Column(name = "cache_quantity", nullable = false)
    private Integer cacheQuantity;

    @NotNull
    @Size(max = 1)
    @Column(name = "encrypted_yn", length = 1, nullable = false)
    private String encryptedYN;

    @Column(name = "modified_date")
    private ZonedDateTime modifiedDate;

    @OneToMany(mappedBy = "productId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productId" }, allowSetters = true)
    private Set<VPVouchers> productIds = new HashSet<>();

    @NotNull
    @Size(max = 100)
    @Column(name = "collectionPoint", length = 100, nullable = false)
    private String collectionPoint;

    /*@OneToMany(mappedBy = "productId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "campaignId", "productId" }, allowSetters = true)
    private Set<VPCampaignVouchers> productIds = new HashSet<>();*/

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VPVoucherDef id(String id) {
        this.id = id;
        return this;
    }

    /*public String getProductId() {
        return this.productId;
    }

    public VPVoucherDef productId(String productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }*/

    public String getDescription() {
        return this.description;
    }

    public VPVoucherDef description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public VPVoucherDef type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return this.category;
    }

    public VPVoucherDef category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVendor() {
        return this.vendor;
    }

    public VPVoucherDef vendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getExtId() {
        return this.extId;
    }

    public VPVoucherDef extId(String extId) {
        this.extId = extId;
        return this;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public String getExtSystem() {
        return this.extSystem;
    }

    public VPVoucherDef extSystem(String extSystem) {
        this.extSystem = extSystem;
        return this;
    }

    public void setExtSystem(String extSystem) {
        this.extSystem = extSystem;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public VPVoucherDef templateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Integer getValidityPeriod() {
        return this.validityPeriod;
    }

    public VPVoucherDef validityPeriod(Integer validityPeriod) {
        this.validityPeriod = validityPeriod;
        return this;
    }

    public void setValidityPeriod(Integer validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public Integer getCacheQuantity() {
        return this.cacheQuantity;
    }

    public VPVoucherDef cacheQuantity(Integer cacheQuantity) {
        this.cacheQuantity = cacheQuantity;
        return this;
    }

    public void setCacheQuantity(Integer cacheQuantity) {
        this.cacheQuantity = cacheQuantity;
    }

    public String getEncryptedYN() {
        return this.encryptedYN;
    }

    public VPVoucherDef encryptedYN(String encryptedYN) {
        this.encryptedYN = encryptedYN;
        return this;
    }

    public void setEncryptedYN(String encryptedYN) {
        this.encryptedYN = encryptedYN;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public VPVoucherDef modifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCollectionPoint() {
        return collectionPoint;
    }

    public VPVoucherDef collectionPoint(String collectionPoint) {
        this.collectionPoint = collectionPoint;
        return this;
    }

    public void setCollectionPoint(String collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    /*public Set<VPVouchers> getProductIds() {
        return this.productIds;
    }

    public void setProductIds(Set<VPVouchers> vPVouchers) {
        if (this.productIds != null) {
            this.productIds.forEach(i -> i.setProductId(null));
        }
        if (vPVouchers != null) {
            vPVouchers.forEach(i -> i.setProductId(this));
        }
        this.productIds = vPVouchers;
    }

    public VPVoucherDef productIds(Set<VPVouchers> vPVouchers) {
        this.setProductIds(vPVouchers);
        return this;
    }

    public VPVoucherDef addProductId(VPVouchers vPVouchers) {
        this.productIds.add(vPVouchers);
        vPVouchers.setProductId(this);
        return this;
    }*/

    /* public VPVoucherDef removeProductId(VPVouchers vPVouchers) {
        this.productIds.remove(vPVouchers);
        vPVouchers.setProductId(null);
        return this;
    }

    public void setProductIds(Set<VPVouchers> vPVouchers) {
        if (this.productIds != null) {
            this.productIds.forEach(i -> i.setProductId(null));
        }
        if (vPVouchers != null) {
            vPVouchers.forEach(i -> i.setProductId(this));
        }
        this.productIds = vPVouchers;
    }

    public Set<VPCampaignVouchers> getProductIds() {
        return this.productIds;
    }

    public VPVoucherDef productIds(Set<VPCampaignVouchers> vPCampaignVouchers) {
        this.setProductIds(vPCampaignVouchers);
        return this;
    }

    public VPVoucherDef addProductId(VPCampaignVouchers vPCampaignVouchers) {
        this.productIds.add(vPCampaignVouchers);
        vPCampaignVouchers.setProductId(this);
        return this;
    }

    public VPVoucherDef removeProductId(VPCampaignVouchers vPCampaignVouchers) {
        this.productIds.remove(vPCampaignVouchers);
        vPCampaignVouchers.setProductId(null);
        return this;
    }

    public void setProductIds(Set<VPCampaignVouchers> vPCampaignVouchers) {
        if (this.productIds != null) {
            this.productIds.forEach(i -> i.setProductId(null));
        }
        if (vPCampaignVouchers != null) {
            vPCampaignVouchers.forEach(i -> i.setProductId(this));
        }
        this.productIds = vPCampaignVouchers;
    }*/

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VPVoucherDef)) {
            return false;
        }
        return id != null && id.equals(((VPVoucherDef) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VPVoucherDef{" +
            "id=" + getId() +
           // ", productId='" + getProductId() + "'" +
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
            ", collectionPoint='" + getCollectionPoint() + "'" +
            "}";
    }
}
