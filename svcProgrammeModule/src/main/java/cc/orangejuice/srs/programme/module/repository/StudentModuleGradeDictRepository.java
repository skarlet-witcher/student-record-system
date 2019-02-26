package cc.orangejuice.srs.programme.module.repository;

import cc.orangejuice.srs.programme.module.domain.StudentModuleGradeDict;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the StudentModuleGradeDict entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentModuleGradeDictRepository extends JpaRepository<StudentModuleGradeDict, Long> {
    @Query(value = "SELECT studentModuleGradeDict FROM StudentModuleGradeDict studentModuleGradeDict WHERE studentModuleGradeDict.name=:gradeName")
    Optional<StudentModuleGradeDict> findOneGradeTypeByName(@Param("gradeName") String gradeName);
}
