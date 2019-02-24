package cc.orangejuice.srs.programme.module.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A StudentModuleSelection.
 */
@Entity
@Table(name = "student_module_selection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StudentModuleSelection implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @NotNull
    @Column(name = "year_no", nullable = false)
    private Integer yearNo;

    @NotNull
    @Column(name = "semester_no", nullable = false)
    private Integer semesterNo;

    /**
     * null before module finished
     */
    @Column(name = "credit_hour")
    private Double creditHour;

    /**
     * null before module finished
     */
    @Column(name = "marks")
    private Double marks;

    /**
     * null before module finished
     */
    @Column(name = "qcs")
    private Double qcs;

    @ManyToOne
    @JsonIgnoreProperties("studentModuleSelections")
    private Module module;

    @ManyToOne
    @JsonIgnoreProperties("studentModuleSelections")
    private StudentModuleGradeDict studentModuleGradeType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public StudentModuleSelection studentId(Long studentId) {
        this.studentId = studentId;
        return this;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getYearNo() {
        return yearNo;
    }

    public StudentModuleSelection yearNo(Integer yearNo) {
        this.yearNo = yearNo;
        return this;
    }

    public void setYearNo(Integer yearNo) {
        this.yearNo = yearNo;
    }

    public Integer getSemesterNo() {
        return semesterNo;
    }

    public StudentModuleSelection semesterNo(Integer semesterNo) {
        this.semesterNo = semesterNo;
        return this;
    }

    public void setSemesterNo(Integer semesterNo) {
        this.semesterNo = semesterNo;
    }

    public Double getCreditHour() {
        return creditHour;
    }

    public StudentModuleSelection creditHour(Double creditHour) {
        this.creditHour = creditHour;
        return this;
    }

    public void setCreditHour(Double creditHour) {
        this.creditHour = creditHour;
    }

    public Double getMarks() {
        return marks;
    }

    public StudentModuleSelection marks(Double marks) {
        this.marks = marks;
        return this;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }

    public Double getQcs() {
        return qcs;
    }

    public StudentModuleSelection qcs(Double qcs) {
        this.qcs = qcs;
        return this;
    }

    public void setQcs(Double qcs) {
        this.qcs = qcs;
    }

    public Module getModule() {
        return module;
    }

    public StudentModuleSelection module(Module module) {
        this.module = module;
        return this;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public StudentModuleGradeDict getStudentModuleGradeType() {
        return studentModuleGradeType;
    }

    public StudentModuleSelection studentModuleGradeType(StudentModuleGradeDict studentModuleGradeDict) {
        this.studentModuleGradeType = studentModuleGradeDict;
        return this;
    }

    public void setStudentModuleGradeType(StudentModuleGradeDict studentModuleGradeDict) {
        this.studentModuleGradeType = studentModuleGradeDict;
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
        StudentModuleSelection studentModuleSelection = (StudentModuleSelection) o;
        if (studentModuleSelection.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentModuleSelection.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentModuleSelection{" +
            "id=" + getId() +
            ", studentId=" + getStudentId() +
            ", yearNo=" + getYearNo() +
            ", semesterNo=" + getSemesterNo() +
            ", creditHour=" + getCreditHour() +
            ", marks=" + getMarks() +
            ", qcs=" + getQcs() +
            "}";
    }
}
