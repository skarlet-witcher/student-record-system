package cc.orangejuice.srs.univ.course.module.service.dto;
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
    private Integer year;

    @NotNull
    private Integer semester;


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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
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
            ", year=" + getYear() +
            ", semester=" + getSemester() +
            "}";
    }
}
