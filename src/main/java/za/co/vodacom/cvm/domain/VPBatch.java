package za.co.vodacom.cvm.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VPBatch.
 */
@Entity
@Table(name = "vp_batch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VPBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

    @Column(name = "load_date")
    private ZonedDateTime loadDate;

    @NotNull
    @Size(max = 100)
    @Column(name = "comment", length = 100, nullable = false)
    private String comment;

    @NotNull
    @Size(max = 1)
    @Column(name = "restricted_yn", length = 1, nullable = false)
    private String restrictedYN;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VPBatch id(Long id) {
        this.id = id;
        return this;
    }

    public ZonedDateTime getCreateDate() {
        return this.createDate;
    }

    public VPBatch createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getLoadDate() {
        return this.loadDate;
    }

    public VPBatch loadDate(ZonedDateTime loadDate) {
        this.loadDate = loadDate;
        return this;
    }

    public void setLoadDate(ZonedDateTime loadDate) {
        this.loadDate = loadDate;
    }

    public String getComment() {
        return this.comment;
    }

    public VPBatch comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRestrictedYN() {
        return this.restrictedYN;
    }

    public VPBatch restrictedYN(String restrictedYN) {
        this.restrictedYN = restrictedYN;
        return this;
    }

    public void setRestrictedYN(String restrictedYN) {
        this.restrictedYN = restrictedYN;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public VPBatch userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VPBatch)) {
            return false;
        }
        return id != null && id.equals(((VPBatch) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VPBatch{" +
            "id=" + getId() +
            ", createDate='" + getCreateDate() + "'" +
            ", loadDate='" + getLoadDate() + "'" +
            ", comment='" + getComment() + "'" +
            ", restrictedYN='" + getRestrictedYN() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
