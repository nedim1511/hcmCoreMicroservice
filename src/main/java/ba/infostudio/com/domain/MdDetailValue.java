package ba.infostudio.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MdDetailValue.
 */
@Entity
@Table(name = "md_detail_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MdDetailValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @NotNull
    @Column(name = "display_values", nullable = false)
    private String displayValues;

    @Column(name = "ordering")
    private Integer ordering;

    @OneToOne
    @JoinColumn(name = "id_detail")
    private MdDetailValue idDetail;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public MdDetailValue value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplayValues() {
        return displayValues;
    }

    public MdDetailValue displayValues(String displayValues) {
        this.displayValues = displayValues;
        return this;
    }

    public void setDisplayValues(String displayValues) {
        this.displayValues = displayValues;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public MdDetailValue ordering(Integer ordering) {
        this.ordering = ordering;
        return this;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public MdDetailValue getIdDetail() {
        return idDetail;
    }

    public MdDetailValue idDetail(MdDetailValue mdDetailValue) {
        this.idDetail = mdDetailValue;
        return this;
    }

    public void setIdDetail(MdDetailValue mdDetailValue) {
        this.idDetail = mdDetailValue;
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
        MdDetailValue mdDetailValue = (MdDetailValue) o;
        if (mdDetailValue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mdDetailValue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MdDetailValue{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", displayValues='" + getDisplayValues() + "'" +
            ", ordering=" + getOrdering() +
            "}";
    }
}
