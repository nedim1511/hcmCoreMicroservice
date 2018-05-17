package ba.infostudio.com.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the RgRegions entity.
 */
public class RgRegionsDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    private String description;

    private Long idTypeId;

    private String idTypeName;

    private Long idParentId;

    private String idParentName;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIdTypeId() {
        return idTypeId;
    }

    public void setIdTypeId(Long rgRegionTypesId) {
        this.idTypeId = rgRegionTypesId;
    }

    public String getIdTypeName() {
        return idTypeName;
    }

    public void setIdTypeName(String rgRegionTypesName) {
        this.idTypeName = rgRegionTypesName;
    }

    public Long getIdParentId() {
        return idParentId;
    }

    public void setIdParentId(Long rgRegionsId) {
        this.idParentId = rgRegionsId;
    }

    public String getIdParentName() {
        return idParentName;
    }

    public void setIdParentName(String rgRegionsName) {
        this.idParentName = rgRegionsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RgRegionsDTO rgRegionsDTO = (RgRegionsDTO) o;
        if(rgRegionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rgRegionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RgRegionsDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
