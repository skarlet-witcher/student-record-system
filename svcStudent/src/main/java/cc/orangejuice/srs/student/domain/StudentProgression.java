package cc.orangejuice.srs.student.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;

/**
 * A StudentProgression.
 */
@Entity
@Table(name = "student_progression")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StudentProgression implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "year_no", nullable = false)
    private Integer yearNo;

    @NotNull
    @Column(name = "semester_no", nullable = false)
    private Integer semesterNo;

    /**
     * semesterNo -> qca
     */
    @NotNull
    @Column(name = "qca", nullable = false)
    private Double qca;

    /**
     * 0:not   1...n:part n  | yearNo(finish of a part) -> cumulativeQcaForPart
     */
    @NotNull
    @Column(name = "cumulative_qca_for_part", nullable = false)
    private Integer cumulativeQcaForPart;

    /**
     * yearNo -> progressDecision
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "progress_decision")
    private ProgressDecision progressDecision;

    @ManyToOne
    @JsonIgnoreProperties("students")
    private Student student;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYearNo() {
        return yearNo;
    }

    public StudentProgression yearNo(Integer yearNo) {
        this.yearNo = yearNo;
        return this;
    }

    public void setYearNo(Integer yearNo) {
        this.yearNo = yearNo;
    }

    public Integer getSemesterNo() {
        return semesterNo;
    }

    public StudentProgression semesterNo(Integer semesterNo) {
        this.semesterNo = semesterNo;
        return this;
    }

    public void setSemesterNo(Integer semesterNo) {
        this.semesterNo = semesterNo;
    }

    public Double getQca() {
        return qca;
    }

    public StudentProgression qca(Double qca) {
        this.qca = qca;
        return this;
    }

    public void setQca(Double qca) {
        this.qca = qca;
    }

    public Integer getCumulativeQcaForPart() {
        return cumulativeQcaForPart;
    }

    public StudentProgression cumulativeQcaForPart(Integer cumulativeQcaForPart) {
        this.cumulativeQcaForPart = cumulativeQcaForPart;
        return this;
    }

    public void setCumulativeQcaForPart(Integer cumulativeQcaForPart) {
        this.cumulativeQcaForPart = cumulativeQcaForPart;
    }

    public ProgressDecision getProgressDecision() {
        return progressDecision;
    }

    public StudentProgression progressDecision(ProgressDecision progressDecision) {
        this.progressDecision = progressDecision;
        return this;
    }

    public void setProgressDecision(ProgressDecision progressDecision) {
        this.progressDecision = progressDecision;
    }

    public Student getStudent() {
        return student;
    }

    public StudentProgression student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
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
        StudentProgression studentProgression = (StudentProgression) o;
        if (studentProgression.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentProgression.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentProgression{" +
            "id=" + getId() +
            ", yearNo=" + getYearNo() +
            ", semesterNo=" + getSemesterNo() +
            ", qca=" + getQca() +
            ", cumulativeQcaForPart=" + getCumulativeQcaForPart() +
            ", progressDecision='" + getProgressDecision() + "'" +
            "}";
    }
}
