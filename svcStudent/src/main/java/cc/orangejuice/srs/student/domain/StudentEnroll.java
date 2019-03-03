package cc.orangejuice.srs.student.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import cc.orangejuice.srs.student.domain.enumeration.Degree;

import cc.orangejuice.srs.student.domain.enumeration.EnrollStatus;

/**
 * A StudentEnroll.
 */
@Entity
@Table(name = "student_enroll")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StudentEnroll implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "enroll_year", nullable = false)
    private Integer enrollYear;

    @NotNull
    @Column(name = "for_programme_id", nullable = false)
    private Long forProgrammeId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "for_degree", nullable = false)
    private Degree forDegree;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EnrollStatus status;

    @ManyToOne
    @JsonIgnoreProperties("studentEnrolls")
    private Student student;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEnrollYear() {
        return enrollYear;
    }

    public StudentEnroll enrollYear(Integer enrollYear) {
        this.enrollYear = enrollYear;
        return this;
    }

    public void setEnrollYear(Integer enrollYear) {
        this.enrollYear = enrollYear;
    }

    public Long getForProgrammeId() {
        return forProgrammeId;
    }

    public StudentEnroll forProgrammeId(Long forProgrammeId) {
        this.forProgrammeId = forProgrammeId;
        return this;
    }

    public void setForProgrammeId(Long forProgrammeId) {
        this.forProgrammeId = forProgrammeId;
    }

    public Degree getForDegree() {
        return forDegree;
    }

    public StudentEnroll forDegree(Degree forDegree) {
        this.forDegree = forDegree;
        return this;
    }

    public void setForDegree(Degree forDegree) {
        this.forDegree = forDegree;
    }

    public EnrollStatus getStatus() {
        return status;
    }

    public StudentEnroll status(EnrollStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(EnrollStatus status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public StudentEnroll student(Student student) {
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
        StudentEnroll studentEnroll = (StudentEnroll) o;
        if (studentEnroll.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentEnroll.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentEnroll{" +
            "id=" + getId() +
            ", enrollYear=" + getEnrollYear() +
            ", forProgrammeId=" + getForProgrammeId() +
            ", forDegree='" + getForDegree() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
