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
 * A VPCampaign.
 */
@Entity
@Table(name = "vp_campaign")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VPCampaign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    //@NotNull
    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;

    //@NotNull
    @Column(name = "end_date", nullable = false)
    private ZonedDateTime endDate;

    /*@OneToMany(mappedBy = "campaignId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "campaignId", "productId" }, allowSetters = true)
    private Set<VPCampaignVouchers> ids = new HashSet<>();*/

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VPCampaign id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public VPCampaign name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getStartDate() {
        return this.startDate;
    }

    public VPCampaign startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return this.endDate;
    }

    public VPCampaign endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    /* public Set<VPCampaignVouchers> getIds() {
        return this.ids;
    }

     public VPCampaign ids(Set<VPCampaignVouchers> vPCampaignVouchers) {
        this.setIds(vPCampaignVouchers);
        return this;
    }

   public VPCampaign addId(VPCampaignVouchers vPCampaignVouchers) {
        this.ids.add(vPCampaignVouchers);
        vPCampaignVouchers.setCampaignId(this);
        return this;
    }

    public VPCampaign removeId(VPCampaignVouchers vPCampaignVouchers) {
        this.ids.remove(vPCampaignVouchers);
        vPCampaignVouchers.setCampaignId(null);
        return this;
    }

    public void setIds(Set<VPCampaignVouchers> vPCampaignVouchers) {
        if (this.ids != null) {
            this.ids.forEach(i -> i.setCampaignId(null));
        }
        if (vPCampaignVouchers != null) {
            vPCampaignVouchers.forEach(i -> i.setCampaignId(this));
        }
        this.ids = vPCampaignVouchers;
    }*/

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VPCampaign)) {
            return false;
        }
        return id != null && id.equals(((VPCampaign) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VPCampaign{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
