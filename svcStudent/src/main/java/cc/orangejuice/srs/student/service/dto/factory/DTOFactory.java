package cc.orangejuice.srs.student.service.dto.factory;

import cc.orangejuice.srs.student.domain.enumeration.ProgressType;
import cc.orangejuice.srs.student.service.dto.StudentProgressionDTO;

public abstract class DTOFactory {

    public abstract StudentProgressionDTO createStudentProgressionDTOForSemesterQCA(Integer forAcademicYear, Integer forAcademicSemester, Double qca, ProgressType progressType, Long studentId);

    public abstract StudentProgressionDTO createStudentProgressionDTOForCumulativeQCA(Integer forPartNo, Integer forAcademicYear, Double qca, Long studentId, ProgressType progressType);



}
