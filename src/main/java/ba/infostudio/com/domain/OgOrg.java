package ba.infostudio.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A OgOrg.
 */
@Entity
@Table(name = "og_org")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OgOrg extends AbstractAuditingEntity implements Serializable {

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
    @JoinColumn(name = "id_organization_type_id")
    private OgOrgTypes idOrganizationTypeId;

    @OneToOne
    @JoinColumn(name = "id_parent_id")
    private OgOrg idParentId;

    @OneToOne
    @JoinColumn(name = "id_legal_entity_id")
    private LeLegalEntities idLegalEntityId;

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

    public OgOrg code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public OgOrg name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public OgOrg description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OgOrgTypes getIdOrganizationTypeId() {
        return idOrganizationTypeId;
    }

    public OgOrg idOrganizationTypeId(OgOrgTypes ogOrgTypes) {
        this.idOrganizationTypeId = ogOrgTypes;
        return this;
    }

    public void setIdOrganizationTypeId(OgOrgTypes ogOrgTypes) {
        this.idOrganizationTypeId = ogOrgTypes;
    }

    public OgOrg getIdParentId() {
        return idParentId;
    }

    public OgOrg idParentId(OgOrg ogOrg) {
        this.idParentId = ogOrg;
        return this;
    }

    public void setIdParentId(OgOrg ogOrg) {
        this.idParentId = ogOrg;
    }

    public LeLegalEntities getIdLegalEntityId() {
        return idLegalEntityId;
    }

    public OgOrg idLegalEntityId(LeLegalEntities leLegalEntities) {
        this.idLegalEntityId = leLegalEntities;
        return this;
    }

    public void setIdLegalEntityId(LeLegalEntities leLegalEntities) {
        this.idLegalEntityId = leLegalEntities;
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
        OgOrg ogOrg = (OgOrg) o;
        if (ogOrg.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ogOrg.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OgOrg{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
