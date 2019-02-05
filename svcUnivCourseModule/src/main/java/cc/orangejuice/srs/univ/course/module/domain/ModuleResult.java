package cc.orangejuice.srs.univ.course.module.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ModuleResult.
 */
@Entity
@Table(name = "module_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "moduleresult")
public class ModuleResult implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grade")
    private Double grade;

    @Column(name = "qca")
    private Double qca;

    @Column(name = "stundet_id")
    private Long stundetId;

    @ManyToOne
    @JsonIgnoreProperties("moduleResults")
    private Module module;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGrade() {
        return grade;
    }

    public ModuleResult grade(Double grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Double getQca() {
        return qca;
    }

    public ModuleResult qca(Double qca) {
        this.qca = qca;
        return this;
    }

    public void setQca(Double qca) {
        this.qca = qca;
    }

    public Long getStundetId() {
        return stundetId;
    }

    public ModuleResult stundetId(Long stundetId) {
        this.stundetId = stundetId;
        return this;
    }

    public void setStundetId(Long stundetId) {
        this.stundetId = stundetId;
    }

    public Module getModule() {
        return module;
    }

    public ModuleResult module(Module module) {
        this.module = module;
        return this;
    }

    public void setModule(Module module) {
        this.module = module;
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
        ModuleResult moduleResult = (ModuleResult) o;
        if (moduleResult.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), moduleResult.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModuleResult{" +
            "id=" + getId() +
            ", grade=" + getGrade() +
            ", qca=" + getQca() +
            ", stundetId=" + getStundetId() +
            "}";
    }
}
