package cc.orangejuice.srs.univ.course.module.service;

import cc.orangejuice.srs.univ.course.module.domain.ModuleResult;
import cc.orangejuice.srs.univ.course.module.repository.ModuleResultRepository;
import cc.orangejuice.srs.univ.course.module.repository.search.ModuleResultSearchRepository;
import cc.orangejuice.srs.univ.course.module.service.dto.ModuleResultDTO;
import cc.orangejuice.srs.univ.course.module.service.mapper.ModuleResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ModuleResult.
 */
@Service
@Transactional
public class ModuleResultService {

    private final Logger log = LoggerFactory.getLogger(ModuleResultService.class);

    private final ModuleResultRepository moduleResultRepository;

    private final ModuleResultMapper moduleResultMapper;

    private final ModuleResultSearchRepository moduleResultSearchRepository;

    public ModuleResultService(ModuleResultRepository moduleResultRepository, ModuleResultMapper moduleResultMapper, ModuleResultSearchRepository moduleResultSearchRepository) {
        this.moduleResultRepository = moduleResultRepository;
        this.moduleResultMapper = moduleResultMapper;
        this.moduleResultSearchRepository = moduleResultSearchRepository;
    }

    /**
     * Save a moduleResult.
     *
     * @param moduleResultDTO the entity to save
     * @return the persisted entity
     */
    public ModuleResultDTO save(ModuleResultDTO moduleResultDTO) {
        log.debug("Request to save ModuleResult : {}", moduleResultDTO);
        ModuleResult moduleResult = moduleResultMapper.toEntity(moduleResultDTO);
        moduleResult = moduleResultRepository.save(moduleResult);
        ModuleResultDTO result = moduleResultMapper.toDto(moduleResult);
        moduleResultSearchRepository.save(moduleResult);
        return result;
    }

    /**
     * Get all the moduleResults.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ModuleResultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ModuleResults");
        return moduleResultRepository.findAll(pageable)
            .map(moduleResultMapper::toDto);
    }


    /**
     * Get one moduleResult by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ModuleResultDTO> findOne(Long id) {
        log.debug("Request to get ModuleResult : {}", id);
        return moduleResultRepository.findById(id)
            .map(moduleResultMapper::toDto);
    }

    /**
     * Delete the moduleResult by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ModuleResult : {}", id);        moduleResultRepository.deleteById(id);
        moduleResultSearchRepository.deleteById(id);
    }

    /**
     * Search for the moduleResult corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ModuleResultDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ModuleResults for query {}", query);
        return moduleResultSearchRepository.search(queryStringQuery(query), pageable)
            .map(moduleResultMapper::toDto);
    }
}
