package cc.orangejuice.srs.module.repository;

import cc.orangejuice.srs.module.domain.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Module entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    Optional<Module> findOneByCode(@Param("moduleCode") String moduleCode);
}
