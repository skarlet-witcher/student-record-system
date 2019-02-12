package cc.orangejuice.srs.univ.course.module.repository;

import cc.orangejuice.srs.univ.course.module.domain.ModuleResult;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ModuleResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleResultRepository extends JpaRepository<ModuleResult, Long> {
    @Query(value = "Select moduleResult From ModuleResult moduleResult where moduleResult.studentId=:studentId")
    List<ModuleResult> findAllModulesResultsByStudentId(@Param("studentId") Long studentId);
}
