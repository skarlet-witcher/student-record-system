package cc.orangejuice.srs.student.service.dto;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import cc.orangejuice.srs.student.domain.enumeration.ProgressType;
import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;

/**
 * A DTO for the StudentProgression entity.
 */
public class StudentProgressionDTO implements Serializable {

    private Long id;

    private Integer forAcademicYear;

    private Integer forAcademicSemester;

    private Integer forPartNo;

    /**
     * academicSemester -> qca
     */
    @NotNull
    @ApiModelProperty(value = "academicSemester -> qca", required = true)
    private Double qca;

    /**
     * 1...n:part n  , academicYear(finish of a part) -> cumulativeQcaForPart
     */
    @ApiModelProperty(value = "1...n:part n  , academicYear(finish of a part) -> cumulativeQcaForPart")
    private ProgressType progressType;

    /**
     * academicYear -> progressDecision
     */
    @ApiModelProperty(value = "academicYear -> progressDecision")
    private ProgressDecision progressDecision;


    private Long studentId;

    private String studentStudentNumber;

    // semster qca
    public StudentProgressionDTO(Integer forAcademicYear, Integer forAcademicSemester, @NotNull Double qca, ProgressType progressType, Long studentId) {
        this.forAcademicYear = forAcademicYear;
        this.forAcademicSemester = forAcademicSemester;
        this.qca = qca;
        this.progressType = progressType;
        this.studentId = studentId;
    }

    // cumulative qca
    public StudentProgressionDTO(Integer forPartNo, Integer forAcademicYear, @NotNull Double qca, Long studentId, ProgressType progressType) {
        this.forPartNo = forPartNo;
        this.forAcademicYear = forAcademicYear;
        this.qca = qca;
        this.studentId = studentId;
        this.progressType = progressType;
    }

    public StudentProgressionDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getForAcademicYear() {
        return forAcademicYear;
    }

    public void setForAcademicYear(Integer forAcademicYear) {
        this.forAcademicYear = forAcademicYear;
    }

    public Integer getForAcademicSemester() {
        return forAcademicSemester;
    }

    public void setForAcademicSemester(Integer forAcademicSemester) {
        this.forAcademicSemester = forAcademicSemester;
    }

    public Integer getForPartNo() {
        return forPartNo;
    }

    public void setForPartNo(Integer forPartNo) {
        this.forPartNo = forPartNo;
    }

    public Double getQca() {
        return qca;
    }

    public void setQca(Double qca) {
        this.qca = qca;
    }

    public ProgressType getProgressType() {
        return progressType;
    }

    public void setProgressType(ProgressType progressType) {
        this.progressType = progressType;
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
            ", forAcademicYear=" + getForAcademicYear() +
            ", forAcademicSemester=" + getForAcademicSemester() +
            ", forPartNo=" + getForPartNo() +
            ", qca=" + getQca() +
            ", progressType='" + getProgressType() + "'" +
            ", progressDecision='" + getProgressDecision() + "'" +
            ", student=" + getStudentId() +
            ", student='" + getStudentStudentNumber() + "'" +
            "}";
    }
}
