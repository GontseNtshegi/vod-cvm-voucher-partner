package za.co.vodacom.cvm.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VPUsers.
 */
@Entity
@Table(name = "vp_users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VPUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Size(max = 100)
    @Column(name = "user_id", length = 100, nullable = false, unique = true)
    private Long id;

    /*@NotNull
    @Size(max = 100)
    @Column(name = "user_id", length = 100, nullable = false, unique = true)
    private String userId;*/

    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

    @NotNull
    @Column(name = "modified_date", nullable = false)
    private ZonedDateTime modifiedDate;

    @NotNull
    @Size(max = 1)
    @Column(name = "active_yn", length = 1, nullable = false)
    private String activeYN;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VPUsers id(Long id) {
        this.id = id;
        return this;
    }

    /*public String getUserId() {
        return this.userId;
    }

    public VPUsers userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }*/

    public ZonedDateTime getCreateDate() {
        return this.createDate;
    }

    public VPUsers createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public VPUsers modifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getActiveYN() {
        return this.activeYN;
    }

    public VPUsers activeYN(String activeYN) {
        this.activeYN = activeYN;
        return this;
    }

    public void setActiveYN(String activeYN) {
        this.activeYN = activeYN;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VPUsers)) {
            return false;
        }
        return id != null && id.equals(((VPUsers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VPUsers{" +
            "id=" + getId() +
           // ", userId='" + getUserId() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", activeYN='" + getActiveYN() + "'" +
            "}";
    }
}
