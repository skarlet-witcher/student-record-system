package cc.orangejuice.srs.programme.web.rest;
import cc.orangejuice.srs.programme.service.FacultyService;
import cc.orangejuice.srs.programme.web.rest.errors.BadRequestAlertException;
import cc.orangejuice.srs.programme.web.rest.util.HeaderUtil;
import cc.orangejuice.srs.programme.web.rest.util.PaginationUtil;
import cc.orangejuice.srs.programme.service.dto.FacultyDTO;
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
 * REST controller for managing Faculty.
 */
@RestController
@RequestMapping("/api")
public class FacultyResource {

    private final Logger log = LoggerFactory.getLogger(FacultyResource.class);

    private static final String ENTITY_NAME = "svcProgrammeFaculty";

    private final FacultyService facultyService;

    public FacultyResource(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    /**
     * POST  /faculties : Create a new faculty.
     *
     * @param facultyDTO the facultyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facultyDTO, or with status 400 (Bad Request) if the faculty has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/faculties")
    public ResponseEntity<FacultyDTO> createFaculty(@Valid @RequestBody FacultyDTO facultyDTO) throws URISyntaxException {
        log.debug("REST request to save Faculty : {}", facultyDTO);
        if (facultyDTO.getId() != null) {
            throw new BadRequestAlertException("A new faculty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FacultyDTO result = facultyService.save(facultyDTO);
        return ResponseEntity.created(new URI("/api/faculties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /faculties : Updates an existing faculty.
     *
     * @param facultyDTO the facultyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facultyDTO,
     * or with status 400 (Bad Request) if the facultyDTO is not valid,
     * or with status 500 (Internal Server Error) if the facultyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/faculties")
    public ResponseEntity<FacultyDTO> updateFaculty(@Valid @RequestBody FacultyDTO facultyDTO) throws URISyntaxException {
        log.debug("REST request to update Faculty : {}", facultyDTO);
        if (facultyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FacultyDTO result = facultyService.save(facultyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, facultyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /faculties : get all the faculties.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of faculties in body
     */
    @GetMapping("/faculties")
    public ResponseEntity<List<FacultyDTO>> getAllFaculties(Pageable pageable) {
        log.debug("REST request to get a page of Faculties");
        Page<FacultyDTO> page = facultyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/faculties");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /faculties/:id : get the "id" faculty.
     *
     * @param id the id of the facultyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facultyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/faculties/{id}")
    public ResponseEntity<FacultyDTO> getFaculty(@PathVariable Long id) {
        log.debug("REST request to get Faculty : {}", id);
        Optional<FacultyDTO> facultyDTO = facultyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(facultyDTO);
    }

    /**
     * DELETE  /faculties/:id : delete the "id" faculty.
     *
     * @param id the id of the facultyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/faculties/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        log.debug("REST request to delete Faculty : {}", id);
        facultyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
