package cc.orangejuice.srs.student.service.dto;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;

/**
 * A DTO for the StudentProgression entity.
 */
public class StudentProgressionDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer yearNo;

    @NotNull
    private Integer semesterNo;

    /**
     * semesterNo -> qca
     */
    @NotNull
    @ApiModelProperty(value = "semesterNo -> qca", required = true)
    private Double qca;

    /**
     * 0:not   1...n:part n  | yearNo(finish of a part) -> cumulativeQcaForPart
     */
    @NotNull
    @ApiModelProperty(value = "0:not   1...n:part n  | yearNo(finish of a part) -> cumulativeQcaForPart", required = true)
    private Integer cumulativeQcaForPart;

    /**
     * yearNo -> progressDecision
     */
    @ApiModelProperty(value = "yearNo -> progressDecision")
    private ProgressDecision progressDecision;


    private Long studentId;

    private String studentStudentNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYearNo() {
        return yearNo;
    }

    public void setYearNo(Integer yearNo) {
        this.yearNo = yearNo;
    }

    public Integer getSemesterNo() {
        return semesterNo;
    }

    public void setSemesterNo(Integer semesterNo) {
        this.semesterNo = semesterNo;
    }

    public Double getQca() {
        return qca;
    }

    public void setQca(Double qca) {
        this.qca = qca;
    }

    public Integer getCumulativeQcaForPart() {
        return cumulativeQcaForPart;
    }

    public void setCumulativeQcaForPart(Integer cumulativeQcaForPart) {
        this.cumulativeQcaForPart = cumulativeQcaForPart;
    }

    public ProgressDecision getProgressDecision() {
        return progressDecision;
    }

    public void setProgressDecision(ProgressDecision progressDecision) {
        this.progressDecision = progressDecision;
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

        StudentProgressionDTO studentProgressionDTO = (StudentProgressionDTO) o;
        if (studentProgressionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentProgressionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentProgressionDTO{" +
            "id=" + getId() +
            ", yearNo=" + getYearNo() +
            ", semesterNo=" + getSemesterNo() +
            ", qca=" + getQca() +
            ", cumulativeQcaForPart=" + getCumulativeQcaForPart() +
            ", progressDecision='" + getProgressDecision() + "'" +
            ", student=" + getStudentId() +
            ", student='" + getStudentStudentNumber() + "'" +
            "}";
    }
}
