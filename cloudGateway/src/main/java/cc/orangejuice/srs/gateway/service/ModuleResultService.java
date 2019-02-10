package cc.orangejuice.srs.gateway.service;

import cc.orangejuice.srs.gateway.domain.ModuleResult;
import cc.orangejuice.srs.gateway.repository.ModuleResultRepository;
import cc.orangejuice.srs.gateway.service.dto.ModuleResultDTO;
import cc.orangejuice.srs.gateway.service.mapper.ModuleResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ModuleResult.
 */
@Service
@Transactional
public class ModuleResultService {

    private final Logger log = LoggerFactory.getLogger(ModuleResultService.class);

    private final ModuleResultRepository moduleResultRepository;

    private final ModuleResultMapper moduleResultMapper;

    public ModuleResultService(ModuleResultRepository moduleResultRepository, ModuleResultMapper moduleResultMapper) {
        this.moduleResultRepository = moduleResultRepository;
        this.moduleResultMapper = moduleResultMapper;
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
        return moduleResultMapper.toDto(moduleResult);
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
    }
}
