package ba.infostudio.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A OgOrganizations.
 */
@Entity
@Table(name = "og_organizations")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OgOrganizations extends AbstractAuditingEntity implements Serializable {

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
    @JoinColumn(name = "id_legal_entity")
    private LeLegalEntities idLegalEntity;

    @OneToOne
    @JoinColumn(name = "id_parent")
    private OgOrganizations idParent;

    @OneToOne
    @JoinColumn(name = "id_organization_type")
    private OgOrgTypes idOrganizationType;

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

    public OgOrganizations code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public OgOrganizations name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public OgOrganizations description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LeLegalEntities getIdLegalEntity() {
        return idLegalEntity;
    }

    public OgOrganizations idLegalEntity(LeLegalEntities leLegalEntities) {
        this.idLegalEntity = leLegalEntities;
        return this;
    }

    public void setIdLegalEntity(LeLegalEntities leLegalEntities) {
        this.idLegalEntity = leLegalEntities;
    }

    public OgOrganizations getIdParent() {
        return idParent;
    }

    public OgOrganizations idParent(OgOrganizations ogOrganizations) {
        this.idParent = ogOrganizations;
        return this;
    }

    public void setIdParent(OgOrganizations ogOrganizations) {
        this.idParent = ogOrganizations;
    }

    public OgOrgTypes getIdOrganizationType() {
        return idOrganizationType;
    }

    public OgOrganizations idOrganizationType(OgOrgTypes ogOrgTypes) {
        this.idOrganizationType = ogOrgTypes;
        return this;
    }

    public void setIdOrganizationType(OgOrgTypes ogOrgTypes) {
        this.idOrganizationType = ogOrgTypes;
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
        OgOrganizations ogOrganizations = (OgOrganizations) o;
        if (ogOrganizations.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ogOrganizations.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OgOrganizations{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
