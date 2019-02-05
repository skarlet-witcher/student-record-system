package cc.orangejuice.srs.univ.course.module.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A StudentModule.
 */
@Entity
@Table(name = "student_module")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StudentModule implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @NotNull
    @Column(name = "module_id", nullable = false)
    private Long moduleId;

    @NotNull
    @Column(name = "enroll_year", nullable = false)
    private Integer enrollYear;

    @NotNull
    @Column(name = "enroll_semester", nullable = false)
    private Integer enrollSemester;

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

    public StudentModule studentId(Long studentId) {
        this.studentId = studentId;
        return this;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public StudentModule moduleId(Long moduleId) {
        this.moduleId = moduleId;
        return this;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getEnrollYear() {
        return enrollYear;
    }

    public StudentModule enrollYear(Integer enrollYear) {
        this.enrollYear = enrollYear;
        return this;
    }

    public void setEnrollYear(Integer enrollYear) {
        this.enrollYear = enrollYear;
    }

    public Integer getEnrollSemester() {
        return enrollSemester;
    }

    public StudentModule enrollSemester(Integer enrollSemester) {
        this.enrollSemester = enrollSemester;
        return this;
    }

    public void setEnrollSemester(Integer enrollSemester) {
        this.enrollSemester = enrollSemester;
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
        StudentModule studentModule = (StudentModule) o;
        if (studentModule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentModule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentModule{" +
            "id=" + getId() +
            ", studentId=" + getStudentId() +
            ", moduleId=" + getModuleId() +
            ", enrollYear=" + getEnrollYear() +
            ", enrollSemester=" + getEnrollSemester() +
            "}";
    }
}
