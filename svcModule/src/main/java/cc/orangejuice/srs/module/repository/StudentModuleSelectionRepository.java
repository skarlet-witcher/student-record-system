package cc.orangejuice.srs.module.repository;


import cc.orangejuice.srs.module.domain.Module;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import cc.orangejuice.srs.module.domain.StudentModuleSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the StudentModuleSelection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentModuleSelectionRepository extends JpaRepository<StudentModuleSelection, Long> {
    @Query(value = "update StudentModuleSelection studentModuleSelection SET studentModuleSelection.marks=:marks WHERE studentModuleSelection.id=:id")
    void updateMarksById(@Param("id") Long id, @Param("marks") Double marks);

    @Query(value = "update StudentModuleSelection studentModuleSelection SET studentModuleSelection.studentModuleGradeType=:studentModuleGradeType, studentModuleSelection.qcs=:qcs, studentModuleSelection.creditHour=:creditHour WHERE studentModuleSelection.id=:id")
    void updateByIdAndStudentModuleGradeTypeAndQcsAndCreditHour(@Param("id") Long id, @Param("studentModuleGradeType") String studentModuleGradeType, @Param("qcs") Double qcs, @Param("creditHour") Double creditHour);

    Optional<StudentModuleSelection> findOneByAcademicYearAndAcademicSemesterAndYearNoAndSemesterNo(@Param("academic_year") Integer academic_year, @Param("academic_semester") Integer academic_semester, @Param("yearNo") Integer yearNo, @Param("semesterNo") Integer semesterNo);

    List<StudentModuleSelection> findAllByStudentIdAndAcademicYearAndYearNoAndSemesterNo(@Param("studentId") Long studentId, @Param("academicYear") Integer academicYear, @Param("yearNo") Integer yearNo, @Param("semesterNo") Integer semesterNo);

    List<StudentModuleSelection> findAllByStudentIdAndAcademicYearAndYearNo(@Param("studentId") Long studentId, @Param("academicYear") Integer academicYear, @Param("yearNo") Integer yearNo);

}
