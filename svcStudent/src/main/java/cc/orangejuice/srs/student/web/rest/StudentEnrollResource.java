package cc.orangejuice.srs.student.web.rest;
import cc.orangejuice.srs.student.service.StudentEnrollService;
import cc.orangejuice.srs.student.web.rest.errors.BadRequestAlertException;
import cc.orangejuice.srs.student.web.rest.util.HeaderUtil;
import cc.orangejuice.srs.student.web.rest.util.PaginationUtil;
import cc.orangejuice.srs.student.service.dto.StudentEnrollDTO;
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
import javax.ws.rs.Path;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StudentEnroll.
 */
@RestController
@RequestMapping("/api")
public class StudentEnrollResource {

    private final Logger log = LoggerFactory.getLogger(StudentEnrollResource.class);

    private static final String ENTITY_NAME = "svcStudentStudentEnroll";

    private final StudentEnrollService studentEnrollService;

    public StudentEnrollResource(StudentEnrollService studentEnrollService) {
        this.studentEnrollService = studentEnrollService;
    }

    /**
     * POST  /student-enrolls : Create a new studentEnroll.
     *
     * @param studentEnrollDTO the studentEnrollDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentEnrollDTO, or with status 400 (Bad Request) if the studentEnroll has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-enrolls")
    public ResponseEntity<StudentEnrollDTO> createStudentEnroll(@Valid @RequestBody StudentEnrollDTO studentEnrollDTO) throws URISyntaxException {
        log.debug("REST request to save StudentEnroll : {}", studentEnrollDTO);
        if (studentEnrollDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentEnroll cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentEnrollDTO result = studentEnrollService.save(studentEnrollDTO);
        return ResponseEntity.created(new URI("/api/student-enrolls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-enrolls : Updates an existing studentEnroll.
     *
     * @param studentEnrollDTO the studentEnrollDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentEnrollDTO,
     * or with status 400 (Bad Request) if the studentEnrollDTO is not valid,
     * or with status 500 (Internal Server Error) if the studentEnrollDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-enrolls")
    public ResponseEntity<StudentEnrollDTO> updateStudentEnroll(@Valid @RequestBody StudentEnrollDTO studentEnrollDTO) throws URISyntaxException {
        log.debug("REST request to update StudentEnroll : {}", studentEnrollDTO);
        if (studentEnrollDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentEnrollDTO result = studentEnrollService.save(studentEnrollDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentEnrollDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-enrolls : get all the studentEnrolls.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentEnrolls in body
     */
    @GetMapping("/student-enrolls")
    public ResponseEntity<List<StudentEnrollDTO>> getAllStudentEnrolls(Pageable pageable) {
        log.debug("REST request to get a page of StudentEnrolls");
        Page<StudentEnrollDTO> page = studentEnrollService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-enrolls");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /student-enrolls/:id : get the "id" studentEnroll.
     *
     * @param id the id of the studentEnrollDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentEnrollDTO, or with status 404 (Not Found)
     */
    @GetMapping("/student-enrolls/{id}")
    public ResponseEntity<StudentEnrollDTO> getStudentEnroll(@PathVariable Long id) {
        log.debug("REST request to get StudentEnroll : {}", id);
        Optional<StudentEnrollDTO> studentEnrollDTO = studentEnrollService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentEnrollDTO);
    }


    /**
     * one of the steps for transcript for gathering data
     * @param studentId
     * @return enroll detail i.e. status and programme id
     */
    @GetMapping("/student-enrolls/student-enroll-detail/{studentId}")
    public StudentEnrollDTO getStudentEnrollDetail(@PathVariable Long studentId) {
        log.debug("REST request to get student enroll detail by student id: {}", studentId);
        StudentEnrollDTO studentEnrollDTO = studentEnrollService.getOneByStudentId(studentId);
        return studentEnrollDTO;
    }

    /**
     * DELETE  /student-enrolls/:id : delete the "id" studentEnroll.
     *
     * @param id the id of the studentEnrollDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-enrolls/{id}")
    public ResponseEntity<Void> deleteStudentEnroll(@PathVariable Long id) {
        log.debug("REST request to delete StudentEnroll : {}", id);
        studentEnrollService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
