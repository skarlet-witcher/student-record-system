package cc.orangejuice.srs.student.web.rest;

import cc.orangejuice.srs.student.SvcStudentApp;

import cc.orangejuice.srs.student.config.SecurityBeanOverrideConfiguration;

import cc.orangejuice.srs.student.domain.StudentProgression;
import cc.orangejuice.srs.student.repository.StudentProgressionRepository;
import cc.orangejuice.srs.student.service.StudentProgressionService;
import cc.orangejuice.srs.student.service.dto.StudentProgressionDTO;
import cc.orangejuice.srs.student.service.mapper.StudentProgressionMapper;
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

import cc.orangejuice.srs.student.domain.enumeration.ProgressType;
import cc.orangejuice.srs.student.domain.enumeration.ProgressDecision;
/**
 * Test class for the StudentProgressionResource REST controller.
 *
 * @see StudentProgressionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, SvcStudentApp.class})
public class StudentProgressionResourceIntTest {

    private static final Integer DEFAULT_FOR_ACADEMIC_YEAR = 1;
    private static final Integer UPDATED_FOR_ACADEMIC_YEAR = 2;

    private static final Integer DEFAULT_FOR_ACADEMIC_SEMESTER = 1;
    private static final Integer UPDATED_FOR_ACADEMIC_SEMESTER = 2;

    private static final Integer DEFAULT_FOR_PART_NO = 1;
    private static final Integer UPDATED_FOR_PART_NO = 2;

    private static final Double DEFAULT_QCA = 1D;
    private static final Double UPDATED_QCA = 2D;

    private static final ProgressType DEFAULT_PROGRESS_TYPE = ProgressType.SEMESTER;
    private static final ProgressType UPDATED_PROGRESS_TYPE = ProgressType.YEAR;

    private static final ProgressDecision DEFAULT_PROGRESS_DECISION = ProgressDecision.PASS;
    private static final ProgressDecision UPDATED_PROGRESS_DECISION = ProgressDecision.SUSPENSION;

    @Autowired
    private StudentProgressionRepository studentProgressionRepository;

    @Autowired
    private StudentProgressionMapper studentProgressionMapper;

    @Autowired
    private StudentProgressionService studentProgressionService;

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

    private MockMvc restStudentProgressionMockMvc;

    private StudentProgression studentProgression;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentProgressionResource studentProgressionResource = new StudentProgressionResource(studentProgressionService);
        this.restStudentProgressionMockMvc = MockMvcBuilders.standaloneSetup(studentProgressionResource)
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
    public static StudentProgression createEntity(EntityManager em) {
        StudentProgression studentProgression = new StudentProgression()
            .forAcademicYear(DEFAULT_FOR_ACADEMIC_YEAR)
            .forAcademicSemester(DEFAULT_FOR_ACADEMIC_SEMESTER)
            .forPartNo(DEFAULT_FOR_PART_NO)
            .qca(DEFAULT_QCA)
            .progressType(DEFAULT_PROGRESS_TYPE)
            .progressDecision(DEFAULT_PROGRESS_DECISION);
        return studentProgression;
    }

    @Before
    public void initTest() {
        studentProgression = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentProgression() throws Exception {
        int databaseSizeBeforeCreate = studentProgressionRepository.findAll().size();

        // Create the StudentProgression
        StudentProgressionDTO studentProgressionDTO = studentProgressionMapper.toDto(studentProgression);
        restStudentProgressionMockMvc.perform(post("/api/student-progressions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentProgressionDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentProgression in the database
        List<StudentProgression> studentProgressionList = studentProgressionRepository.findAll();
        assertThat(studentProgressionList).hasSize(databaseSizeBeforeCreate + 1);
        StudentProgression testStudentProgression = studentProgressionList.get(studentProgressionList.size() - 1);
        assertThat(testStudentProgression.getForAcademicYear()).isEqualTo(DEFAULT_FOR_ACADEMIC_YEAR);
        assertThat(testStudentProgression.getForAcademicSemester()).isEqualTo(DEFAULT_FOR_ACADEMIC_SEMESTER);
        assertThat(testStudentProgression.getForPartNo()).isEqualTo(DEFAULT_FOR_PART_NO);
        assertThat(testStudentProgression.getQca()).isEqualTo(DEFAULT_QCA);
        assertThat(testStudentProgression.getProgressType()).isEqualTo(DEFAULT_PROGRESS_TYPE);
        assertThat(testStudentProgression.getProgressDecision()).isEqualTo(DEFAULT_PROGRESS_DECISION);
    }

    @Test
    @Transactional
    public void createStudentProgressionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentProgressionRepository.findAll().size();

        // Create the StudentProgression with an existing ID
        studentProgression.setId(1L);
        StudentProgressionDTO studentProgressionDTO = studentProgressionMapper.toDto(studentProgression);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentProgressionMockMvc.perform(post("/api/student-progressions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentProgressionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentProgression in the database
        List<StudentProgression> studentProgressionList = studentProgressionRepository.findAll();
        assertThat(studentProgressionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQcaIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProgressionRepository.findAll().size();
        // set the field null
        studentProgression.setQca(null);

        // Create the StudentProgression, which fails.
        StudentProgressionDTO studentProgressionDTO = studentProgressionMapper.toDto(studentProgression);

        restStudentProgressionMockMvc.perform(post("/api/student-progressions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentProgressionDTO)))
            .andExpect(status().isBadRequest());

        List<StudentProgression> studentProgressionList = studentProgressionRepository.findAll();
        assertThat(studentProgressionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentProgressions() throws Exception {
        // Initialize the database
        studentProgressionRepository.saveAndFlush(studentProgression);

        // Get all the studentProgressionList
        restStudentProgressionMockMvc.perform(get("/api/student-progressions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentProgression.getId().intValue())))
            .andExpect(jsonPath("$.[*].forAcademicYear").value(hasItem(DEFAULT_FOR_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].forAcademicSemester").value(hasItem(DEFAULT_FOR_ACADEMIC_SEMESTER)))
            .andExpect(jsonPath("$.[*].forPartNo").value(hasItem(DEFAULT_FOR_PART_NO)))
            .andExpect(jsonPath("$.[*].qca").value(hasItem(DEFAULT_QCA.doubleValue())))
            .andExpect(jsonPath("$.[*].progressType").value(hasItem(DEFAULT_PROGRESS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].progressDecision").value(hasItem(DEFAULT_PROGRESS_DECISION.toString())));
    }
    
    @Test
    @Transactional
    public void getStudentProgression() throws Exception {
        // Initialize the database
        studentProgressionRepository.saveAndFlush(studentProgression);

        // Get the studentProgression
        restStudentProgressionMockMvc.perform(get("/api/student-progressions/{id}", studentProgression.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentProgression.getId().intValue()))
            .andExpect(jsonPath("$.forAcademicYear").value(DEFAULT_FOR_ACADEMIC_YEAR))
            .andExpect(jsonPath("$.forAcademicSemester").value(DEFAULT_FOR_ACADEMIC_SEMESTER))
            .andExpect(jsonPath("$.forPartNo").value(DEFAULT_FOR_PART_NO))
            .andExpect(jsonPath("$.qca").value(DEFAULT_QCA.doubleValue()))
            .andExpect(jsonPath("$.progressType").value(DEFAULT_PROGRESS_TYPE.toString()))
            .andExpect(jsonPath("$.progressDecision").value(DEFAULT_PROGRESS_DECISION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentProgression() throws Exception {
        // Get the studentProgression
        restStudentProgressionMockMvc.perform(get("/api/student-progressions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentProgression() throws Exception {
        // Initialize the database
        studentProgressionRepository.saveAndFlush(studentProgression);

        int databaseSizeBeforeUpdate = studentProgressionRepository.findAll().size();

        // Update the studentProgression
        StudentProgression updatedStudentProgression = studentProgressionRepository.findById(studentProgression.getId()).get();
        // Disconnect from session so that the updates on updatedStudentProgression are not directly saved in db
        em.detach(updatedStudentProgression);
        updatedStudentProgression
            .forAcademicYear(UPDATED_FOR_ACADEMIC_YEAR)
            .forAcademicSemester(UPDATED_FOR_ACADEMIC_SEMESTER)
            .forPartNo(UPDATED_FOR_PART_NO)
            .qca(UPDATED_QCA)
            .progressType(UPDATED_PROGRESS_TYPE)
            .progressDecision(UPDATED_PROGRESS_DECISION);
        StudentProgressionDTO studentProgressionDTO = studentProgressionMapper.toDto(updatedStudentProgression);

        restStudentProgressionMockMvc.perform(put("/api/student-progressions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentProgressionDTO)))
            .andExpect(status().isOk());

        // Validate the StudentProgression in the database
        List<StudentProgression> studentProgressionList = studentProgressionRepository.findAll();
        assertThat(studentProgressionList).hasSize(databaseSizeBeforeUpdate);
        StudentProgression testStudentProgression = studentProgressionList.get(studentProgressionList.size() - 1);
        assertThat(testStudentProgression.getForAcademicYear()).isEqualTo(UPDATED_FOR_ACADEMIC_YEAR);
        assertThat(testStudentProgression.getForAcademicSemester()).isEqualTo(UPDATED_FOR_ACADEMIC_SEMESTER);
        assertThat(testStudentProgression.getForPartNo()).isEqualTo(UPDATED_FOR_PART_NO);
        assertThat(testStudentProgression.getQca()).isEqualTo(UPDATED_QCA);
        assertThat(testStudentProgression.getProgressType()).isEqualTo(UPDATED_PROGRESS_TYPE);
        assertThat(testStudentProgression.getProgressDecision()).isEqualTo(UPDATED_PROGRESS_DECISION);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentProgression() throws Exception {
        int databaseSizeBeforeUpdate = studentProgressionRepository.findAll().size();

        // Create the StudentProgression
        StudentProgressionDTO studentProgressionDTO = studentProgressionMapper.toDto(studentProgression);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentProgressionMockMvc.perform(put("/api/student-progressions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentProgressionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentProgression in the database
        List<StudentProgression> studentProgressionList = studentProgressionRepository.findAll();
        assertThat(studentProgressionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudentProgression() throws Exception {
        // Initialize the database
        studentProgressionRepository.saveAndFlush(studentProgression);

        int databaseSizeBeforeDelete = studentProgressionRepository.findAll().size();

        // Delete the studentProgression
        restStudentProgressionMockMvc.perform(delete("/api/student-progressions/{id}", studentProgression.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentProgression> studentProgressionList = studentProgressionRepository.findAll();
        assertThat(studentProgressionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentProgression.class);
        StudentProgression studentProgression1 = new StudentProgression();
        studentProgression1.setId(1L);
        StudentProgression studentProgression2 = new StudentProgression();
        studentProgression2.setId(studentProgression1.getId());
        assertThat(studentProgression1).isEqualTo(studentProgression2);
        studentProgression2.setId(2L);
        assertThat(studentProgression1).isNotEqualTo(studentProgression2);
        studentProgression1.setId(null);
        assertThat(studentProgression1).isNotEqualTo(studentProgression2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentProgressionDTO.class);
        StudentProgressionDTO studentProgressionDTO1 = new StudentProgressionDTO();
        studentProgressionDTO1.setId(1L);
        StudentProgressionDTO studentProgressionDTO2 = new StudentProgressionDTO();
        assertThat(studentProgressionDTO1).isNotEqualTo(studentProgressionDTO2);
        studentProgressionDTO2.setId(studentProgressionDTO1.getId());
        assertThat(studentProgressionDTO1).isEqualTo(studentProgressionDTO2);
        studentProgressionDTO2.setId(2L);
        assertThat(studentProgressionDTO1).isNotEqualTo(studentProgressionDTO2);
        studentProgressionDTO1.setId(null);
        assertThat(studentProgressionDTO1).isNotEqualTo(studentProgressionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studentProgressionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studentProgressionMapper.fromId(null)).isNull();
    }
}
