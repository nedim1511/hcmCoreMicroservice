package ba.infostudio.com.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the OgWorkPlaces entity.
 */
public class OgWorkPlacesDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    private String description;

    private Long idParentId;

    private String idParentName;

    private Long idWorkPlaceTypesId;

    private String idWorkPlaceTypesName;

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

    public Long getIdParentId() {
        return idParentId;
    }

    public void setIdParentId(Long ogWorkPlacesId) {
        this.idParentId = ogWorkPlacesId;
    }

    public String getIdParentName() {
        return idParentName;
    }

    public void setIdParentName(String ogWorkPlacesName) {
        this.idParentName = ogWorkPlacesName;
    }

    public Long getIdWorkPlaceTypesId() {
        return idWorkPlaceTypesId;
    }

    public void setIdWorkPlaceTypesId(Long ogWorkPlaceTypesId) {
        this.idWorkPlaceTypesId = ogWorkPlaceTypesId;
    }

    public String getIdWorkPlaceTypesName() {
        return idWorkPlaceTypesName;
    }

    public void setIdWorkPlaceTypesName(String ogWorkPlaceTypesName) {
        this.idWorkPlaceTypesName = ogWorkPlaceTypesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OgWorkPlacesDTO ogWorkPlacesDTO = (OgWorkPlacesDTO) o;
        if(ogWorkPlacesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ogWorkPlacesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OgWorkPlacesDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
