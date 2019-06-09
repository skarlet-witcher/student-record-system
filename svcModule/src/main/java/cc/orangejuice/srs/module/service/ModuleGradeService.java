package cc.orangejuice.srs.module.service;

import cc.orangejuice.srs.module.domain.ModuleGrade;
import cc.orangejuice.srs.module.repository.ModuleGradeRepository;
import cc.orangejuice.srs.module.service.dto.ModuleGradeDTO;
import cc.orangejuice.srs.module.service.mapper.ModuleGradeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ModuleGrade.
 */
@Service
@Transactional
public class ModuleGradeService {

    private final Logger log = LoggerFactory.getLogger(ModuleGradeService.class);

    private final ModuleGradeRepository moduleGradeRepository;

    private final ModuleGradeMapper moduleGradeMapper;

    public ModuleGradeService(ModuleGradeRepository moduleGradeRepository, ModuleGradeMapper moduleGradeMapper) {
        this.moduleGradeRepository = moduleGradeRepository;
        this.moduleGradeMapper = moduleGradeMapper;
    }

    /**
     * Save a moduleGrade.
     *
     * @param moduleGradeDTO the entity to save
     * @return the persisted entity
     */
    public ModuleGradeDTO save(ModuleGradeDTO moduleGradeDTO) {
        log.debug("Request to save ModuleGrade : {}", moduleGradeDTO);
        ModuleGrade moduleGrade = moduleGradeMapper.toEntity(moduleGradeDTO);
        moduleGrade = moduleGradeRepository.save(moduleGrade);
        return moduleGradeMapper.toDto(moduleGrade);
    }

    /**
     * Get all the moduleGrades.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ModuleGradeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ModuleGrades");
        return moduleGradeRepository.findAll(pageable)
            .map(moduleGradeMapper::toDto);
    }


    /**
     * Get one moduleGrade by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ModuleGradeDTO> findOne(Long id) {
        log.debug("Request to get ModuleGrade : {}", id);
        return moduleGradeRepository.findById(id)
            .map(moduleGradeMapper::toDto);
    }

    /**
     * Delete the moduleGrade by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ModuleGrade : {}", id);
        moduleGradeRepository.deleteById(id);
    }

    public List<ModuleGrade> getAllModuleGradewithQcaAffected() {
        log.debug("Request to get all module grade detail");
        return moduleGradeRepository.findAllByIsAffectQca(true);
    }

    public ModuleGrade getModuleGradeByName(String name) {
        log.debug("Request to get module grade by Name : {}", name);
        return moduleGradeRepository.findByName(name);
    }
}
