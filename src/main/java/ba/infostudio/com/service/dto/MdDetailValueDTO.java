package ba.infostudio.com.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MdDetailValue entity.
 */
public class MdDetailValueDTO implements Serializable {

    private Long id;

    @NotNull
    private String value;

    @NotNull
    private String displayValues;

    private Integer ordering;

    private Long idDetailId;

    private String createdBy;

    private Instant createdAt;

    private String updatedBy;

    private Instant updatedAt;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplayValues() {
        return displayValues;
    }

    public void setDisplayValues(String displayValues) {
        this.displayValues = displayValues;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public Long getIdDetailId() {
        return idDetailId;
    }

    public void setIdDetailId(Long mdDetailValueId) {
        this.idDetailId = mdDetailValueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MdDetailValueDTO mdDetailValueDTO = (MdDetailValueDTO) o;
        if(mdDetailValueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mdDetailValueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MdDetailValueDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", displayValues='" + getDisplayValues() + "'" +
            ", ordering=" + getOrdering() +
            "}";
    }
}
