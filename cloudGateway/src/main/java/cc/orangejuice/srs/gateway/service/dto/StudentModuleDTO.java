package cc.orangejuice.srs.gateway.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the StudentModule entity.
 */
public class StudentModuleDTO implements Serializable {

    private Long id;

    @NotNull
    private Long studentId;

    @NotNull
    private Long moduleId;

    @NotNull
    private Integer enrollYear;

    @NotNull
    private Integer enrollSemester;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getEnrollYear() {
        return enrollYear;
    }

    public void setEnrollYear(Integer enrollYear) {
        this.enrollYear = enrollYear;
    }

    public Integer getEnrollSemester() {
        return enrollSemester;
    }

    public void setEnrollSemester(Integer enrollSemester) {
        this.enrollSemester = enrollSemester;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudentModuleDTO studentModuleDTO = (StudentModuleDTO) o;
        if (studentModuleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentModuleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentModuleDTO{" +
            "id=" + getId() +
            ", studentId=" + getStudentId() +
            ", moduleId=" + getModuleId() +
            ", enrollYear=" + getEnrollYear() +
            ", enrollSemester=" + getEnrollSemester() +
            "}";
    }
}
