package cc.orangejuice.srs.module.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ModuleGrade.
 */
@Entity
@Table(name = "module_grade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ModuleGrade implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * marks bigger than this will get corresponding qpv
     */
    @Column(name = "low_marks")
    private Integer lowMarks;

    @Column(name = "qpv")
    private Double qpv;

    @Column(name = "is_affect_qca")
    private Boolean isAffectQca;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ModuleGrade name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ModuleGrade description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLowMarks() {
        return lowMarks;
    }

    public ModuleGrade lowMarks(Integer lowMarks) {
        this.lowMarks = lowMarks;
        return this;
    }

    public void setLowMarks(Integer lowMarks) {
        this.lowMarks = lowMarks;
    }

    public Double getQpv() {
        return qpv;
    }

    public ModuleGrade qpv(Double qpv) {
        this.qpv = qpv;
        return this;
    }

    public void setQpv(Double qpv) {
        this.qpv = qpv;
    }

    public Boolean isIsAffectQca() {
        return isAffectQca;
    }

    public ModuleGrade isAffectQca(Boolean isAffectQca) {
        this.isAffectQca = isAffectQca;
        return this;
    }

    public void setIsAffectQca(Boolean isAffectQca) {
        this.isAffectQca = isAffectQca;
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
        ModuleGrade moduleGrade = (ModuleGrade) o;
        if (moduleGrade.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), moduleGrade.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModuleGrade{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", lowMarks=" + getLowMarks() +
            ", qpv=" + getQpv() +
            ", isAffectQca='" + isIsAffectQca() + "'" +
            "}";
    }
}
