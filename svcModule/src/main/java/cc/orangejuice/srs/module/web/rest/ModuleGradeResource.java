package cc.orangejuice.srs.module.web.rest;
import cc.orangejuice.srs.module.service.ModuleGradeService;
import cc.orangejuice.srs.module.web.rest.errors.BadRequestAlertException;
import cc.orangejuice.srs.module.web.rest.util.HeaderUtil;
import cc.orangejuice.srs.module.web.rest.util.PaginationUtil;
import cc.orangejuice.srs.module.service.dto.ModuleGradeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ModuleGrade.
 */
@RestController
@RequestMapping("/api")
public class ModuleGradeResource {

    private final Logger log = LoggerFactory.getLogger(ModuleGradeResource.class);

    private static final String ENTITY_NAME = "svcModuleModuleGrade";

    private final ModuleGradeService moduleGradeService;

    public ModuleGradeResource(ModuleGradeService moduleGradeService) {
        this.moduleGradeService = moduleGradeService;
    }

    /**
     * POST  /module-grades : Create a new moduleGrade.
     *
     * @param moduleGradeDTO the moduleGradeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new moduleGradeDTO, or with status 400 (Bad Request) if the moduleGrade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/module-grades")
    public ResponseEntity<ModuleGradeDTO> createModuleGrade(@Valid @RequestBody ModuleGradeDTO moduleGradeDTO) throws URISyntaxException {
        log.debug("REST request to save ModuleGrade : {}", moduleGradeDTO);
        if (moduleGradeDTO.getId() != null) {
            throw new BadRequestAlertException("A new moduleGrade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModuleGradeDTO result = moduleGradeService.save(moduleGradeDTO);
        return ResponseEntity.created(new URI("/api/module-grades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /module-grades : Updates an existing moduleGrade.
     *
     * @param moduleGradeDTO the moduleGradeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated moduleGradeDTO,
     * or with status 400 (Bad Request) if the moduleGradeDTO is not valid,
     * or with status 500 (Internal Server Error) if the moduleGradeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/module-grades")
    public ResponseEntity<ModuleGradeDTO> updateModuleGrade(@Valid @RequestBody ModuleGradeDTO moduleGradeDTO) throws URISyntaxException {
        log.debug("REST request to update ModuleGrade : {}", moduleGradeDTO);
        if (moduleGradeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ModuleGradeDTO result = moduleGradeService.save(moduleGradeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, moduleGradeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /module-grades : get all the moduleGrades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of moduleGrades in body
     */
    @GetMapping("/module-grades")
    public ResponseEntity<List<ModuleGradeDTO>> getAllModuleGrades(Pageable pageable) {
        log.debug("REST request to get a page of ModuleGrades");
        Page<ModuleGradeDTO> page = moduleGradeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/module-grades");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /module-grades/:id : get the "id" moduleGrade.
     *
     * @param id the id of the moduleGradeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the moduleGradeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/module-grades/{id}")
    public ResponseEntity<ModuleGradeDTO> getModuleGrade(@PathVariable Long id) {
        log.debug("REST request to get ModuleGrade : {}", id);
        Optional<ModuleGradeDTO> moduleGradeDTO = moduleGradeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moduleGradeDTO);
    }

    /**
     * DELETE  /module-grades/:id : delete the "id" moduleGrade.
     *
     * @param id the id of the moduleGradeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/module-grades/{id}")
    public ResponseEntity<Void> deleteModuleGrade(@PathVariable Long id) {
        log.debug("REST request to delete ModuleGrade : {}", id);
        moduleGradeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/module-grades/get-all-module-grades")
    public List<ModuleGradeDTO> getAllModuleGradeWithQCAAffected() {
        log.debug("REST request to get all module grades");
        List<ModuleGradeDTO> moduleGradeDTOS = moduleGradeService.getAllModuleGradeDTOWithQcaAffected();
        return moduleGradeDTOS;
    }
}
