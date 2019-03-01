package cc.orangejuice.srs.module.repository;

import cc.orangejuice.srs.module.domain.StudentModuleSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StudentModuleSelection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentModuleSelectionRepository extends JpaRepository<StudentModuleSelection, Long> {

}
