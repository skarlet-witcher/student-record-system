package cc.orangejuice.srs.programme.module.repository;

import cc.orangejuice.srs.programme.module.domain.StudentModuleGradeDict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the StudentModuleGradeDict entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentModuleGradeDictRepository extends JpaRepository<StudentModuleGradeDict, Long> {

    Optional<StudentModuleGradeDict> findOneByNameIgnoreCase(@Param("name") String gradeName);
}
