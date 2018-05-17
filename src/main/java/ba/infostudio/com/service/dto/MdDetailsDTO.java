package ba.infostudio.com.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MdDetails entity.
 */
public class MdDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    private String description;

    private String mandatory;

    private Long idHeaderId;

    private String idHeaderName;

    private Long idDataTypeId;

    private String idDataTypeName;

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

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public Long getIdHeaderId() {
        return idHeaderId;
    }

    public void setIdHeaderId(Long mdHeadersId) {
        this.idHeaderId = mdHeadersId;
    }

    public String getIdHeaderName() {
        return idHeaderName;
    }

    public void setIdHeaderName(String mdHeadersName) {
        this.idHeaderName = mdHeadersName;
    }

    public Long getIdDataTypeId() {
        return idDataTypeId;
    }

    public void setIdDataTypeId(Long mdDataTypesId) {
        this.idDataTypeId = mdDataTypesId;
    }

    public String getIdDataTypeName() {
        return idDataTypeName;
    }

    public void setIdDataTypeName(String mdDataTypesName) {
        this.idDataTypeName = mdDataTypesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MdDetailsDTO mdDetailsDTO = (MdDetailsDTO) o;
        if(mdDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mdDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MdDetailsDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", mandatory='" + getMandatory() + "'" +
            "}";
    }
}
