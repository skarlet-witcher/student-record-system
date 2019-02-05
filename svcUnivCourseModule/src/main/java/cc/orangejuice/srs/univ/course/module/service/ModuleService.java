package cc.orangejuice.srs.univ.course.module.service;

import cc.orangejuice.srs.univ.course.module.domain.Module;
import cc.orangejuice.srs.univ.course.module.repository.ModuleRepository;
import cc.orangejuice.srs.univ.course.module.repository.search.ModuleSearchRepository;
import cc.orangejuice.srs.univ.course.module.service.dto.ModuleDTO;
import cc.orangejuice.srs.univ.course.module.service.mapper.ModuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Module.
 */
@Service
@Transactional
public class ModuleService {

    private final Logger log = LoggerFactory.getLogger(ModuleService.class);

    private final ModuleRepository moduleRepository;

    private final ModuleMapper moduleMapper;

    private final ModuleSearchRepository moduleSearchRepository;

    public ModuleService(ModuleRepository moduleRepository, ModuleMapper moduleMapper, ModuleSearchRepository moduleSearchRepository) {
        this.moduleRepository = moduleRepository;
        this.moduleMapper = moduleMapper;
        this.moduleSearchRepository = moduleSearchRepository;
    }

    /**
     * Save a module.
     *
     * @param moduleDTO the entity to save
     * @return the persisted entity
     */
    public ModuleDTO save(ModuleDTO moduleDTO) {
        log.debug("Request to save Module : {}", moduleDTO);
        Module module = moduleMapper.toEntity(moduleDTO);
        module = moduleRepository.save(module);
        ModuleDTO result = moduleMapper.toDto(module);
        moduleSearchRepository.save(module);
        return result;
    }

    /**
     * Get all the modules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ModuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Modules");
        return moduleRepository.findAll(pageable)
            .map(moduleMapper::toDto);
    }


    /**
     * Get one module by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ModuleDTO> findOne(Long id) {
        log.debug("Request to get Module : {}", id);
        return moduleRepository.findById(id)
            .map(moduleMapper::toDto);
    }

    /**
     * Delete the module by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Module : {}", id);        moduleRepository.deleteById(id);
        moduleSearchRepository.deleteById(id);
    }

    /**
     * Search for the module corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ModuleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Modules for query {}", query);
        return moduleSearchRepository.search(queryStringQuery(query), pageable)
            .map(moduleMapper::toDto);
    }
}
