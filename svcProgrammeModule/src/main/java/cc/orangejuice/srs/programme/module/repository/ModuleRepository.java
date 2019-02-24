package cc.orangejuice.srs.programme.module.repository;

import cc.orangejuice.srs.programme.module.domain.Module;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Module entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

}
