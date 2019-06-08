package cc.orangejuice.srs.student.service.dto.factory;

import cc.orangejuice.srs.student.domain.enumeration.ProgressType;
import cc.orangejuice.srs.student.service.dto.StudentEnrollDTO;
import cc.orangejuice.srs.student.service.dto.StudentProgressionDTO;

public abstract class DTOFactory {

    public static StudentProgressionDTO createStudentProgressionDTOForSemesterQCA(Integer forAcademicYear, Integer forAcademicSemester, Double qca, ProgressType progressType, Long studentId){
        return new StudentProgressionDTO(forAcademicYear, forAcademicSemester, qca, progressType, studentId);

    }
    public static StudentProgressionDTO createStudentProgressionDTOForCumulativeQCA(Integer forPartNo, Integer forAcademicYear, Double qca, Long studentId, ProgressType progressType) {
        return new StudentProgressionDTO(forPartNo, forAcademicYear, qca, studentId, progressType);
    }

    public static StudentEnrollDTO createStudentEnrollDTO() {
        return new StudentEnrollDTO();
    }
}
