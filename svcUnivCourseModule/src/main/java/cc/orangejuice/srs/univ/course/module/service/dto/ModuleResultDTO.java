package cc.orangejuice.srs.univ.course.module.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ModuleResult entity.
 */
public class ModuleResultDTO implements Serializable {

    private Long id;

    private Double grade;

    private Double qca;

    private Long stundetId;


    private Long moduleId;

    private String moduleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Double getQca() {
        return qca;
    }

    public void setQca(Double qca) {
        this.qca = qca;
    }

    public Long getStundetId() {
        return stundetId;
    }

    public void setStundetId(Long stundetId) {
        this.stundetId = stundetId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModuleResultDTO moduleResultDTO = (ModuleResultDTO) o;
        if (moduleResultDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), moduleResultDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModuleResultDTO{" +
            "id=" + getId() +
            ", grade=" + getGrade() +
            ", qca=" + getQca() +
            ", stundetId=" + getStundetId() +
            ", module=" + getModuleId() +
            ", module='" + getModuleName() + "'" +
            "}";
    }
}
