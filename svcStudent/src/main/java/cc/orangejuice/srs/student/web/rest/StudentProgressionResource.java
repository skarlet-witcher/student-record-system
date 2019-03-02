package cc.orangejuice.srs.student.web.rest;
import cc.orangejuice.srs.student.service.StudentProgressionService;
import cc.orangejuice.srs.student.web.rest.errors.BadRequestAlertException;
import cc.orangejuice.srs.student.web.rest.util.HeaderUtil;
import cc.orangejuice.srs.student.web.rest.util.PaginationUtil;
import cc.orangejuice.srs.student.service.dto.StudentProgressionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StudentProgression.
 */
@RestController
@RequestMapping("/api")
public class StudentProgressionResource {

    private final Logger log = LoggerFactory.getLogger(StudentProgressionResource.class);

    private static final String ENTITY_NAME = "svcStudentStudentProgression";

    private final StudentProgressionService studentProgressionService;

    public StudentProgressionResource(StudentProgressionService studentProgressionService) {
        this.studentProgressionService = studentProgressionService;
    }

    /**
     * POST  /student-progressions : Create a new studentProgression.
     *
     * @param studentProgressionDTO the studentProgressionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentProgressionDTO, or with status 400 (Bad Request) if the studentProgression has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-progressions")
    public ResponseEntity<StudentProgressionDTO> createStudentProgression(@Valid @RequestBody StudentProgressionDTO studentProgressionDTO) throws URISyntaxException {
        log.debug("REST request to save StudentProgression : {}", studentProgressionDTO);
        if (studentProgressionDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentProgression cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentProgressionDTO result = studentProgressionService.save(studentProgressionDTO);
        return ResponseEntity.created(new URI("/api/student-progressions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-progressions : Updates an existing studentProgression.
     *
     * @param studentProgressionDTO the studentProgressionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentProgressionDTO,
     * or with status 400 (Bad Request) if the studentProgressionDTO is not valid,
     * or with status 500 (Internal Server Error) if the studentProgressionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-progressions")
    public ResponseEntity<StudentProgressionDTO> updateStudentProgression(@Valid @RequestBody StudentProgressionDTO studentProgressionDTO) throws URISyntaxException {
        log.debug("REST request to update StudentProgression : {}", studentProgressionDTO);
        if (studentProgressionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentProgressionDTO result = studentProgressionService.save(studentProgressionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentProgressionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-progressions : get all the studentProgressions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentProgressions in body
     */
    @GetMapping("/student-progressions")
    public ResponseEntity<List<StudentProgressionDTO>> getAllStudentProgressions(Pageable pageable) {
        log.debug("REST request to get a page of StudentProgressions");
        Page<StudentProgressionDTO> page = studentProgressionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-progressions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /student-progressions/:id : get the "id" studentProgression.
     *
     * @param id the id of the studentProgressionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentProgressionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/student-progressions/{id}")
    public ResponseEntity<StudentProgressionDTO> getStudentProgression(@PathVariable Long id) {
        log.debug("REST request to get StudentProgression : {}", id);
        Optional<StudentProgressionDTO> studentProgressionDTO = studentProgressionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentProgressionDTO);
    }

    /**
     * DELETE  /student-progressions/:id : delete the "id" studentProgression.
     *
     * @param id the id of the studentProgressionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-progressions/{id}")
    public ResponseEntity<Void> deleteStudentProgression(@PathVariable Long id) {
        log.debug("REST request to delete StudentProgression : {}", id);
        studentProgressionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/student-progressions/firstdecision")
    public ResponseEntity<Void> firstDecision(){
        studentProgressionService.firstDecision();
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "First decision made")).build();
    }

    @GetMapping("/student-progressions/seconddecision")
    public ResponseEntity<Void> secondDecision(){
        studentProgressionService.secondDesion();
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "Second decision made")).build();
    }
    @GetMapping("/student-progressions/thirddecision")
    public ResponseEntity<Void> thirdDecision(){
        studentProgressionService.secondDesion();
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "Second decision made")).build();
    }

}
