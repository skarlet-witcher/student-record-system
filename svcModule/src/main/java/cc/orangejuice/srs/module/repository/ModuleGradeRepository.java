package cc.orangejuice.srs.module.repository;

import cc.orangejuice.srs.module.domain.ModuleGrade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ModuleGrade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleGradeRepository extends JpaRepository<ModuleGrade, Long> {

}
