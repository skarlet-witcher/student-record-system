package cc.orangejuice.srs.programme.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Programme entity.
 */
public class ProgrammeDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    @NotNull
    private Integer length;

    @NotNull
    private String courseLeader;

    @NotNull
    private String degree;


    private Long departmentId;

    private String departmentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getCourseLeader() {
        return courseLeader;
    }

    public void setCourseLeader(String courseLeader) {
        this.courseLeader = courseLeader;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProgrammeDTO programmeDTO = (ProgrammeDTO) o;
        if (programmeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), programmeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProgrammeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", length=" + getLength() +
            ", courseLeader='" + getCourseLeader() + "'" +
            ", degree='" + getDegree() + "'" +
            ", department=" + getDepartmentId() +
            ", department='" + getDepartmentName() + "'" +
            "}";
    }
}
