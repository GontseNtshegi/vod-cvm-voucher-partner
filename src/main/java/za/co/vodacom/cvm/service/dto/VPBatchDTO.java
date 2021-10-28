package za.co.vodacom.cvm.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link za.co.vodacom.cvm.domain.VPBatch} entity.
 */
public class VPBatchDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime createDate;

    private ZonedDateTime loadDate;

    @NotNull
    @Size(max = 100)
    private String comment;

    @NotNull
    @Size(max = 1)
    private String restrictedYN;

    @NotNull
    private Integer userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(ZonedDateTime loadDate) {
        this.loadDate = loadDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRestrictedYN() {
        return restrictedYN;
    }

    public void setRestrictedYN(String restrictedYN) {
        this.restrictedYN = restrictedYN;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VPBatchDTO)) {
            return false;
        }

        VPBatchDTO vPBatchDTO = (VPBatchDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vPBatchDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VPBatchDTO{" +
            "id=" + getId() +
            ", createDate='" + getCreateDate() + "'" +
            ", loadDate='" + getLoadDate() + "'" +
            ", comment='" + getComment() + "'" +
            ", restrictedYN='" + getRestrictedYN() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
