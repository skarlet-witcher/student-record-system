package cc.orangejuice.srs.programme.module.repository;

import cc.orangejuice.srs.programme.module.domain.StudentModuleSelection;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StudentModuleSelection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentModuleSelectionRepository extends JpaRepository<StudentModuleSelection, Long> {
    @Query(value = "update StudentModuleSelection studentModuleSelection SET studentModuleSelection.marks=:marks WHERE studentModuleSelection.id=:id")
    void updateByIdAndMark(@Param("id") Long id, @Param("marks") Double marks);

    void updateByIdAndStudentModuleGradeTypeAndQcs(@Param("id") Long id, @Param("studentModuleGradeType") String moduleGradeType, @Param("qcs") Double qcs);
}
