package cc.orangejuice.srs.student.web.rest;

import cc.orangejuice.srs.student.SvcStudentApp;

import cc.orangejuice.srs.student.config.SecurityBeanOverrideConfiguration;

import cc.orangejuice.srs.student.domain.StudentEnroll;
import cc.orangejuice.srs.student.repository.StudentEnrollRepository;
import cc.orangejuice.srs.student.service.StudentEnrollService;
import cc.orangejuice.srs.student.service.dto.StudentEnrollDTO;
import cc.orangejuice.srs.student.service.mapper.StudentEnrollMapper;
import cc.orangejuice.srs.student.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static cc.orangejuice.srs.student.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cc.orangejuice.srs.student.domain.enumeration.Degree;
import cc.orangejuice.srs.student.domain.enumeration.EnrollStatus;
/**
 * Test class for the StudentEnrollResource REST controller.
 *
 * @see StudentEnrollResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, SvcStudentApp.class})
public class StudentEnrollResourceIntTest {

    private static final Integer DEFAULT_ENROLL_YEAR = 1;
    private static final Integer UPDATED_ENROLL_YEAR = 2;

    private static final Long DEFAULT_FOR_PROGRAMME_ID = 1L;
    private static final Long UPDATED_FOR_PROGRAMME_ID = 2L;

    private static final Degree DEFAULT_FOR_DEGREE = Degree.BACHELOR;
    private static final Degree UPDATED_FOR_DEGREE = Degree.MASTER;

    private static final EnrollStatus DEFAULT_STATUS = EnrollStatus.TAKING;
    private static final EnrollStatus UPDATED_STATUS = EnrollStatus.SUSPEND;

    @Autowired
    private StudentEnrollRepository studentEnrollRepository;

    @Autowired
    private StudentEnrollMapper studentEnrollMapper;

    @Autowired
    private StudentEnrollService studentEnrollService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restStudentEnrollMockMvc;

    private StudentEnroll studentEnroll;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentEnrollResource studentEnrollResource = new StudentEnrollResource(studentEnrollService);
        this.restStudentEnrollMockMvc = MockMvcBuilders.standaloneSetup(studentEnrollResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentEnroll createEntity(EntityManager em) {
        StudentEnroll studentEnroll = new StudentEnroll()
            .enrollYear(DEFAULT_ENROLL_YEAR)
            .forProgrammeId(DEFAULT_FOR_PROGRAMME_ID)
            .forDegree(DEFAULT_FOR_DEGREE)
            .status(DEFAULT_STATUS);
        return studentEnroll;
    }

    @Before
    public void initTest() {
        studentEnroll = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentEnroll() throws Exception {
        int databaseSizeBeforeCreate = studentEnrollRepository.findAll().size();

        // Create the StudentEnroll
        StudentEnrollDTO studentEnrollDTO = studentEnrollMapper.toDto(studentEnroll);
        restStudentEnrollMockMvc.perform(post("/api/student-enrolls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentEnrollDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentEnroll in the database
        List<StudentEnroll> studentEnrollList = studentEnrollRepository.findAll();
        assertThat(studentEnrollList).hasSize(databaseSizeBeforeCreate + 1);
        StudentEnroll testStudentEnroll = studentEnrollList.get(studentEnrollList.size() - 1);
        assertThat(testStudentEnroll.getEnrollYear()).isEqualTo(DEFAULT_ENROLL_YEAR);
        assertThat(testStudentEnroll.getForProgrammeId()).isEqualTo(DEFAULT_FOR_PROGRAMME_ID);
        assertThat(testStudentEnroll.getForDegree()).isEqualTo(DEFAULT_FOR_DEGREE);
        assertThat(testStudentEnroll.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createStudentEnrollWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentEnrollRepository.findAll().size();

        // Create the StudentEnroll with an existing ID
        studentEnroll.setId(1L);
        StudentEnrollDTO studentEnrollDTO = studentEnrollMapper.toDto(studentEnroll);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentEnrollMockMvc.perform(post("/api/student-enrolls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentEnrollDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentEnroll in the database
        List<StudentEnroll> studentEnrollList = studentEnrollRepository.findAll();
        assertThat(studentEnrollList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEnrollYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentEnrollRepository.findAll().size();
        // set the field null
        studentEnroll.setEnrollYear(null);

        // Create the StudentEnroll, which fails.
        StudentEnrollDTO studentEnrollDTO = studentEnrollMapper.toDto(studentEnroll);

        restStudentEnrollMockMvc.perform(post("/api/student-enrolls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentEnrollDTO)))
            .andExpect(status().isBadRequest());

        List<StudentEnroll> studentEnrollList = studentEnrollRepository.findAll();
        assertThat(studentEnrollList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkForProgrammeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentEnrollRepository.findAll().size();
        // set the field null
        studentEnroll.setForProgrammeId(null);

        // Create the StudentEnroll, which fails.
        StudentEnrollDTO studentEnrollDTO = studentEnrollMapper.toDto(studentEnroll);

        restStudentEnrollMockMvc.perform(post("/api/student-enrolls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentEnrollDTO)))
            .andExpect(status().isBadRequest());

        List<StudentEnroll> studentEnrollList = studentEnrollRepository.findAll();
        assertThat(studentEnrollList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkForDegreeIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentEnrollRepository.findAll().size();
        // set the field null
        studentEnroll.setForDegree(null);

        // Create the StudentEnroll, which fails.
        StudentEnrollDTO studentEnrollDTO = studentEnrollMapper.toDto(studentEnroll);

        restStudentEnrollMockMvc.perform(post("/api/student-enrolls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentEnrollDTO)))
            .andExpect(status().isBadRequest());

        List<StudentEnroll> studentEnrollList = studentEnrollRepository.findAll();
        assertThat(studentEnrollList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentEnrollRepository.findAll().size();
        // set the field null
        studentEnroll.setStatus(null);

        // Create the StudentEnroll, which fails.
        StudentEnrollDTO studentEnrollDTO = studentEnrollMapper.toDto(studentEnroll);

        restStudentEnrollMockMvc.perform(post("/api/student-enrolls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentEnrollDTO)))
            .andExpect(status().isBadRequest());

        List<StudentEnroll> studentEnrollList = studentEnrollRepository.findAll();
        assertThat(studentEnrollList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentEnrolls() throws Exception {
        // Initialize the database
        studentEnrollRepository.saveAndFlush(studentEnroll);

        // Get all the studentEnrollList
        restStudentEnrollMockMvc.perform(get("/api/student-enrolls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentEnroll.getId().intValue())))
            .andExpect(jsonPath("$.[*].enrollYear").value(hasItem(DEFAULT_ENROLL_YEAR)))
            .andExpect(jsonPath("$.[*].forProgrammeId").value(hasItem(DEFAULT_FOR_PROGRAMME_ID.intValue())))
            .andExpect(jsonPath("$.[*].forDegree").value(hasItem(DEFAULT_FOR_DEGREE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getStudentEnroll() throws Exception {
        // Initialize the database
        studentEnrollRepository.saveAndFlush(studentEnroll);

        // Get the studentEnroll
        restStudentEnrollMockMvc.perform(get("/api/student-enrolls/{id}", studentEnroll.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentEnroll.getId().intValue()))
            .andExpect(jsonPath("$.enrollYear").value(DEFAULT_ENROLL_YEAR))
            .andExpect(jsonPath("$.forProgrammeId").value(DEFAULT_FOR_PROGRAMME_ID.intValue()))
            .andExpect(jsonPath("$.forDegree").value(DEFAULT_FOR_DEGREE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentEnroll() throws Exception {
        // Get the studentEnroll
        restStudentEnrollMockMvc.perform(get("/api/student-enrolls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentEnroll() throws Exception {
        // Initialize the database
        studentEnrollRepository.saveAndFlush(studentEnroll);

        int databaseSizeBeforeUpdate = studentEnrollRepository.findAll().size();

        // Update the studentEnroll
        StudentEnroll updatedStudentEnroll = studentEnrollRepository.findById(studentEnroll.getId()).get();
        // Disconnect from session so that the updates on updatedStudentEnroll are not directly saved in db
        em.detach(updatedStudentEnroll);
        updatedStudentEnroll
            .enrollYear(UPDATED_ENROLL_YEAR)
            .forProgrammeId(UPDATED_FOR_PROGRAMME_ID)
            .forDegree(UPDATED_FOR_DEGREE)
            .status(UPDATED_STATUS);
        StudentEnrollDTO studentEnrollDTO = studentEnrollMapper.toDto(updatedStudentEnroll);

        restStudentEnrollMockMvc.perform(put("/api/student-enrolls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentEnrollDTO)))
            .andExpect(status().isOk());

        // Validate the StudentEnroll in the database
        List<StudentEnroll> studentEnrollList = studentEnrollRepository.findAll();
        assertThat(studentEnrollList).hasSize(databaseSizeBeforeUpdate);
        StudentEnroll testStudentEnroll = studentEnrollList.get(studentEnrollList.size() - 1);
        assertThat(testStudentEnroll.getEnrollYear()).isEqualTo(UPDATED_ENROLL_YEAR);
        assertThat(testStudentEnroll.getForProgrammeId()).isEqualTo(UPDATED_FOR_PROGRAMME_ID);
        assertThat(testStudentEnroll.getForDegree()).isEqualTo(UPDATED_FOR_DEGREE);
        assertThat(testStudentEnroll.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentEnroll() throws Exception {
        int databaseSizeBeforeUpdate = studentEnrollRepository.findAll().size();

        // Create the StudentEnroll
        StudentEnrollDTO studentEnrollDTO = studentEnrollMapper.toDto(studentEnroll);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentEnrollMockMvc.perform(put("/api/student-enrolls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentEnrollDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentEnroll in the database
        List<StudentEnroll> studentEnrollList = studentEnrollRepository.findAll();
        assertThat(studentEnrollList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudentEnroll() throws Exception {
        // Initialize the database
        studentEnrollRepository.saveAndFlush(studentEnroll);

        int databaseSizeBeforeDelete = studentEnrollRepository.findAll().size();

        // Delete the studentEnroll
        restStudentEnrollMockMvc.perform(delete("/api/student-enrolls/{id}", studentEnroll.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentEnroll> studentEnrollList = studentEnrollRepository.findAll();
        assertThat(studentEnrollList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentEnroll.class);
        StudentEnroll studentEnroll1 = new StudentEnroll();
        studentEnroll1.setId(1L);
        StudentEnroll studentEnroll2 = new StudentEnroll();
        studentEnroll2.setId(studentEnroll1.getId());
        assertThat(studentEnroll1).isEqualTo(studentEnroll2);
        studentEnroll2.setId(2L);
        assertThat(studentEnroll1).isNotEqualTo(studentEnroll2);
        studentEnroll1.setId(null);
        assertThat(studentEnroll1).isNotEqualTo(studentEnroll2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentEnrollDTO.class);
        StudentEnrollDTO studentEnrollDTO1 = new StudentEnrollDTO();
        studentEnrollDTO1.setId(1L);
        StudentEnrollDTO studentEnrollDTO2 = new StudentEnrollDTO();
        assertThat(studentEnrollDTO1).isNotEqualTo(studentEnrollDTO2);
        studentEnrollDTO2.setId(studentEnrollDTO1.getId());
        assertThat(studentEnrollDTO1).isEqualTo(studentEnrollDTO2);
        studentEnrollDTO2.setId(2L);
        assertThat(studentEnrollDTO1).isNotEqualTo(studentEnrollDTO2);
        studentEnrollDTO1.setId(null);
        assertThat(studentEnrollDTO1).isNotEqualTo(studentEnrollDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studentEnrollMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studentEnrollMapper.fromId(null)).isNull();
    }
}
