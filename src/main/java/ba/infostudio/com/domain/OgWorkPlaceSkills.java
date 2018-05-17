package ba.infostudio.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A OgWorkPlaceSkills.
 */
@Entity
@Table(name = "og_work_place_skills")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OgWorkPlaceSkills extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "date_skill")
    private LocalDate dateSkill;

    @OneToOne
    @JoinColumn(name = "id_work_place")
    private OgWorkPlaces idWorkPlace;

    @OneToOne
    @JoinColumn(name = "id_skill")
    private RgSkills idSkill;

    @OneToOne
    @JoinColumn(name = "id_grade")
    private RgSkillGrades idGrade;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public OgWorkPlaceSkills description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateSkill() {
        return dateSkill;
    }

    public OgWorkPlaceSkills dateSkill(LocalDate dateSkill) {
        this.dateSkill = dateSkill;
        return this;
    }

    public void setDateSkill(LocalDate dateSkill) {
        this.dateSkill = dateSkill;
    }

    public OgWorkPlaces getIdWorkPlace() {
        return idWorkPlace;
    }

    public OgWorkPlaceSkills idWorkPlace(OgWorkPlaces ogWorkPlaces) {
        this.idWorkPlace = ogWorkPlaces;
        return this;
    }

    public void setIdWorkPlace(OgWorkPlaces ogWorkPlaces) {
        this.idWorkPlace = ogWorkPlaces;
    }

    public RgSkills getIdSkill() {
        return idSkill;
    }

    public OgWorkPlaceSkills idSkill(RgSkills rgSkills) {
        this.idSkill = rgSkills;
        return this;
    }

    public void setIdSkill(RgSkills rgSkills) {
        this.idSkill = rgSkills;
    }

    public RgSkillGrades getIdGrade() {
        return idGrade;
    }

    public OgWorkPlaceSkills idGrade(RgSkillGrades rgSkillGrades) {
        this.idGrade = rgSkillGrades;
        return this;
    }

    public void setIdGrade(RgSkillGrades rgSkillGrades) {
        this.idGrade = rgSkillGrades;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OgWorkPlaceSkills ogWorkPlaceSkills = (OgWorkPlaceSkills) o;
        if (ogWorkPlaceSkills.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ogWorkPlaceSkills.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OgWorkPlaceSkills{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", dateSkill='" + getDateSkill() + "'" +
            "}";
    }
}
