package za.co.vodacom.cvm.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link za.co.vodacom.cvm.domain.VPFileLoad} entity.
 */
public class VPFileLoadDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String fileName;

    @NotNull
    private Integer batchId;

    @NotNull
    private ZonedDateTime createDate;

    private ZonedDateTime completedDate;

    private Integer numLoaded;

    private Integer numFailed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(ZonedDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public Integer getNumLoaded() {
        return numLoaded;
    }

    public void setNumLoaded(Integer numLoaded) {
        this.numLoaded = numLoaded;
    }

    public Integer getNumFailed() {
        return numFailed;
    }

    public void setNumFailed(Integer numFailed) {
        this.numFailed = numFailed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VPFileLoadDTO)) {
            return false;
        }

        VPFileLoadDTO vPFileLoadDTO = (VPFileLoadDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vPFileLoadDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VPFileLoadDTO{" +
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
