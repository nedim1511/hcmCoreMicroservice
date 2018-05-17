package ba.infostudio.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RgRegions.
 */
@Entity
@Table(name = "rg_regions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RgRegions extends AbstractAuditingEntity implements Serializable {

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

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "id_type")
    private RgRegionTypes idType;

    @OneToOne
    @JoinColumn(name = "id_parent")
    private RgRegions idParent;

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

    public RgRegions code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public RgRegions name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public RgRegions description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RgRegionTypes getIdType() {
        return idType;
    }

    public RgRegions idType(RgRegionTypes rgRegionTypes) {
        this.idType = rgRegionTypes;
        return this;
    }

    public void setIdType(RgRegionTypes rgRegionTypes) {
        this.idType = rgRegionTypes;
    }

    public RgRegions getIdParent() {
        return idParent;
    }

    public RgRegions idParent(RgRegions rgRegions) {
        this.idParent = rgRegions;
        return this;
    }

    public void setIdParent(RgRegions rgRegions) {
        this.idParent = rgRegions;
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
        RgRegions rgRegions = (RgRegions) o;
        if (rgRegions.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rgRegions.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RgRegions{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
