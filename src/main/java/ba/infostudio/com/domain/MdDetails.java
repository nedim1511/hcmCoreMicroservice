package ba.infostudio.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MdDetails.
 */
@Entity
@Table(name = "md_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MdDetails extends AbstractAuditingEntity implements Serializable {

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

    @Column(name = "mandatory")
    private String mandatory;

    @OneToOne
    @JoinColumn(name = "id_header")
    private MdHeaders idHeader;

    @OneToOne
    @JoinColumn(name = "id_data_type")
    private MdDataTypes idDataType;

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

    public MdDetails code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public MdDetails name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public MdDetails description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMandatory() {
        return mandatory;
    }

    public MdDetails mandatory(String mandatory) {
        this.mandatory = mandatory;
        return this;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public MdHeaders getIdHeader() {
        return idHeader;
    }

    public MdDetails idHeader(MdHeaders mdHeaders) {
        this.idHeader = mdHeaders;
        return this;
    }

    public void setIdHeader(MdHeaders mdHeaders) {
        this.idHeader = mdHeaders;
    }

    public MdDataTypes getIdDataType() {
        return idDataType;
    }

    public MdDetails idDataType(MdDataTypes mdDataTypes) {
        this.idDataType = mdDataTypes;
        return this;
    }

    public void setIdDataType(MdDataTypes mdDataTypes) {
        this.idDataType = mdDataTypes;
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
        MdDetails mdDetails = (MdDetails) o;
        if (mdDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mdDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MdDetails{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", mandatory='" + getMandatory() + "'" +
            "}";
    }
}
