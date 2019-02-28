package cc.orangejuice.srs.programme.module.web.rest;

import cc.orangejuice.srs.programme.module.domain.StudentModuleGradeDict;
import cc.orangejuice.srs.programme.module.service.StudentModuleGradeDictService;
import cc.orangejuice.srs.programme.module.service.dto.StudentModuleGradeDictDTO;
import cc.orangejuice.srs.programme.module.web.rest.errors.BadRequestAlertException;
import cc.orangejuice.srs.programme.module.web.rest.util.HeaderUtil;
import cc.orangejuice.srs.programme.module.web.rest.util.PaginationUtil;
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
 * REST controller for managing StudentModuleGradeDict.
 */
@RestController
@RequestMapping("/api")
public class StudentModuleGradeDictResource {

    private final Logger log = LoggerFactory.getLogger(StudentModuleGradeDictResource.class);

    private static final String ENTITY_NAME = "svcProgrammeModuleStudentModuleGradeDict";

    private final StudentModuleGradeDictService studentModuleGradeDictService;

    public StudentModuleGradeDictResource(StudentModuleGradeDictService studentModuleGradeDictService) {
        this.studentModuleGradeDictService = studentModuleGradeDictService;
    }

    /**
     * POST  /student-module-grade-dicts : Create a new studentModuleGradeDict.
     *
     * @param studentModuleGradeDictDTO the studentModuleGradeDictDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentModuleGradeDictDTO, or with status 400 (Bad Request) if the studentModuleGradeDict has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-module-grade-dicts")
    public ResponseEntity<StudentModuleGradeDictDTO> createStudentModuleGradeDict(@Valid @RequestBody StudentModuleGradeDictDTO studentModuleGradeDictDTO) throws URISyntaxException {
        log.debug("REST request to save StudentModuleGradeDict : {}", studentModuleGradeDictDTO);
        if (studentModuleGradeDictDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentModuleGradeDict cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentModuleGradeDictDTO result = studentModuleGradeDictService.save(studentModuleGradeDictDTO);
        return ResponseEntity.created(new URI("/api/student-module-grade-dicts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-module-grade-dicts : Updates an existing studentModuleGradeDict.
     *
     * @param studentModuleGradeDictDTO the studentModuleGradeDictDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentModuleGradeDictDTO,
     * or with status 400 (Bad Request) if the studentModuleGradeDictDTO is not valid,
     * or with status 500 (Internal Server Error) if the studentModuleGradeDictDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-module-grade-dicts")
    public ResponseEntity<StudentModuleGradeDictDTO> updateStudentModuleGradeDict(@Valid @RequestBody StudentModuleGradeDictDTO studentModuleGradeDictDTO) throws URISyntaxException {
        log.debug("REST request to update StudentModuleGradeDict : {}", studentModuleGradeDictDTO);
        if (studentModuleGradeDictDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentModuleGradeDictDTO result = studentModuleGradeDictService.save(studentModuleGradeDictDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentModuleGradeDictDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-module-grade-dicts : get all the studentModuleGradeDicts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentModuleGradeDicts in body
     */
    @GetMapping( value = "/student-module-grade-dicts", params = {"gradeName"})
    public ResponseEntity<List<StudentModuleGradeDictDTO>> getAllStudentModuleGradeDicts(Pageable pageable) {
        log.debug("REST request to get a page of StudentModuleGradeDicts");
        Page<StudentModuleGradeDictDTO> page = studentModuleGradeDictService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-module-grade-dicts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /student-module-grade-dicts/:id : get the "id" studentModuleGradeDict.
     *
     * @param id the id of the studentModuleGradeDictDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentModuleGradeDictDTO, or with status 404 (Not Found)
     */
    @GetMapping("/student-module-grade-dicts/{id}")
    public ResponseEntity<StudentModuleGradeDictDTO> getStudentModuleGradeDict(@PathVariable Long id) {
        log.debug("REST request to get StudentModuleGradeDict : {}", id);
        Optional<StudentModuleGradeDictDTO> studentModuleGradeDictDTO = studentModuleGradeDictService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentModuleGradeDictDTO);
    }

    /**
     * DELETE  /student-module-grade-dicts/:id : delete the "id" studentModuleGradeDict.
     *
     * @param id the id of the studentModuleGradeDictDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-module-grade-dicts/{id}")
    public ResponseEntity<Void> deleteStudentModuleGradeDict(@PathVariable Long id) {
        log.debug("REST request to delete StudentModuleGradeDict : {}", id);
        studentModuleGradeDictService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping(value = "/student-module-grade-dicts/", params = {"gradeName"})
    public ResponseEntity<StudentModuleGradeDictDTO> getStudentModuleGradeDictByGradeName(
        @RequestParam(value = "gradeName") String gradeName) {
        log.debug("REST request to get  {} StudentModuleGradeDict", gradeName);
        Optional<StudentModuleGradeDictDTO> studentModuleGradeDictDTO = studentModuleGradeDictService.findOneGradeTypeByGradeName(gradeName);
        return ResponseUtil.wrapOrNotFound(studentModuleGradeDictDTO);
    }


}
