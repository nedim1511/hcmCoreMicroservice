package ba.infostudio.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A OgWorkPlaces.
 */
@Entity
@Table(name = "og_work_places")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OgWorkPlaces extends AbstractAuditingEntity implements Serializable {

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
    @JoinColumn(name = "id_parent")
    private OgWorkPlaces idParent;

    @OneToOne
    @JoinColumn(name = "id_work_place_types")
    private OgWorkPlaceTypes idWorkPlaceTypes;

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

    public OgWorkPlaces code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public OgWorkPlaces name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public OgWorkPlaces description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OgWorkPlaces getIdParent() {
        return idParent;
    }

    public OgWorkPlaces idParent(OgWorkPlaces ogWorkPlaces) {
        this.idParent = ogWorkPlaces;
        return this;
    }

    public void setIdParent(OgWorkPlaces ogWorkPlaces) {
        this.idParent = ogWorkPlaces;
    }

    public OgWorkPlaceTypes getIdWorkPlaceTypes() {
        return idWorkPlaceTypes;
    }

    public OgWorkPlaces idWorkPlaceTypes(OgWorkPlaceTypes ogWorkPlaceTypes) {
        this.idWorkPlaceTypes = ogWorkPlaceTypes;
        return this;
    }

    public void setIdWorkPlaceTypes(OgWorkPlaceTypes ogWorkPlaceTypes) {
        this.idWorkPlaceTypes = ogWorkPlaceTypes;
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
        OgWorkPlaces ogWorkPlaces = (OgWorkPlaces) o;
        if (ogWorkPlaces.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ogWorkPlaces.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OgWorkPlaces{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
