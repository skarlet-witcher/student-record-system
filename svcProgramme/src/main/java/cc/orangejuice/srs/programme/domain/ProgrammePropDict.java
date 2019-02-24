package cc.orangejuice.srs.programme.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import cc.orangejuice.srs.programme.domain.enumeration.ProgrammePropType;

/**
 * A ProgrammePropDict.
 */
@Entity
@Table(name = "programme_prop_dict")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProgrammePropDict implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "for_enroll_year", nullable = false)
    private Integer forEnrollYear;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private ProgrammePropType type;

    @Column(name = "for_year_no")
    private Integer forYearNo;

    @Column(name = "for_semester_no")
    private Integer forSemesterNo;

    /**
     * semester->factor, semester->belong_to_part, general->how_many_parts
     */
    @NotNull
    @Column(name = "jhi_key", nullable = false)
    private String key;

    @NotNull
    @Column(name = "jhi_value", nullable = false)
    private String value;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getForEnrollYear() {
        return forEnrollYear;
    }

    public ProgrammePropDict forEnrollYear(Integer forEnrollYear) {
        this.forEnrollYear = forEnrollYear;
        return this;
    }

    public void setForEnrollYear(Integer forEnrollYear) {
        this.forEnrollYear = forEnrollYear;
    }

    public ProgrammePropType getType() {
        return type;
    }

    public ProgrammePropDict type(ProgrammePropType type) {
        this.type = type;
        return this;
    }

    public void setType(ProgrammePropType type) {
        this.type = type;
    }

    public Integer getForYearNo() {
        return forYearNo;
    }

    public ProgrammePropDict forYearNo(Integer forYearNo) {
        this.forYearNo = forYearNo;
        return this;
    }

    public void setForYearNo(Integer forYearNo) {
        this.forYearNo = forYearNo;
    }

    public Integer getForSemesterNo() {
        return forSemesterNo;
    }

    public ProgrammePropDict forSemesterNo(Integer forSemesterNo) {
        this.forSemesterNo = forSemesterNo;
        return this;
    }

    public void setForSemesterNo(Integer forSemesterNo) {
        this.forSemesterNo = forSemesterNo;
    }

    public String getKey() {
        return key;
    }

    public ProgrammePropDict key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public ProgrammePropDict value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
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
        ProgrammePropDict programmePropDict = (ProgrammePropDict) o;
        if (programmePropDict.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), programmePropDict.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProgrammePropDict{" +
            "id=" + getId() +
            ", forEnrollYear=" + getForEnrollYear() +
            ", type='" + getType() + "'" +
            ", forYearNo=" + getForYearNo() +
            ", forSemesterNo=" + getForSemesterNo() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
