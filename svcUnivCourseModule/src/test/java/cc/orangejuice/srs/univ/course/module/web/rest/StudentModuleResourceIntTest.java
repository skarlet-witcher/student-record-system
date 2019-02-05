package cc.orangejuice.srs.univ.course.module.web.rest;

import cc.orangejuice.srs.univ.course.module.SvcUnivCourseModuleApp;

import cc.orangejuice.srs.univ.course.module.config.SecurityBeanOverrideConfiguration;

import cc.orangejuice.srs.univ.course.module.domain.StudentModule;
import cc.orangejuice.srs.univ.course.module.repository.StudentModuleRepository;
import cc.orangejuice.srs.univ.course.module.repository.search.StudentModuleSearchRepository;
import cc.orangejuice.srs.univ.course.module.service.StudentModuleService;
import cc.orangejuice.srs.univ.course.module.service.dto.StudentModuleDTO;
import cc.orangejuice.srs.univ.course.module.service.mapper.StudentModuleMapper;
import cc.orangejuice.srs.univ.course.module.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static cc.orangejuice.srs.univ.course.module.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StudentModuleResource REST controller.
 *
 * @see StudentModuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, SvcUnivCourseModuleApp.class})
public class StudentModuleResourceIntTest {

    private static final Long DEFAULT_STUDENT_ID = 1L;
    private static final Long UPDATED_STUDENT_ID = 2L;

    private static final Long DEFAULT_MODULE_ID = 1L;
    private static final Long UPDATED_MODULE_ID = 2L;

    private static final Integer DEFAULT_ENROLL_YEAR = 1;
    private static final Integer UPDATED_ENROLL_YEAR = 2;

    private static final Integer DEFAULT_ENROLL_SEMESTER = 1;
    private static final Integer UPDATED_ENROLL_SEMESTER = 2;

    @Autowired
    private StudentModuleRepository studentModuleRepository;

    @Autowired
    private StudentModuleMapper studentModuleMapper;

    @Autowired
    private StudentModuleService studentModuleService;

    /**
     * This repository is mocked in the cc.orangejuice.srs.univ.course.module.repository.search test package.
     *
     * @see cc.orangejuice.srs.univ.course.module.repository.search.StudentModuleSearchRepositoryMockConfiguration
     */
    @Autowired
    private StudentModuleSearchRepository mockStudentModuleSearchRepository;

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

    private MockMvc restStudentModuleMockMvc;

    private StudentModule studentModule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentModuleResource studentModuleResource = new StudentModuleResource(studentModuleService);
        this.restStudentModuleMockMvc = MockMvcBuilders.standaloneSetup(studentModuleResource)
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
    public static StudentModule createEntity(EntityManager em) {
        StudentModule studentModule = new StudentModule()
            .studentId(DEFAULT_STUDENT_ID)
            .moduleId(DEFAULT_MODULE_ID)
            .enrollYear(DEFAULT_ENROLL_YEAR)
            .enrollSemester(DEFAULT_ENROLL_SEMESTER);
        return studentModule;
    }

    @Before
    public void initTest() {
        studentModule = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentModule() throws Exception {
        int databaseSizeBeforeCreate = studentModuleRepository.findAll().size();

        // Create the StudentModule
        StudentModuleDTO studentModuleDTO = studentModuleMapper.toDto(studentModule);
        restStudentModuleMockMvc.perform(post("/api/student-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentModule in the database
        List<StudentModule> studentModuleList = studentModuleRepository.findAll();
        assertThat(studentModuleList).hasSize(databaseSizeBeforeCreate + 1);
        StudentModule testStudentModule = studentModuleList.get(studentModuleList.size() - 1);
        assertThat(testStudentModule.getStudentId()).isEqualTo(DEFAULT_STUDENT_ID);
        assertThat(testStudentModule.getModuleId()).isEqualTo(DEFAULT_MODULE_ID);
        assertThat(testStudentModule.getEnrollYear()).isEqualTo(DEFAULT_ENROLL_YEAR);
        assertThat(testStudentModule.getEnrollSemester()).isEqualTo(DEFAULT_ENROLL_SEMESTER);

        // Validate the StudentModule in Elasticsearch
        verify(mockStudentModuleSearchRepository, times(1)).save(testStudentModule);
    }

    @Test
    @Transactional
    public void createStudentModuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentModuleRepository.findAll().size();

        // Create the StudentModule with an existing ID
        studentModule.setId(1L);
        StudentModuleDTO studentModuleDTO = studentModuleMapper.toDto(studentModule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentModuleMockMvc.perform(post("/api/student-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentModule in the database
        List<StudentModule> studentModuleList = studentModuleRepository.findAll();
        assertThat(studentModuleList).hasSize(databaseSizeBeforeCreate);

        // Validate the StudentModule in Elasticsearch
        verify(mockStudentModuleSearchRepository, times(0)).save(studentModule);
    }

    @Test
    @Transactional
    public void checkStudentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentModuleRepository.findAll().size();
        // set the field null
        studentModule.setStudentId(null);

        // Create the StudentModule, which fails.
        StudentModuleDTO studentModuleDTO = studentModuleMapper.toDto(studentModule);

        restStudentModuleMockMvc.perform(post("/api/student-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleDTO)))
            .andExpect(status().isBadRequest());

        List<StudentModule> studentModuleList = studentModuleRepository.findAll();
        assertThat(studentModuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModuleIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentModuleRepository.findAll().size();
        // set the field null
        studentModule.setModuleId(null);

        // Create the StudentModule, which fails.
        StudentModuleDTO studentModuleDTO = studentModuleMapper.toDto(studentModule);

        restStudentModuleMockMvc.perform(post("/api/student-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleDTO)))
            .andExpect(status().isBadRequest());

        List<StudentModule> studentModuleList = studentModuleRepository.findAll();
        assertThat(studentModuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnrollYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentModuleRepository.findAll().size();
        // set the field null
        studentModule.setEnrollYear(null);

        // Create the StudentModule, which fails.
        StudentModuleDTO studentModuleDTO = studentModuleMapper.toDto(studentModule);

        restStudentModuleMockMvc.perform(post("/api/student-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleDTO)))
            .andExpect(status().isBadRequest());

        List<StudentModule> studentModuleList = studentModuleRepository.findAll();
        assertThat(studentModuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnrollSemesterIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentModuleRepository.findAll().size();
        // set the field null
        studentModule.setEnrollSemester(null);

        // Create the StudentModule, which fails.
        StudentModuleDTO studentModuleDTO = studentModuleMapper.toDto(studentModule);

        restStudentModuleMockMvc.perform(post("/api/student-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleDTO)))
            .andExpect(status().isBadRequest());

        List<StudentModule> studentModuleList = studentModuleRepository.findAll();
        assertThat(studentModuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentModules() throws Exception {
        // Initialize the database
        studentModuleRepository.saveAndFlush(studentModule);

        // Get all the studentModuleList
        restStudentModuleMockMvc.perform(get("/api/student-modules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentModule.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentId").value(hasItem(DEFAULT_STUDENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].moduleId").value(hasItem(DEFAULT_MODULE_ID.intValue())))
            .andExpect(jsonPath("$.[*].enrollYear").value(hasItem(DEFAULT_ENROLL_YEAR)))
            .andExpect(jsonPath("$.[*].enrollSemester").value(hasItem(DEFAULT_ENROLL_SEMESTER)));
    }
    
    @Test
    @Transactional
    public void getStudentModule() throws Exception {
        // Initialize the database
        studentModuleRepository.saveAndFlush(studentModule);

        // Get the studentModule
        restStudentModuleMockMvc.perform(get("/api/student-modules/{id}", studentModule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentModule.getId().intValue()))
            .andExpect(jsonPath("$.studentId").value(DEFAULT_STUDENT_ID.intValue()))
            .andExpect(jsonPath("$.moduleId").value(DEFAULT_MODULE_ID.intValue()))
            .andExpect(jsonPath("$.enrollYear").value(DEFAULT_ENROLL_YEAR))
            .andExpect(jsonPath("$.enrollSemester").value(DEFAULT_ENROLL_SEMESTER));
    }

    @Test
    @Transactional
    public void getNonExistingStudentModule() throws Exception {
        // Get the studentModule
        restStudentModuleMockMvc.perform(get("/api/student-modules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentModule() throws Exception {
        // Initialize the database
        studentModuleRepository.saveAndFlush(studentModule);

        int databaseSizeBeforeUpdate = studentModuleRepository.findAll().size();

        // Update the studentModule
        StudentModule updatedStudentModule = studentModuleRepository.findById(studentModule.getId()).get();
        // Disconnect from session so that the updates on updatedStudentModule are not directly saved in db
        em.detach(updatedStudentModule);
        updatedStudentModule
            .studentId(UPDATED_STUDENT_ID)
            .moduleId(UPDATED_MODULE_ID)
            .enrollYear(UPDATED_ENROLL_YEAR)
            .enrollSemester(UPDATED_ENROLL_SEMESTER);
        StudentModuleDTO studentModuleDTO = studentModuleMapper.toDto(updatedStudentModule);

        restStudentModuleMockMvc.perform(put("/api/student-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleDTO)))
            .andExpect(status().isOk());

        // Validate the StudentModule in the database
        List<StudentModule> studentModuleList = studentModuleRepository.findAll();
        assertThat(studentModuleList).hasSize(databaseSizeBeforeUpdate);
        StudentModule testStudentModule = studentModuleList.get(studentModuleList.size() - 1);
        assertThat(testStudentModule.getStudentId()).isEqualTo(UPDATED_STUDENT_ID);
        assertThat(testStudentModule.getModuleId()).isEqualTo(UPDATED_MODULE_ID);
        assertThat(testStudentModule.getEnrollYear()).isEqualTo(UPDATED_ENROLL_YEAR);
        assertThat(testStudentModule.getEnrollSemester()).isEqualTo(UPDATED_ENROLL_SEMESTER);

        // Validate the StudentModule in Elasticsearch
        verify(mockStudentModuleSearchRepository, times(1)).save(testStudentModule);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentModule() throws Exception {
        int databaseSizeBeforeUpdate = studentModuleRepository.findAll().size();

        // Create the StudentModule
        StudentModuleDTO studentModuleDTO = studentModuleMapper.toDto(studentModule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentModuleMockMvc.perform(put("/api/student-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentModuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentModule in the database
        List<StudentModule> studentModuleList = studentModuleRepository.findAll();
        assertThat(studentModuleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StudentModule in Elasticsearch
        verify(mockStudentModuleSearchRepository, times(0)).save(studentModule);
    }

    @Test
    @Transactional
    public void deleteStudentModule() throws Exception {
        // Initialize the database
        studentModuleRepository.saveAndFlush(studentModule);

        int databaseSizeBeforeDelete = studentModuleRepository.findAll().size();

        // Delete the studentModule
        restStudentModuleMockMvc.perform(delete("/api/student-modules/{id}", studentModule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentModule> studentModuleList = studentModuleRepository.findAll();
        assertThat(studentModuleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StudentModule in Elasticsearch
        verify(mockStudentModuleSearchRepository, times(1)).deleteById(studentModule.getId());
    }

    @Test
    @Transactional
    public void searchStudentModule() throws Exception {
        // Initialize the database
        studentModuleRepository.saveAndFlush(studentModule);
        when(mockStudentModuleSearchRepository.search(queryStringQuery("id:" + studentModule.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(studentModule), PageRequest.of(0, 1), 1));
        // Search the studentModule
        restStudentModuleMockMvc.perform(get("/api/_search/student-modules?query=id:" + studentModule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentModule.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentId").value(hasItem(DEFAULT_STUDENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].moduleId").value(hasItem(DEFAULT_MODULE_ID.intValue())))
            .andExpect(jsonPath("$.[*].enrollYear").value(hasItem(DEFAULT_ENROLL_YEAR)))
            .andExpect(jsonPath("$.[*].enrollSemester").value(hasItem(DEFAULT_ENROLL_SEMESTER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentModule.class);
        StudentModule studentModule1 = new StudentModule();
        studentModule1.setId(1L);
        StudentModule studentModule2 = new StudentModule();
        studentModule2.setId(studentModule1.getId());
        assertThat(studentModule1).isEqualTo(studentModule2);
        studentModule2.setId(2L);
        assertThat(studentModule1).isNotEqualTo(studentModule2);
        studentModule1.setId(null);
        assertThat(studentModule1).isNotEqualTo(studentModule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentModuleDTO.class);
        StudentModuleDTO studentModuleDTO1 = new StudentModuleDTO();
        studentModuleDTO1.setId(1L);
        StudentModuleDTO studentModuleDTO2 = new StudentModuleDTO();
        assertThat(studentModuleDTO1).isNotEqualTo(studentModuleDTO2);
        studentModuleDTO2.setId(studentModuleDTO1.getId());
        assertThat(studentModuleDTO1).isEqualTo(studentModuleDTO2);
        studentModuleDTO2.setId(2L);
        assertThat(studentModuleDTO1).isNotEqualTo(studentModuleDTO2);
        studentModuleDTO1.setId(null);
        assertThat(studentModuleDTO1).isNotEqualTo(studentModuleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studentModuleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studentModuleMapper.fromId(null)).isNull();
    }
}
