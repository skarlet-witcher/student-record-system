package cc.orangejuice.srs.univ.course.module.repository;

import cc.orangejuice.srs.univ.course.module.domain.StudentModule;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the StudentModule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentModuleRepository extends JpaRepository<StudentModule, Long> {

    @Query(value = "Select studentModule From StudentModule studentModule where studentModule.studentId =:studentId and studentModule.enrollSemester=:enrollSemester and studentModule.enrollYear=:enrollYear")
    List<StudentModule> findAllWithStudentRegisteredModules(@Param("studentId") Long studentId, @Param("enrollYear") Integer enrollYear, @Param("enrollSemester") Integer enrollSemester);

    @Query(value = "Select studentModule From StudentModule studentModule where studentModule.moduleId =:moduleId")
    List<StudentModule> findAllStudentsByModuleId(@Param("moduleId") Long moduleId);
}
