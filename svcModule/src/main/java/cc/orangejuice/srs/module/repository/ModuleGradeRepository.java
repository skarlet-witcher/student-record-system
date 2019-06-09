package cc.orangejuice.srs.module.repository;

import cc.orangejuice.srs.module.domain.ModuleGrade;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ModuleGrade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleGradeRepository extends JpaRepository<ModuleGrade, Long> {

    List<ModuleGrade> findAllByIsAffectQca(@Param("is_affect_qca") Boolean isAffectQca);

    ModuleGrade findByName(@Param("name") String name);

}
