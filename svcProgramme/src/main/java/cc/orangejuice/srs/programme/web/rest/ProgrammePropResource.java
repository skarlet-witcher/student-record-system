package cc.orangejuice.srs.programme.web.rest;
import cc.orangejuice.srs.programme.domain.ProgrammeProp;
import cc.orangejuice.srs.programme.domain.enumeration.ProgrammePropType;
import cc.orangejuice.srs.programme.service.ProgrammePropService;
import cc.orangejuice.srs.programme.web.rest.errors.BadRequestAlertException;
import cc.orangejuice.srs.programme.web.rest.util.HeaderUtil;
import cc.orangejuice.srs.programme.web.rest.util.PaginationUtil;
import cc.orangejuice.srs.programme.service.dto.ProgrammePropDTO;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
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
 * REST controller for managing ProgrammeProp.
 */
@RestController
@RequestMapping("/api")
public class ProgrammePropResource {

    private final Logger log = LoggerFactory.getLogger(ProgrammePropResource.class);

    private static final String ENTITY_NAME = "svcProgrammeProgrammeProp";

    private final ProgrammePropService programmePropService;

    public ProgrammePropResource(ProgrammePropService programmePropService) {
        this.programmePropService = programmePropService;
    }

    /**
     * POST  /programme-props : Create a new programmeProp.
     *
     * @param programmePropDTO the programmePropDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new programmePropDTO, or with status 400 (Bad Request) if the programmeProp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/programme-props")
    public ResponseEntity<ProgrammePropDTO> createProgrammeProp(@Valid @RequestBody ProgrammePropDTO programmePropDTO) throws URISyntaxException {
        log.debug("REST request to save ProgrammeProp : {}", programmePropDTO);
        if (programmePropDTO.getId() != null) {
            throw new BadRequestAlertException("A new programmeProp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProgrammePropDTO result = programmePropService.save(programmePropDTO);
        return ResponseEntity.created(new URI("/api/programme-props/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /programme-props : Updates an existing programmeProp.
     *
     * @param programmePropDTO the programmePropDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated programmePropDTO,
     * or with status 400 (Bad Request) if the programmePropDTO is not valid,
     * or with status 500 (Internal Server Error) if the programmePropDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/programme-props")
    public ResponseEntity<ProgrammePropDTO> updateProgrammeProp(@Valid @RequestBody ProgrammePropDTO programmePropDTO) throws URISyntaxException {
        log.debug("REST request to update ProgrammeProp : {}", programmePropDTO);
        if (programmePropDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProgrammePropDTO result = programmePropService.save(programmePropDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, programmePropDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /programme-props : get all the programmeProps.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of programmeProps in body
     */
    @GetMapping("/programme-props")
    public ResponseEntity<List<ProgrammePropDTO>> getAllProgrammeProps(Pageable pageable) {
        log.debug("REST request to get a page of ProgrammeProps");
        Page<ProgrammePropDTO> page = programmePropService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/programme-props");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /programme-props/:id : get the "id" programmeProp.
     *
     * @param id the id of the programmePropDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the programmePropDTO, or with status 404 (Not Found)
     */
    @GetMapping("/programme-props/{id:\\d+}")
    public ResponseEntity<ProgrammePropDTO> getProgrammeProp(@PathVariable Long id) {
        log.debug("REST request to get ProgrammeProp : {}", id);
        Optional<ProgrammePropDTO> programmePropDTO = programmePropService.findOne(id);
        return ResponseUtil.wrapOrNotFound(programmePropDTO);
    }

    /**
     * DELETE  /programme-props/:id : delete the "id" programmeProp.
     *
     * @param id the id of the programmePropDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/programme-props/{id}")
    public ResponseEntity<Void> deleteProgrammeProp(@PathVariable Long id) {
        log.debug("REST request to delete ProgrammeProp : {}", id);
        programmePropService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "forYearNo", allowEmptyValue = true),
        @ApiImplicitParam(name = "forSemesterNo", allowEmptyValue = true)
    })
    @GetMapping(value = "/programme-props/query")
    public ResponseEntity<ProgrammePropDTO> getProgrammeProps(
        @RequestParam(value = "type", defaultValue = "GENERAL") ProgrammePropType type,
        @RequestParam(value = "forEnrollYear") Integer forEnrollYear,
        @RequestParam(value = "forYearNo", required = false) Integer forYearNo,
        @RequestParam(value = "forSemesterNo", required = false) Integer forSemesterNo,
        @RequestParam("key") String key) {

        log.debug("REST request to get year: {} ,type: {}, semesterNO: {} and key: {} for ProgrammePropDict", forEnrollYear, type, forSemesterNo, key);

        Optional<ProgrammePropDTO> programmePropDTO;

        if (type == ProgrammePropType.YEAR) {
            programmePropDTO = programmePropService.findOneByYear(type, forEnrollYear, forYearNo, key);
        } else if (type == ProgrammePropType.SEMESTER) {
            programmePropDTO = programmePropService.findOneBySemester(type, forEnrollYear, forSemesterNo, key);
        } else {
            return null; // for general?
        }
        return ResponseUtil.wrapOrNotFound(programmePropDTO);
    }


}
