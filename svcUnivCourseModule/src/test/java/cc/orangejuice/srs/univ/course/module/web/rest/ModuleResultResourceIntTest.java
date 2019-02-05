package cc.orangejuice.srs.univ.course.module.web.rest;

import cc.orangejuice.srs.univ.course.module.SvcUnivCourseModuleApp;

import cc.orangejuice.srs.univ.course.module.config.SecurityBeanOverrideConfiguration;

import cc.orangejuice.srs.univ.course.module.domain.ModuleResult;
import cc.orangejuice.srs.univ.course.module.repository.ModuleResultRepository;
import cc.orangejuice.srs.univ.course.module.service.ModuleResultService;
import cc.orangejuice.srs.univ.course.module.service.dto.ModuleResultDTO;
import cc.orangejuice.srs.univ.course.module.service.mapper.ModuleResultMapper;
import cc.orangejuice.srs.univ.course.module.web.rest.errors.ExceptionTranslator;

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


import static cc.orangejuice.srs.univ.course.module.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ModuleResultResource REST controller.
 *
 * @see ModuleResultResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, SvcUnivCourseModuleApp.class})
public class ModuleResultResourceIntTest {

    private static final Double DEFAULT_GRADE = 1D;
    private static final Double UPDATED_GRADE = 2D;

    private static final Double DEFAULT_QCA = 1D;
    private static final Double UPDATED_QCA = 2D;

    private static final Long DEFAULT_STUNDET_ID = 1L;
    private static final Long UPDATED_STUNDET_ID = 2L;

    @Autowired
    private ModuleResultRepository moduleResultRepository;

    @Autowired
    private ModuleResultMapper moduleResultMapper;

    @Autowired
    private ModuleResultService moduleResultService;

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

    private MockMvc restModuleResultMockMvc;

    private ModuleResult moduleResult;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModuleResultResource moduleResultResource = new ModuleResultResource(moduleResultService);
        this.restModuleResultMockMvc = MockMvcBuilders.standaloneSetup(moduleResultResource)
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
    public static ModuleResult createEntity(EntityManager em) {
        ModuleResult moduleResult = new ModuleResult()
            .grade(DEFAULT_GRADE)
            .qca(DEFAULT_QCA)
            .stundetId(DEFAULT_STUNDET_ID);
        return moduleResult;
    }

    @Before
    public void initTest() {
        moduleResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createModuleResult() throws Exception {
        int databaseSizeBeforeCreate = moduleResultRepository.findAll().size();

        // Create the ModuleResult
        ModuleResultDTO moduleResultDTO = moduleResultMapper.toDto(moduleResult);
        restModuleResultMockMvc.perform(post("/api/module-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleResultDTO)))
            .andExpect(status().isCreated());

        // Validate the ModuleResult in the database
        List<ModuleResult> moduleResultList = moduleResultRepository.findAll();
        assertThat(moduleResultList).hasSize(databaseSizeBeforeCreate + 1);
        ModuleResult testModuleResult = moduleResultList.get(moduleResultList.size() - 1);
        assertThat(testModuleResult.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testModuleResult.getQca()).isEqualTo(DEFAULT_QCA);
        assertThat(testModuleResult.getStundetId()).isEqualTo(DEFAULT_STUNDET_ID);
    }

    @Test
    @Transactional
    public void createModuleResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moduleResultRepository.findAll().size();

        // Create the ModuleResult with an existing ID
        moduleResult.setId(1L);
        ModuleResultDTO moduleResultDTO = moduleResultMapper.toDto(moduleResult);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModuleResultMockMvc.perform(post("/api/module-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModuleResult in the database
        List<ModuleResult> moduleResultList = moduleResultRepository.findAll();
        assertThat(moduleResultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllModuleResults() throws Exception {
        // Initialize the database
        moduleResultRepository.saveAndFlush(moduleResult);

        // Get all the moduleResultList
        restModuleResultMockMvc.perform(get("/api/module-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moduleResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.doubleValue())))
            .andExpect(jsonPath("$.[*].qca").value(hasItem(DEFAULT_QCA.doubleValue())))
            .andExpect(jsonPath("$.[*].stundetId").value(hasItem(DEFAULT_STUNDET_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getModuleResult() throws Exception {
        // Initialize the database
        moduleResultRepository.saveAndFlush(moduleResult);

        // Get the moduleResult
        restModuleResultMockMvc.perform(get("/api/module-results/{id}", moduleResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(moduleResult.getId().intValue()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.doubleValue()))
            .andExpect(jsonPath("$.qca").value(DEFAULT_QCA.doubleValue()))
            .andExpect(jsonPath("$.stundetId").value(DEFAULT_STUNDET_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingModuleResult() throws Exception {
        // Get the moduleResult
        restModuleResultMockMvc.perform(get("/api/module-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModuleResult() throws Exception {
        // Initialize the database
        moduleResultRepository.saveAndFlush(moduleResult);

        int databaseSizeBeforeUpdate = moduleResultRepository.findAll().size();

        // Update the moduleResult
        ModuleResult updatedModuleResult = moduleResultRepository.findById(moduleResult.getId()).get();
        // Disconnect from session so that the updates on updatedModuleResult are not directly saved in db
        em.detach(updatedModuleResult);
        updatedModuleResult
            .grade(UPDATED_GRADE)
            .qca(UPDATED_QCA)
            .stundetId(UPDATED_STUNDET_ID);
        ModuleResultDTO moduleResultDTO = moduleResultMapper.toDto(updatedModuleResult);

        restModuleResultMockMvc.perform(put("/api/module-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleResultDTO)))
            .andExpect(status().isOk());

        // Validate the ModuleResult in the database
        List<ModuleResult> moduleResultList = moduleResultRepository.findAll();
        assertThat(moduleResultList).hasSize(databaseSizeBeforeUpdate);
        ModuleResult testModuleResult = moduleResultList.get(moduleResultList.size() - 1);
        assertThat(testModuleResult.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testModuleResult.getQca()).isEqualTo(UPDATED_QCA);
        assertThat(testModuleResult.getStundetId()).isEqualTo(UPDATED_STUNDET_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingModuleResult() throws Exception {
        int databaseSizeBeforeUpdate = moduleResultRepository.findAll().size();

        // Create the ModuleResult
        ModuleResultDTO moduleResultDTO = moduleResultMapper.toDto(moduleResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModuleResultMockMvc.perform(put("/api/module-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ModuleResult in the database
        List<ModuleResult> moduleResultList = moduleResultRepository.findAll();
        assertThat(moduleResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteModuleResult() throws Exception {
        // Initialize the database
        moduleResultRepository.saveAndFlush(moduleResult);

        int databaseSizeBeforeDelete = moduleResultRepository.findAll().size();

        // Delete the moduleResult
        restModuleResultMockMvc.perform(delete("/api/module-results/{id}", moduleResult.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ModuleResult> moduleResultList = moduleResultRepository.findAll();
        assertThat(moduleResultList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModuleResult.class);
        ModuleResult moduleResult1 = new ModuleResult();
        moduleResult1.setId(1L);
        ModuleResult moduleResult2 = new ModuleResult();
        moduleResult2.setId(moduleResult1.getId());
        assertThat(moduleResult1).isEqualTo(moduleResult2);
        moduleResult2.setId(2L);
        assertThat(moduleResult1).isNotEqualTo(moduleResult2);
        moduleResult1.setId(null);
        assertThat(moduleResult1).isNotEqualTo(moduleResult2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModuleResultDTO.class);
        ModuleResultDTO moduleResultDTO1 = new ModuleResultDTO();
        moduleResultDTO1.setId(1L);
        ModuleResultDTO moduleResultDTO2 = new ModuleResultDTO();
        assertThat(moduleResultDTO1).isNotEqualTo(moduleResultDTO2);
        moduleResultDTO2.setId(moduleResultDTO1.getId());
        assertThat(moduleResultDTO1).isEqualTo(moduleResultDTO2);
        moduleResultDTO2.setId(2L);
        assertThat(moduleResultDTO1).isNotEqualTo(moduleResultDTO2);
        moduleResultDTO1.setId(null);
        assertThat(moduleResultDTO1).isNotEqualTo(moduleResultDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(moduleResultMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(moduleResultMapper.fromId(null)).isNull();
    }
}
