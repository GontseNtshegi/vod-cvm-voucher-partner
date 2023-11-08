package za.co.vodacom.cvm.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    //@NotNull
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

    @Column(name = "delete_date")
    private ZonedDateTime deleteDate;

    @NotNull
    @Size(max = 1)
    @Column(name = "status", length = 1, nullable = false)
    private String status;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Size(max = 100)
    @Column(name = "create_user", length = 100, nullable = false)
    private String createUser;

    @Size(max = 100)
    @Column(name = "activate_user", length = 100, nullable = false)
    private String activateUser;

    @Size(max = 100)
    @Column(name = "delete_user", length = 100)
    private String deleteUser;

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

    public ZonedDateTime getDeleteDate() {
        return deleteDate;
    }

    public VPBatch deleteDate(ZonedDateTime deleteDate) {
        this.deleteDate = deleteDate;
        return this;
    }

    public void setDeleteDate(ZonedDateTime deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getStatus() {
        return status;
    }

    public VPBatch status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public VPBatch name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateUser() {
        return createUser;
    }

    public VPBatch createUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getActivateUser() {
        return activateUser;
    }

    public VPBatch activateUser(String activateUser) {
        this.activateUser = activateUser;
        return this;
    }

    public void setActivateUser(String activateUser) {
        this.activateUser = activateUser;
    }

    public String getDeleteUser() {
        return deleteUser;
    }

    public VPBatch deleteUser(String deleteUser) {
        this.deleteUser = deleteUser;
        return this;
    }

    public void setDeleteUser(String deleteUser) {
        this.deleteUser = deleteUser;
    }

    @Override
    public String toString() {
        return (
            "VPBatch{" +
            "id=" +
            id +
            ", createDate=" +
            createDate +
            ", loadDate=" +
            loadDate +
            ", comment='" +
            comment +
            '\'' +
            ", restrictedYN='" +
            restrictedYN +
            '\'' +
            ", deleteDate=" +
            deleteDate +
            ", status='" +
            status +
            '\'' +
            ", name='" +
            name +
            '\'' +
            ", createUser='" +
            createUser +
            '\'' +
            ", activateUser='" +
            activateUser +
            '\'' +
            ", deleteUser='" +
            deleteUser +
            '\'' +
            '}'
        );
    }
}
