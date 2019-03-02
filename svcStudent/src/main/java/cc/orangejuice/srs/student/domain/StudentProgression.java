package cc.orangejuice.srs.student.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import cc.orangejuice.srs.student.domain.enumeration.ProgressType;

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

    @Column(name = "for_academic_year")
    private Integer forAcademicYear;

    @Column(name = "for_academic_semester")
    private Integer forAcademicSemester;

    @Column(name = "for_part_no")
    private Integer forPartNo;

    /**
     * academicSemester -> qca
     */
    @NotNull
    @Column(name = "qca", nullable = false)
    private Double qca;

    /**
     * 1...n:part n  , academicYear(finish of a part) -> cumulativeQcaForPart
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "progress_type")
    private ProgressType progressType;

    /**
     * academicYear -> progressDecision
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

    public Integer getForAcademicYear() {
        return forAcademicYear;
    }

    public StudentProgression forAcademicYear(Integer forAcademicYear) {
        this.forAcademicYear = forAcademicYear;
        return this;
    }

    public void setForAcademicYear(Integer forAcademicYear) {
        this.forAcademicYear = forAcademicYear;
    }

    public Integer getForAcademicSemester() {
        return forAcademicSemester;
    }

    public StudentProgression forAcademicSemester(Integer forAcademicSemester) {
        this.forAcademicSemester = forAcademicSemester;
        return this;
    }

    public void setForAcademicSemester(Integer forAcademicSemester) {
        this.forAcademicSemester = forAcademicSemester;
    }

    public Integer getForPartNo() {
        return forPartNo;
    }

    public StudentProgression forPartNo(Integer forPartNo) {
        this.forPartNo = forPartNo;
        return this;
    }

    public void setForPartNo(Integer forPartNo) {
        this.forPartNo = forPartNo;
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

    public ProgressType getProgressType() {
        return progressType;
    }

    public StudentProgression progressType(ProgressType progressType) {
        this.progressType = progressType;
        return this;
    }

    public void setProgressType(ProgressType progressType) {
        this.progressType = progressType;
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
            ", forAcademicYear=" + getForAcademicYear() +
            ", forAcademicSemester=" + getForAcademicSemester() +
            ", forPartNo=" + getForPartNo() +
            ", qca=" + getQca() +
            ", progressType='" + getProgressType() + "'" +
            ", progressDecision='" + getProgressDecision() + "'" +
            "}";
    }
}
