package ba.infostudio.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RgSkillGrades.
 */
@Entity
@Table(name = "rg_skill_grades")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RgSkillGrades extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Column(name = "description")
    private String description;

    @Column(name = "numerical")
    private String numerical;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public RgSkillGrades code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public RgSkillGrades name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrade() {
        return grade;
    }

    public RgSkillGrades grade(Integer grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getDescription() {
        return description;
    }

    public RgSkillGrades description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumerical() {
        return numerical;
    }

    public RgSkillGrades numerical(String numerical) {
        this.numerical = numerical;
        return this;
    }

    public void setNumerical(String numerical) {
        this.numerical = numerical;
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
        RgSkillGrades rgSkillGrades = (RgSkillGrades) o;
        if (rgSkillGrades.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rgSkillGrades.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RgSkillGrades{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", grade=" + getGrade() +
            ", description='" + getDescription() + "'" +
            ", numerical='" + getNumerical() + "'" +
            "}";
    }
}
