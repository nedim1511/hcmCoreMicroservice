package ba.infostudio.com.service.dto;


import java.time.Instant;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the OgWorkPlaceSkills entity.
 */
public class OgWorkPlaceSkillsDTO implements Serializable {

    private Long id;

    private String description;

    private LocalDate dateSkill;

    private Long idWorkPlaceId;

    private String idWorkPlaceName;

    private Long idSkillId;

    private String idSkillName;

    private Long idGradeId;

    private String idGradeName;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateSkill() {
        return dateSkill;
    }

    public void setDateSkill(LocalDate dateSkill) {
        this.dateSkill = dateSkill;
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

    public Long getIdSkillId() {
        return idSkillId;
    }

    public void setIdSkillId(Long rgSkillsId) {
        this.idSkillId = rgSkillsId;
    }

    public String getIdSkillName() {
        return idSkillName;
    }

    public void setIdSkillName(String rgSkillsName) {
        this.idSkillName = rgSkillsName;
    }

    public Long getIdGradeId() {
        return idGradeId;
    }

    public void setIdGradeId(Long rgSkillGradesId) {
        this.idGradeId = rgSkillGradesId;
    }

    public String getIdGradeName() {
        return idGradeName;
    }

    public void setIdGradeName(String rgSkillGradesName) {
        this.idGradeName = rgSkillGradesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OgWorkPlaceSkillsDTO ogWorkPlaceSkillsDTO = (OgWorkPlaceSkillsDTO) o;
        if(ogWorkPlaceSkillsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ogWorkPlaceSkillsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OgWorkPlaceSkillsDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", dateSkill='" + getDateSkill() + "'" +
            "}";
    }
}
