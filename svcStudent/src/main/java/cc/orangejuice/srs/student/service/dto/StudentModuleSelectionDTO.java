package cc.orangejuice.srs.student.service.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the StudentModuleSelection entity.
 */
public class StudentModuleSelectionDTO implements Serializable {

    private Long id;

    @NotNull
    private Long studentId;

    @NotNull
    private Integer academicYear;

    @NotNull
    private Integer academicSemester;

    @NotNull
    private Integer yearNo;

    @NotNull
    private Integer semesterNo;

    /**
     * null before module finished
     */
    @ApiModelProperty(value = "null before module finished")
    private Double creditHour;

    /**
     * null before module finished
     */
    @ApiModelProperty(value = "null before module finished")
    private Double marks;

    /**
     * null before module finished
     */
    @ApiModelProperty(value = "null before module finished")
    private Double qcs;


    private Long moduleId;

    private String moduleName;

    private Long studentModuleGradeTypeId;

    private String studentModuleGradeTypeName;

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

    public Integer getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(Integer academicYear) {
        this.academicYear = academicYear;
    }

    public Integer getAcademicSemester() {
        return academicSemester;
    }

    public void setAcademicSemester(Integer academicSemester) {
        this.academicSemester = academicSemester;
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

    public Double getCreditHour() {
        return creditHour;
    }

    public void setCreditHour(Double creditHour) {
        this.creditHour = creditHour;
    }

    public Double getMarks() {
        return marks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }

    public Double getQcs() {
        return qcs;
    }

    public void setQcs(Double qcs) {
        this.qcs = qcs;
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

    public Long getStudentModuleGradeTypeId() {
        return studentModuleGradeTypeId;
    }

    public void setStudentModuleGradeTypeId(Long moduleGradeId) {
        this.studentModuleGradeTypeId = moduleGradeId;
    }

    public String getStudentModuleGradeTypeName() {
        return studentModuleGradeTypeName;
    }

    public void setStudentModuleGradeTypeName(String moduleGradeName) {
        this.studentModuleGradeTypeName = moduleGradeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudentModuleSelectionDTO studentModuleSelectionDTO = (StudentModuleSelectionDTO) o;
        if (studentModuleSelectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentModuleSelectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentModuleSelectionDTO{" +
            "id=" + getId() +
            ", studentId=" + getStudentId() +
            ", academicYear=" + getAcademicYear() +
            ", academicSemester=" + getAcademicSemester() +
            ", yearNo=" + getYearNo() +
            ", semesterNo=" + getSemesterNo() +
            ", creditHour=" + getCreditHour() +
            ", marks=" + getMarks() +
            ", qcs=" + getQcs() +
            ", module=" + getModuleId() +
            ", module='" + getModuleName() + "'" +
            ", studentModuleGradeType=" + getStudentModuleGradeTypeId() +
            ", studentModuleGradeType='" + getStudentModuleGradeTypeName() + "'" +
            "}";
    }
}
