package cc.orangejuice.srs.programme.module.repository;

import cc.orangejuice.srs.programme.module.domain.Module;
import cc.orangejuice.srs.programme.module.service.dto.ModuleDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Module entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    @Query(value = "Select module From Module module Where module.code=:moduleCode")
    Optional<Module> findOneModule(@Param("moduleCode") String moduleCode);
}
