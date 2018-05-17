package ba.infostudio.com.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the OgOrg entity.
 */
public class OgOrgDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    private String description;

    private Long idOrganizationTypeIdId;

    private String idOrganizationTypeIdName;

    private Long idParentIdId;

    private String idParentIdName;

    private Long idLegalEntityIdId;

    private String idLegalEntityIdName;

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

    public Long getIdOrganizationTypeIdId() {
        return idOrganizationTypeIdId;
    }

    public void setIdOrganizationTypeIdId(Long ogOrgTypesId) {
        this.idOrganizationTypeIdId = ogOrgTypesId;
    }

    public String getIdOrganizationTypeIdName() {
        return idOrganizationTypeIdName;
    }

    public void setIdOrganizationTypeIdName(String ogOrgTypesName) {
        this.idOrganizationTypeIdName = ogOrgTypesName;
    }

    public Long getIdParentIdId() {
        return idParentIdId;
    }

    public void setIdParentIdId(Long ogOrgId) {
        this.idParentIdId = ogOrgId;
    }

    public String getIdParentIdName() {
        return idParentIdName;
    }

    public void setIdParentIdName(String ogOrgName) {
        this.idParentIdName = ogOrgName;
    }

    public Long getIdLegalEntityIdId() {
        return idLegalEntityIdId;
    }

    public void setIdLegalEntityIdId(Long leLegalEntitiesId) {
        this.idLegalEntityIdId = leLegalEntitiesId;
    }

    public String getIdLegalEntityIdName() {
        return idLegalEntityIdName;
    }

    public void setIdLegalEntityIdName(String leLegalEntitiesName) {
        this.idLegalEntityIdName = leLegalEntitiesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OgOrgDTO ogOrgDTO = (OgOrgDTO) o;
        if(ogOrgDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ogOrgDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OgOrgDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
