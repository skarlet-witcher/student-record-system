package cc.orangejuice.srs.student.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import cc.orangejuice.srs.student.domain.enumeration.Degree;
import cc.orangejuice.srs.student.domain.enumeration.EnrollStatus;

/**
 * A DTO for the StudentEnroll entity.
 */
public class StudentEnrollDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer enrollYear;

    @NotNull
    private Long forProgrammeId;

    @NotNull
    private Degree forDegree;

    @NotNull
    private EnrollStatus status;


    private Long studentId;

    private String studentStudentNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEnrollYear() {
        return enrollYear;
    }

    public void setEnrollYear(Integer enrollYear) {
        this.enrollYear = enrollYear;
    }

    public Long getForProgrammeId() {
        return forProgrammeId;
    }

    public void setForProgrammeId(Long forProgrammeId) {
        this.forProgrammeId = forProgrammeId;
    }

    public Degree getForDegree() {
        return forDegree;
    }

    public void setForDegree(Degree forDegree) {
        this.forDegree = forDegree;
    }

    public EnrollStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollStatus status) {
        this.status = status;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentStudentNumber() {
        return studentStudentNumber;
    }

    public void setStudentStudentNumber(String studentStudentNumber) {
        this.studentStudentNumber = studentStudentNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudentEnrollDTO studentEnrollDTO = (StudentEnrollDTO) o;
        if (studentEnrollDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentEnrollDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentEnrollDTO{" +
            "id=" + getId() +
            ", enrollYear=" + getEnrollYear() +
            ", forProgrammeId=" + getForProgrammeId() +
            ", forDegree='" + getForDegree() + "'" +
            ", status='" + getStatus() + "'" +
            ", student=" + getStudentId() +
            ", student='" + getStudentStudentNumber() + "'" +
            "}";
    }
}
