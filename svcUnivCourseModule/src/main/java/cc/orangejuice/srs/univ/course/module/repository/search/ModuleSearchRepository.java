package cc.orangejuice.srs.univ.course.module.repository.search;

import cc.orangejuice.srs.univ.course.module.domain.Module;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Module entity.
 */
public interface ModuleSearchRepository extends ElasticsearchRepository<Module, Long> {
}
