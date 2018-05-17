package ba.infostudio.com.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the LeLegalEntities entity.
 */
public class LeLegalEntitiesDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    private String name;

    private String idNumber;

    private String dutyNumber;

    @NotNull
    private String address;

    @NotNull
    private String postalNumber;

    private Long idEntityTypeId;

    private String idEntityTypeName;

    private Long regionId;

    private String regionName;

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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getDutyNumber() {
        return dutyNumber;
    }

    public void setDutyNumber(String dutyNumber) {
        this.dutyNumber = dutyNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalNumber() {
        return postalNumber;
    }

    public void setPostalNumber(String postalNumber) {
        this.postalNumber = postalNumber;
    }

    public Long getIdEntityTypeId() {
        return idEntityTypeId;
    }

    public void setIdEntityTypeId(Long leLegalEntityTypesId) {
        this.idEntityTypeId = leLegalEntityTypesId;
    }

    public String getIdEntityTypeName() {
        return idEntityTypeName;
    }

    public void setIdEntityTypeName(String leLegalEntityTypesName) {
        this.idEntityTypeName = leLegalEntityTypesName;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long rgRegionsId) {
        this.regionId = rgRegionsId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String rgRegionsName) {
        this.regionName = rgRegionsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LeLegalEntitiesDTO leLegalEntitiesDTO = (LeLegalEntitiesDTO) o;
        if(leLegalEntitiesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leLegalEntitiesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LeLegalEntitiesDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", idNumber='" + getIdNumber() + "'" +
            ", dutyNumber='" + getDutyNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", postalNumber='" + getPostalNumber() + "'" +
            "}";
    }
}
