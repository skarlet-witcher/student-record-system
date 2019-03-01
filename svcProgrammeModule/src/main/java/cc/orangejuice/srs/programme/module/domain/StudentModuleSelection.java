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
    @Column(name = "academic_year", nullable = false)
    private Integer academicYear;

    @NotNull
    @Column(name = "academic_semester", nullable = false)
    private Integer academicSemester;

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
    private ModuleGrade studentModuleGradeType;

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

    public Integer getAcademicYear() {
        return academicYear;
    }

    public StudentModuleSelection academicYear(Integer academicYear) {
        this.academicYear = academicYear;
        return this;
    }

    public void setAcademicYear(Integer academicYear) {
        this.academicYear = academicYear;
    }

    public Integer getAcademicSemester() {
        return academicSemester;
    }

    public StudentModuleSelection academicSemester(Integer academicSemester) {
        this.academicSemester = academicSemester;
        return this;
    }

    public void setAcademicSemester(Integer academicSemester) {
        this.academicSemester = academicSemester;
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

    public ModuleGrade getStudentModuleGradeType() {
        return studentModuleGradeType;
    }

    public StudentModuleSelection studentModuleGradeType(ModuleGrade moduleGrade) {
        this.studentModuleGradeType = moduleGrade;
        return this;
    }

    public void setStudentModuleGradeType(ModuleGrade moduleGrade) {
        this.studentModuleGradeType = moduleGrade;
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
            ", academicYear=" + getAcademicYear() +
            ", academicSemester=" + getAcademicSemester() +
            ", creditHour=" + getCreditHour() +
            ", marks=" + getMarks() +
            ", qcs=" + getQcs() +
            "}";
    }
}
