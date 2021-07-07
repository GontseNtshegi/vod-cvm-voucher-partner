package za.co.vodacom.cvm.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VPFileLoad.
 */
@Entity
@Table(name = "vp_file_load")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VPFileLoad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "file_name", length = 100, nullable = false)
    private String fileName;

    @NotNull
    @Column(name = "batch_id", nullable = false)
    private Integer batchId;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

    @Column(name = "completed_date")
    private ZonedDateTime completedDate;

    @Column(name = "num_loaded")
    private Integer numLoaded;

    @Column(name = "num_failed")
    private Integer numFailed;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VPFileLoad id(Long id) {
        this.id = id;
        return this;
    }

    public String getFileName() {
        return this.fileName;
    }

    public VPFileLoad fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getBatchId() {
        return this.batchId;
    }

    public VPFileLoad batchId(Integer batchId) {
        this.batchId = batchId;
        return this;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public ZonedDateTime getCreateDate() {
        return this.createDate;
    }

    public VPFileLoad createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getCompletedDate() {
        return this.completedDate;
    }

    public VPFileLoad completedDate(ZonedDateTime completedDate) {
        this.completedDate = completedDate;
        return this;
    }

    public void setCompletedDate(ZonedDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public Integer getNumLoaded() {
        return this.numLoaded;
    }

    public VPFileLoad numLoaded(Integer numLoaded) {
        this.numLoaded = numLoaded;
        return this;
    }

    public void setNumLoaded(Integer numLoaded) {
        this.numLoaded = numLoaded;
    }

    public Integer getNumFailed() {
        return this.numFailed;
    }

    public VPFileLoad numFailed(Integer numFailed) {
        this.numFailed = numFailed;
        return this;
    }

    public void setNumFailed(Integer numFailed) {
        this.numFailed = numFailed;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VPFileLoad)) {
            return false;
        }
        return id != null && id.equals(((VPFileLoad) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VPFileLoad{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", batchId=" + getBatchId() +
            ", createDate='" + getCreateDate() + "'" +
            ", completedDate='" + getCompletedDate() + "'" +
            ", numLoaded=" + getNumLoaded() +
            ", numFailed=" + getNumFailed() +
            "}";
    }
}
