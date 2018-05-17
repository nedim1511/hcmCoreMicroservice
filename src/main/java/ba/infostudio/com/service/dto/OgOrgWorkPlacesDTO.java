package ba.infostudio.com.service.dto;


import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the OgOrgWorkPlaces entity.
 */
public class OgOrgWorkPlacesDTO implements Serializable {

    private Long id;

    private Long idOrganizationId;

    private String idOrganizationName;

    private Long idWorkPlaceId;

    private String idWorkPlaceName;

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

    public Long getIdOrganizationId() {
        return idOrganizationId;
    }

    public void setIdOrganizationId(Long ogOrganizationsId) {
        this.idOrganizationId = ogOrganizationsId;
    }

    public String getIdOrganizationName() {
        return idOrganizationName;
    }

    public void setIdOrganizationName(String ogOrganizationsName) {
        this.idOrganizationName = ogOrganizationsName;
    }

    public Long getIdWorkPlaceId() {
        return idWorkPlaceId;
    }

    public void setIdWorkPlaceId(Long ogWorkPlacesId) {
        this.idWorkPlaceId = ogWorkPlacesId;
    }

    public String getIdWorkPlaceName() {
        return idWorkPlaceName;
    }

    public void setIdWorkPlaceName(String ogWorkPlacesName) {
        this.idWorkPlaceName = ogWorkPlacesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OgOrgWorkPlacesDTO ogOrgWorkPlacesDTO = (OgOrgWorkPlacesDTO) o;
        if(ogOrgWorkPlacesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ogOrgWorkPlacesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OgOrgWorkPlacesDTO{" +
            "id=" + getId() +
            "}";
    }
}
