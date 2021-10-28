package za.co.vodacom.cvm.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link za.co.vodacom.cvm.domain.VPUsers} entity.
 */
public class VPUsersDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        if (!(o instanceof VPUsersDTO)) {
            return false;
        }

        VPUsersDTO vPUsersDTO = (VPUsersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vPUsersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VPUsersDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", activeYN='" + getActiveYN() + "'" +
            "}";
    }
}
