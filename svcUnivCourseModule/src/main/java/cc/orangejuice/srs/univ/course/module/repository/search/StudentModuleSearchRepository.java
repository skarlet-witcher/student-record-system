package cc.orangejuice.srs.univ.course.module.repository.search;

import cc.orangejuice.srs.univ.course.module.domain.StudentModule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StudentModule entity.
 */
public interface StudentModuleSearchRepository extends ElasticsearchRepository<StudentModule, Long> {
}
