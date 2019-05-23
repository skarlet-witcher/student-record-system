package cc.orangejuice.srs.student.service.dto.factory;

import cc.orangejuice.srs.student.domain.enumeration.ProgressType;
import cc.orangejuice.srs.student.service.dto.StudentProgressionDTO;

public class DTOFactory {

    public StudentProgressionDTO createStudentProgressionDTOForSemesterQCA(Integer forAcademicYear, Integer forAcademicSemester, Double qca, ProgressType progressType, Long studentId) {
        return new StudentProgressionDTO(forAcademicYear, forAcademicSemester, qca, progressType, studentId);
    }

    public StudentProgressionDTO createStudentProgressionDTOForCumulativeQCA(Integer forPartNo, Integer forAcademicYear, Double qca, Long studentId, ProgressType progressType) {
        return new StudentProgressionDTO(forPartNo, forAcademicYear, qca, studentId, progressType);
    }


}
