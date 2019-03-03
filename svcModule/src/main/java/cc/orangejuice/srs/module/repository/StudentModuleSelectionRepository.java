package cc.orangejuice.srs.module.repository;


import cc.orangejuice.srs.module.domain.Module;
import cc.orangejuice.srs.module.domain.ModuleGrade;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import cc.orangejuice.srs.module.domain.StudentModuleSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the StudentModuleSelection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentModuleSelectionRepository extends JpaRepository<StudentModuleSelection, Long> {
    @Modifying
    @Query(value = "update StudentModuleSelection studentModuleSelection SET studentModuleSelection.marks=:marks WHERE studentModuleSelection.id=:id")
    void updateMarksById(@Param("id") Long id, @Param("marks") Double marks);

    @Modifying
    @Query(value = "update StudentModuleSelection studentModuleSelection SET studentModuleSelection.studentModuleGradeType=:studentModuleGradeType, studentModuleSelection.qcs=:qcs, studentModuleSelection.creditHour=:creditHour WHERE studentModuleSelection.id=:id")
    void updateById(@Param("id") Long id, @Param("studentModuleGradeType") ModuleGrade studentModuleGradeType, @Param("qcs") Double qcs, @Param("creditHour") Double creditHour);

    Optional<StudentModuleSelection> findAllByAcademicYearAndYearNoAndSemesterNo(@Param("academic_year") Integer academic_year, @Param("yearNo") Integer yearNo, @Param("semesterNo") Integer semesterNo);

    Optional<StudentModuleSelection> findAllByStudentIdAndAcademicYearAndYearNoAndSemesterNo(@Param("studentId") Long studentId, @Param("academicYear") Integer academicYear, @Param("yearNo") Integer yearNo, @Param("semesterNo") Integer semesterNo);

    List<StudentModuleSelection> findAllByStudentIdAndAcademicYearAndYearNo(@Param("studentId") Long studentId, @Param("academicYear") Integer academicYear, @Param("yearNo") Integer yearNo);

    List<StudentModuleSelection> findAllByStudentIdAndSemesterNo(@Param("studentId") Long studentId, @Param("semesterNo") Integer semesterNo);

    @Modifying
    @Query(value = "SELECT studentModuleSelection  FROM StudentModuleSelection studentModuleSelection WHERE studentModuleSelection.academicSemester<=:academicSemester AND studentModuleSelection.studentId=:studentId ")
    List<StudentModuleSelection> findAllByStudentIdAndAcademicSemester(@Param("studentId") Long studentId, @Param("academicSemester") Integer academicSemester);

}
