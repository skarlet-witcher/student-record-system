package cc.orangejuice.srs.module.web.rest;

import cc.orangejuice.srs.module.SvcModuleApp;
import cc.orangejuice.srs.module.config.SecurityBeanOverrideConfiguration;
import cc.orangejuice.srs.module.domain.StudentModuleSelection;
import cc.orangejuice.srs.module.repository.StudentModuleSelectionRepository;
import cc.orangejuice.srs.module.service.StudentModuleSelectionService;
import cc.orangejuice.srs.module.service.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.module.service.mapper.StudentModuleSelectionMapper;
import cc.orangejuice.srs.module.web.rest.errors.ExceptionTranslator;
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

import static cc.orangejuice.srs.module.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StudentModuleSelectionResource REST controller.
 *
 * @see StudentModuleSelectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, SvcModuleApp.class})
public class StudentModuleSelectionResourceIntTest {

    private static final Long DEFAULT_STUDENT_ID = 1L;
    private static final Long UPDATED_STUDENT_ID = 2L;

    private static final Integer DEFAULT_ACADEMIC_YEAR = 1;
    private static final Integer UPDATED_ACADEMIC_YEAR = 2;

    private static final Integer DEFAULT_ACADEMIC_SEMESTER = 1;
    private static final Integer UPDATED_ACADEMIC_SEMESTER = 2;

    private static final Integer DEFAULT_YEAR_NO = 1;
    private static final Integer UPDATED_YEAR_NO = 2;

    private static final Integer DEFAULT_SEMESTER_NO = 1;
    private static final Integer UPDATED_SEMESTER_NO = 2;

    private static final Double DEFAULT_CREDIT_HOUR = 1D;
    private static final Double UPDATED_CREDIT_HOUR = 2D;

    private static final Double DEFAULT_MARKS = 1D;
    private static final Double UPDATED_MARKS = 2D;

    private static final Double DEFAULT_QCS = 1D;
    private static final Double UPDATED_QCS = 2D;

    @Autowired
    private StudentModuleSelectionRepository studentModuleSelectionRepository;

    @Autowired
    private StudentModuleSelectionMapper studentModuleSelectionMapper;

    @Autowired
    private StudentModuleSelectionService studentModuleSelectionService;

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

    private MockMvc restStudentModuleSelectionMockMvc;

    private StudentModuleSelection studentModuleSelection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentModuleSelectionResource studentModuleSelectionResource = new StudentModuleSelectionResource(studentModuleSelectionService);
        this.restStudentModuleSelectionMockMvc = MockMvcBuilders.standaloneSetup(studentModuleSelectionResource)
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
    public static StudentModuleSelection createEntity(EntityManager em) {
        StudentModuleSelection studentModuleSelection = new StudentModuleSelection()
            .studentId(DEFAULT_STUDENT_ID)
            .academicYear(DEFAULT_ACADEMIC_YEAR)
            .academicSemester(DEFAULT_ACADEMIC_SEMESTER)
            .yearNo(DEFAULT_YEAR_NO)
            .semesterNo(DEFAULT_SEMESTER_NO)
            .creditHour(DEFAULT_CREDIT_HOUR)
            .marks(DEFAULT_MARKS)
            .qcs(DEFAULT_QCS);
        return studentModuleSelection;
    }

    @Before
    public void initTest() {
        studentModuleSelection = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentModuleSelection() throws Exception {
        int databaseSizeBeforeCreate = studentModuleSelectionRepository.findAll().size();

        // Create the StudentModuleSelection
        StudentModuleSelectionDTO studentModuleSelectionDTO = studentModuleSelectionMapper.toDto(studentModuleSelection);
        restStudentModuleSelectionMockMvc.perform(post("/api/student-module-selections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleSelectionDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentModuleSelection in the database
        List<StudentModuleSelection> studentModuleSelectionList = studentModuleSelectionRepository.findAll();
        assertThat(studentModuleSelectionList).hasSize(databaseSizeBeforeCreate + 1);
        StudentModuleSelection testStudentModuleSelection = studentModuleSelectionList.get(studentModuleSelectionList.size() - 1);
        assertThat(testStudentModuleSelection.getStudentId()).isEqualTo(DEFAULT_STUDENT_ID);
        assertThat(testStudentModuleSelection.getAcademicYear()).isEqualTo(DEFAULT_ACADEMIC_YEAR);
        assertThat(testStudentModuleSelection.getAcademicSemester()).isEqualTo(DEFAULT_ACADEMIC_SEMESTER);
        assertThat(testStudentModuleSelection.getYearNo()).isEqualTo(DEFAULT_YEAR_NO);
        assertThat(testStudentModuleSelection.getSemesterNo()).isEqualTo(DEFAULT_SEMESTER_NO);
        assertThat(testStudentModuleSelection.getCreditHour()).isEqualTo(DEFAULT_CREDIT_HOUR);
        assertThat(testStudentModuleSelection.getMarks()).isEqualTo(DEFAULT_MARKS);
        assertThat(testStudentModuleSelection.getQcs()).isEqualTo(DEFAULT_QCS);
    }

    @Test
    @Transactional
    public void createStudentModuleSelectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentModuleSelectionRepository.findAll().size();

        // Create the StudentModuleSelection with an existing ID
        studentModuleSelection.setId(1L);
        StudentModuleSelectionDTO studentModuleSelectionDTO = studentModuleSelectionMapper.toDto(studentModuleSelection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentModuleSelectionMockMvc.perform(post("/api/student-module-selections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleSelectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentModuleSelection in the database
        List<StudentModuleSelection> studentModuleSelectionList = studentModuleSelectionRepository.findAll();
        assertThat(studentModuleSelectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStudentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentModuleSelectionRepository.findAll().size();
        // set the field null
        studentModuleSelection.setStudentId(null);

        // Create the StudentModuleSelection, which fails.
        StudentModuleSelectionDTO studentModuleSelectionDTO = studentModuleSelectionMapper.toDto(studentModuleSelection);

        restStudentModuleSelectionMockMvc.perform(post("/api/student-module-selections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleSelectionDTO)))
            .andExpect(status().isBadRequest());

        List<StudentModuleSelection> studentModuleSelectionList = studentModuleSelectionRepository.findAll();
        assertThat(studentModuleSelectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAcademicYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentModuleSelectionRepository.findAll().size();
        // set the field null
        studentModuleSelection.setAcademicYear(null);

        // Create the StudentModuleSelection, which fails.
        StudentModuleSelectionDTO studentModuleSelectionDTO = studentModuleSelectionMapper.toDto(studentModuleSelection);

        restStudentModuleSelectionMockMvc.perform(post("/api/student-module-selections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleSelectionDTO)))
            .andExpect(status().isBadRequest());

        List<StudentModuleSelection> studentModuleSelectionList = studentModuleSelectionRepository.findAll();
        assertThat(studentModuleSelectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAcademicSemesterIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentModuleSelectionRepository.findAll().size();
        // set the field null
        studentModuleSelection.setAcademicSemester(null);

        // Create the StudentModuleSelection, which fails.
        StudentModuleSelectionDTO studentModuleSelectionDTO = studentModuleSelectionMapper.toDto(studentModuleSelection);

        restStudentModuleSelectionMockMvc.perform(post("/api/student-module-selections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleSelectionDTO)))
            .andExpect(status().isBadRequest());

        List<StudentModuleSelection> studentModuleSelectionList = studentModuleSelectionRepository.findAll();
        assertThat(studentModuleSelectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentModuleSelectionRepository.findAll().size();
        // set the field null
        studentModuleSelection.setYearNo(null);

        // Create the StudentModuleSelection, which fails.
        StudentModuleSelectionDTO studentModuleSelectionDTO = studentModuleSelectionMapper.toDto(studentModuleSelection);

        restStudentModuleSelectionMockMvc.perform(post("/api/student-module-selections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleSelectionDTO)))
            .andExpect(status().isBadRequest());

        List<StudentModuleSelection> studentModuleSelectionList = studentModuleSelectionRepository.findAll();
        assertThat(studentModuleSelectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSemesterNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentModuleSelectionRepository.findAll().size();
        // set the field null
        studentModuleSelection.setSemesterNo(null);

        // Create the StudentModuleSelection, which fails.
        StudentModuleSelectionDTO studentModuleSelectionDTO = studentModuleSelectionMapper.toDto(studentModuleSelection);

        restStudentModuleSelectionMockMvc.perform(post("/api/student-module-selections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleSelectionDTO)))
            .andExpect(status().isBadRequest());

        List<StudentModuleSelection> studentModuleSelectionList = studentModuleSelectionRepository.findAll();
        assertThat(studentModuleSelectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentModuleSelections() throws Exception {
        // Initialize the database
        studentModuleSelectionRepository.saveAndFlush(studentModuleSelection);

        // Get all the studentModuleSelectionList
        restStudentModuleSelectionMockMvc.perform(get("/api/student-module-selections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentModuleSelection.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentId").value(hasItem(DEFAULT_STUDENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].academicYear").value(hasItem(DEFAULT_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].academicSemester").value(hasItem(DEFAULT_ACADEMIC_SEMESTER)))
            .andExpect(jsonPath("$.[*].yearNo").value(hasItem(DEFAULT_YEAR_NO)))
            .andExpect(jsonPath("$.[*].semesterNo").value(hasItem(DEFAULT_SEMESTER_NO)))
            .andExpect(jsonPath("$.[*].creditHour").value(hasItem(DEFAULT_CREDIT_HOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].marks").value(hasItem(DEFAULT_MARKS.doubleValue())))
            .andExpect(jsonPath("$.[*].qcs").value(hasItem(DEFAULT_QCS.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getStudentModuleSelection() throws Exception {
        // Initialize the database
        studentModuleSelectionRepository.saveAndFlush(studentModuleSelection);

        // Get the studentModuleSelection
        restStudentModuleSelectionMockMvc.perform(get("/api/student-module-selections/{id}", studentModuleSelection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentModuleSelection.getId().intValue()))
            .andExpect(jsonPath("$.studentId").value(DEFAULT_STUDENT_ID.intValue()))
            .andExpect(jsonPath("$.academicYear").value(DEFAULT_ACADEMIC_YEAR))
            .andExpect(jsonPath("$.academicSemester").value(DEFAULT_ACADEMIC_SEMESTER))
            .andExpect(jsonPath("$.yearNo").value(DEFAULT_YEAR_NO))
            .andExpect(jsonPath("$.semesterNo").value(DEFAULT_SEMESTER_NO))
            .andExpect(jsonPath("$.creditHour").value(DEFAULT_CREDIT_HOUR.doubleValue()))
            .andExpect(jsonPath("$.marks").value(DEFAULT_MARKS.doubleValue()))
            .andExpect(jsonPath("$.qcs").value(DEFAULT_QCS.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentModuleSelection() throws Exception {
        // Get the studentModuleSelection
        restStudentModuleSelectionMockMvc.perform(get("/api/student-module-selections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentModuleSelection() throws Exception {
        // Initialize the database
        studentModuleSelectionRepository.saveAndFlush(studentModuleSelection);

        int databaseSizeBeforeUpdate = studentModuleSelectionRepository.findAll().size();

        // Update the studentModuleSelection
        StudentModuleSelection updatedStudentModuleSelection = studentModuleSelectionRepository.findById(studentModuleSelection.getId()).get();
        // Disconnect from session so that the updates on updatedStudentModuleSelection are not directly saved in db
        em.detach(updatedStudentModuleSelection);
        updatedStudentModuleSelection
            .studentId(UPDATED_STUDENT_ID)
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .academicSemester(UPDATED_ACADEMIC_SEMESTER)
            .yearNo(UPDATED_YEAR_NO)
            .semesterNo(UPDATED_SEMESTER_NO)
            .creditHour(UPDATED_CREDIT_HOUR)
            .marks(UPDATED_MARKS)
            .qcs(UPDATED_QCS);
        StudentModuleSelectionDTO studentModuleSelectionDTO = studentModuleSelectionMapper.toDto(updatedStudentModuleSelection);

        restStudentModuleSelectionMockMvc.perform(put("/api/student-module-selections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleSelectionDTO)))
            .andExpect(status().isOk());

        // Validate the StudentModuleSelection in the database
        List<StudentModuleSelection> studentModuleSelectionList = studentModuleSelectionRepository.findAll();
        assertThat(studentModuleSelectionList).hasSize(databaseSizeBeforeUpdate);
        StudentModuleSelection testStudentModuleSelection = studentModuleSelectionList.get(studentModuleSelectionList.size() - 1);
        assertThat(testStudentModuleSelection.getStudentId()).isEqualTo(UPDATED_STUDENT_ID);
        assertThat(testStudentModuleSelection.getAcademicYear()).isEqualTo(UPDATED_ACADEMIC_YEAR);
        assertThat(testStudentModuleSelection.getAcademicSemester()).isEqualTo(UPDATED_ACADEMIC_SEMESTER);
        assertThat(testStudentModuleSelection.getYearNo()).isEqualTo(UPDATED_YEAR_NO);
        assertThat(testStudentModuleSelection.getSemesterNo()).isEqualTo(UPDATED_SEMESTER_NO);
        assertThat(testStudentModuleSelection.getCreditHour()).isEqualTo(UPDATED_CREDIT_HOUR);
        assertThat(testStudentModuleSelection.getMarks()).isEqualTo(UPDATED_MARKS);
        assertThat(testStudentModuleSelection.getQcs()).isEqualTo(UPDATED_QCS);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentModuleSelection() throws Exception {
        int databaseSizeBeforeUpdate = studentModuleSelectionRepository.findAll().size();

        // Create the StudentModuleSelection
        StudentModuleSelectionDTO studentModuleSelectionDTO = studentModuleSelectionMapper.toDto(studentModuleSelection);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentModuleSelectionMockMvc.perform(put("/api/student-module-selections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleSelectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentModuleSelection in the database
        List<StudentModuleSelection> studentModuleSelectionList = studentModuleSelectionRepository.findAll();
        assertThat(studentModuleSelectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudentModuleSelection() throws Exception {
        // Initialize the database
        studentModuleSelectionRepository.saveAndFlush(studentModuleSelection);

        int databaseSizeBeforeDelete = studentModuleSelectionRepository.findAll().size();

        // Delete the studentModuleSelection
        restStudentModuleSelectionMockMvc.perform(delete("/api/student-module-selections/{id}", studentModuleSelection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentModuleSelection> studentModuleSelectionList = studentModuleSelectionRepository.findAll();
        assertThat(studentModuleSelectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentModuleSelection.class);
        StudentModuleSelection studentModuleSelection1 = new StudentModuleSelection();
        studentModuleSelection1.setId(1L);
        StudentModuleSelection studentModuleSelection2 = new StudentModuleSelection();
        studentModuleSelection2.setId(studentModuleSelection1.getId());
        assertThat(studentModuleSelection1).isEqualTo(studentModuleSelection2);
        studentModuleSelection2.setId(2L);
        assertThat(studentModuleSelection1).isNotEqualTo(studentModuleSelection2);
        studentModuleSelection1.setId(null);
        assertThat(studentModuleSelection1).isNotEqualTo(studentModuleSelection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentModuleSelectionDTO.class);
        StudentModuleSelectionDTO studentModuleSelectionDTO1 = new StudentModuleSelectionDTO();
        studentModuleSelectionDTO1.setId(1L);
        StudentModuleSelectionDTO studentModuleSelectionDTO2 = new StudentModuleSelectionDTO();
        assertThat(studentModuleSelectionDTO1).isNotEqualTo(studentModuleSelectionDTO2);
        studentModuleSelectionDTO2.setId(studentModuleSelectionDTO1.getId());
        assertThat(studentModuleSelectionDTO1).isEqualTo(studentModuleSelectionDTO2);
        studentModuleSelectionDTO2.setId(2L);
        assertThat(studentModuleSelectionDTO1).isNotEqualTo(studentModuleSelectionDTO2);
        studentModuleSelectionDTO1.setId(null);
        assertThat(studentModuleSelectionDTO1).isNotEqualTo(studentModuleSelectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studentModuleSelectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studentModuleSelectionMapper.fromId(null)).isNull();
    }
}
