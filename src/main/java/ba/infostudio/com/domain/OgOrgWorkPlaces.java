package ba.infostudio.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A OgOrgWorkPlaces.
 */
@Entity
@Table(name = "og_org_work_places")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OgOrgWorkPlaces extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_organization")
    private OgOrganizations idOrganization;

    @OneToOne
    @JoinColumn(name = "id_work_place")
    private OgWorkPlaces idWorkPlace;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OgOrganizations getIdOrganization() {
        return idOrganization;
    }

    public OgOrgWorkPlaces idOrganization(OgOrganizations ogOrganizations) {
        this.idOrganization = ogOrganizations;
        return this;
    }

    public void setIdOrganization(OgOrganizations ogOrganizations) {
        this.idOrganization = ogOrganizations;
    }

    public OgWorkPlaces getIdWorkPlace() {
        return idWorkPlace;
    }

    public OgOrgWorkPlaces idWorkPlace(OgWorkPlaces ogWorkPlaces) {
        this.idWorkPlace = ogWorkPlaces;
        return this;
    }

    public void setIdWorkPlace(OgWorkPlaces ogWorkPlaces) {
        this.idWorkPlace = ogWorkPlaces;
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
        OgOrgWorkPlaces ogOrgWorkPlaces = (OgOrgWorkPlaces) o;
        if (ogOrgWorkPlaces.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ogOrgWorkPlaces.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OgOrgWorkPlaces{" +
            "id=" + getId() +
            "}";
    }
}
