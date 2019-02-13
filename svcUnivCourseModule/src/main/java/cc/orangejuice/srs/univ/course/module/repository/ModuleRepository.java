package cc.orangejuice.srs.univ.course.module.repository;

import cc.orangejuice.srs.univ.course.module.domain.Module;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Module entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    @Query(value = "Select module From Module module Where module.code=:moduleCode")
    Module findOneModuleByModuleCod(@Param("moduleCode") String moduleCode);
}
