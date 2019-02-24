package cc.orangejuice.srs.programme.module.service.dto;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the StudentModuleGradeDict entity.
 */
public class StudentModuleGradeDictDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    /**
     * marks bigger than this will get corresponding qpv
     */
    @ApiModelProperty(value = "marks bigger than this will get corresponding qpv")
    private Integer lowMarks;

    private Double qpv;

    private Boolean isAffectQca;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLowMarks() {
        return lowMarks;
    }

    public void setLowMarks(Integer lowMarks) {
        this.lowMarks = lowMarks;
    }

    public Double getQpv() {
        return qpv;
    }

    public void setQpv(Double qpv) {
        this.qpv = qpv;
    }

    public Boolean isIsAffectQca() {
        return isAffectQca;
    }

    public void setIsAffectQca(Boolean isAffectQca) {
        this.isAffectQca = isAffectQca;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudentModuleGradeDictDTO studentModuleGradeDictDTO = (StudentModuleGradeDictDTO) o;
        if (studentModuleGradeDictDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentModuleGradeDictDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentModuleGradeDictDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", lowMarks=" + getLowMarks() +
            ", qpv=" + getQpv() +
            ", isAffectQca='" + isIsAffectQca() + "'" +
            "}";
    }
}
