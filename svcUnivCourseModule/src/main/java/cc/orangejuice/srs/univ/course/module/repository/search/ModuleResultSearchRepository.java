package cc.orangejuice.srs.univ.course.module.repository.search;

import cc.orangejuice.srs.univ.course.module.domain.ModuleResult;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ModuleResult entity.
 */
public interface ModuleResultSearchRepository extends ElasticsearchRepository<ModuleResult, Long> {
}
