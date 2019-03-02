package cc.orangejuice.srs.module.repository;

import cc.orangejuice.srs.module.domain.ModuleGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the ModuleGrade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleGradeRepository extends JpaRepository<ModuleGrade, Long> {

    Optional<ModuleGrade> findOneByNameIgnoreCase(@Param("name") String gradeName);
}
