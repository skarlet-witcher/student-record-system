package cc.orangejuice.srs.programme.module.repository;

import cc.orangejuice.srs.programme.module.domain.StudentModuleGradeDict;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StudentModuleGradeDict entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentModuleGradeDictRepository extends JpaRepository<StudentModuleGradeDict, Long> {

}
