package cc.orangejuice.srs.univ.course.module.web.rest;
import cc.orangejuice.srs.univ.course.module.service.ModuleResultService;
import cc.orangejuice.srs.univ.course.module.web.rest.errors.BadRequestAlertException;
import cc.orangejuice.srs.univ.course.module.web.rest.util.HeaderUtil;
import cc.orangejuice.srs.univ.course.module.web.rest.util.PaginationUtil;
import cc.orangejuice.srs.univ.course.module.service.dto.ModuleResultDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ModuleResult.
 */
@RestController
@RequestMapping("/api")
public class ModuleResultResource {

    private final Logger log = LoggerFactory.getLogger(ModuleResultResource.class);

    private static final String ENTITY_NAME = "svcUnivCourseModuleModuleResult";

    private final ModuleResultService moduleResultService;

    public ModuleResultResource(ModuleResultService moduleResultService) {
        this.moduleResultService = moduleResultService;
    }

    /**
     * POST  /module-results : Create a new moduleResult.
     *
     * @param moduleResultDTO the moduleResultDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new moduleResultDTO, or with status 400 (Bad Request) if the moduleResult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/module-results")
    public ResponseEntity<ModuleResultDTO> createModuleResult(@RequestBody ModuleResultDTO moduleResultDTO) throws URISyntaxException {
        log.debug("REST request to save ModuleResult : {}", moduleResultDTO);
        if (moduleResultDTO.getId() != null) {
            throw new BadRequestAlertException("A new moduleResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModuleResultDTO result = moduleResultService.save(moduleResultDTO);
        return ResponseEntity.created(new URI("/api/module-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /module-results : Updates an existing moduleResult.
     *
     * @param moduleResultDTO the moduleResultDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated moduleResultDTO,
     * or with status 400 (Bad Request) if the moduleResultDTO is not valid,
     * or with status 500 (Internal Server Error) if the moduleResultDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/module-results")
    public ResponseEntity<ModuleResultDTO> updateModuleResult(@RequestBody ModuleResultDTO moduleResultDTO) throws URISyntaxException {
        log.debug("REST request to update ModuleResult : {}", moduleResultDTO);
        if (moduleResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ModuleResultDTO result = moduleResultService.save(moduleResultDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, moduleResultDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /module-results : get all the moduleResults.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of moduleResults in body
     */
    @GetMapping("/module-results")
    public ResponseEntity<List<ModuleResultDTO>> getAllModuleResults(Pageable pageable) {
        log.debug("REST request to get a page of ModuleResults");
        Page<ModuleResultDTO> page = moduleResultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/module-results");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /module-results/:id : get the "id" moduleResult.
     *
     * @param id the id of the moduleResultDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the moduleResultDTO, or with status 404 (Not Found)
     */
    @GetMapping("/module-results/{id}")
    public ResponseEntity<ModuleResultDTO> getModuleResult(@PathVariable Long id) {
        log.debug("REST request to get ModuleResult : {}", id);
        Optional<ModuleResultDTO> moduleResultDTO = moduleResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moduleResultDTO);
    }

    /**
     * DELETE  /module-results/:id : delete the "id" moduleResult.
     *
     * @param id the id of the moduleResultDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/module-results/{id}")
    public ResponseEntity<Void> deleteModuleResult(@PathVariable Long id) {
        log.debug("REST request to delete ModuleResult : {}", id);
        moduleResultService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
