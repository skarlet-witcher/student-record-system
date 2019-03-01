package cc.orangejuice.srs.programme.module.repository;

import cc.orangejuice.srs.programme.module.domain.ModuleGrade;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the ModuleGrade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleGradeRepository extends JpaRepository<ModuleGrade, Long> {
    List<ModuleGrade> findAllByIsAffectQca(@Param("isAffectQca") Boolean isAffectQca);
}
