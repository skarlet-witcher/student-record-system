package cc.orangejuice.srs.univ.course.module.repository;

import cc.orangejuice.srs.univ.course.module.domain.ModuleResult;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ModuleResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleResultRepository extends JpaRepository<ModuleResult, Long> {

}
