package cc.orangejuice.srs.univ.course.module.web.rest;

import cc.orangejuice.srs.univ.course.module.SvcUnivCourseModuleApp;

import cc.orangejuice.srs.univ.course.module.domain.Module;
import cc.orangejuice.srs.univ.course.module.repository.ModuleRepository;
import cc.orangejuice.srs.univ.course.module.repository.search.ModuleSearchRepository;
import cc.orangejuice.srs.univ.course.module.service.ModuleService;
import cc.orangejuice.srs.univ.course.module.service.dto.ModuleDTO;
import cc.orangejuice.srs.univ.course.module.service.mapper.ModuleMapper;
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
import org.springframework.util.Base64Utils;
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
 * Test class for the ModuleResource REST controller.
 *
 * @see ModuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SvcUnivCourseModuleApp.class)
public class ModuleResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private ModuleService moduleService;

    /**
     * This repository is mocked in the cc.orangejuice.srs.univ.course.module.repository.search test package.
     *
     * @see cc.orangejuice.srs.univ.course.module.repository.search.ModuleSearchRepositoryMockConfiguration
     */
    @Autowired
    private ModuleSearchRepository mockModuleSearchRepository;

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

    private MockMvc restModuleMockMvc;

    private Module module;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModuleResource moduleResource = new ModuleResource(moduleService);
        this.restModuleMockMvc = MockMvcBuilders.standaloneSetup(moduleResource)
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
    public static Module createEntity(EntityManager em) {
        Module module = new Module()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC);
        return module;
    }

    @Before
    public void initTest() {
        module = createEntity(em);
    }

    @Test
    @Transactional
    public void createModule() throws Exception {
        int databaseSizeBeforeCreate = moduleRepository.findAll().size();

        // Create the Module
        ModuleDTO moduleDTO = moduleMapper.toDto(module);
        restModuleMockMvc.perform(post("/api/modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleDTO)))
            .andExpect(status().isCreated());

        // Validate the Module in the database
        List<Module> moduleList = moduleRepository.findAll();
        assertThat(moduleList).hasSize(databaseSizeBeforeCreate + 1);
        Module testModule = moduleList.get(moduleList.size() - 1);
        assertThat(testModule.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testModule.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testModule.getDesc()).isEqualTo(DEFAULT_DESC);

        // Validate the Module in Elasticsearch
        verify(mockModuleSearchRepository, times(1)).save(testModule);
    }

    @Test
    @Transactional
    public void createModuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moduleRepository.findAll().size();

        // Create the Module with an existing ID
        module.setId(1L);
        ModuleDTO moduleDTO = moduleMapper.toDto(module);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModuleMockMvc.perform(post("/api/modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Module in the database
        List<Module> moduleList = moduleRepository.findAll();
        assertThat(moduleList).hasSize(databaseSizeBeforeCreate);

        // Validate the Module in Elasticsearch
        verify(mockModuleSearchRepository, times(0)).save(module);
    }

    @Test
    @Transactional
    public void getAllModules() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get all the moduleList
        restModuleMockMvc.perform(get("/api/modules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(module.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())));
    }
    
    @Test
    @Transactional
    public void getModule() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get the module
        restModuleMockMvc.perform(get("/api/modules/{id}", module.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(module.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingModule() throws Exception {
        // Get the module
        restModuleMockMvc.perform(get("/api/modules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModule() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        int databaseSizeBeforeUpdate = moduleRepository.findAll().size();

        // Update the module
        Module updatedModule = moduleRepository.findById(module.getId()).get();
        // Disconnect from session so that the updates on updatedModule are not directly saved in db
        em.detach(updatedModule);
        updatedModule
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC);
        ModuleDTO moduleDTO = moduleMapper.toDto(updatedModule);

        restModuleMockMvc.perform(put("/api/modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleDTO)))
            .andExpect(status().isOk());

        // Validate the Module in the database
        List<Module> moduleList = moduleRepository.findAll();
        assertThat(moduleList).hasSize(databaseSizeBeforeUpdate);
        Module testModule = moduleList.get(moduleList.size() - 1);
        assertThat(testModule.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testModule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testModule.getDesc()).isEqualTo(UPDATED_DESC);

        // Validate the Module in Elasticsearch
        verify(mockModuleSearchRepository, times(1)).save(testModule);
    }

    @Test
    @Transactional
    public void updateNonExistingModule() throws Exception {
        int databaseSizeBeforeUpdate = moduleRepository.findAll().size();

        // Create the Module
        ModuleDTO moduleDTO = moduleMapper.toDto(module);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModuleMockMvc.perform(put("/api/modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Module in the database
        List<Module> moduleList = moduleRepository.findAll();
        assertThat(moduleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Module in Elasticsearch
        verify(mockModuleSearchRepository, times(0)).save(module);
    }

    @Test
    @Transactional
    public void deleteModule() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        int databaseSizeBeforeDelete = moduleRepository.findAll().size();

        // Delete the module
        restModuleMockMvc.perform(delete("/api/modules/{id}", module.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Module> moduleList = moduleRepository.findAll();
        assertThat(moduleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Module in Elasticsearch
        verify(mockModuleSearchRepository, times(1)).deleteById(module.getId());
    }

    @Test
    @Transactional
    public void searchModule() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);
        when(mockModuleSearchRepository.search(queryStringQuery("id:" + module.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(module), PageRequest.of(0, 1), 1));
        // Search the module
        restModuleMockMvc.perform(get("/api/_search/modules?query=id:" + module.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(module.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Module.class);
        Module module1 = new Module();
        module1.setId(1L);
        Module module2 = new Module();
        module2.setId(module1.getId());
        assertThat(module1).isEqualTo(module2);
        module2.setId(2L);
        assertThat(module1).isNotEqualTo(module2);
        module1.setId(null);
        assertThat(module1).isNotEqualTo(module2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModuleDTO.class);
        ModuleDTO moduleDTO1 = new ModuleDTO();
        moduleDTO1.setId(1L);
        ModuleDTO moduleDTO2 = new ModuleDTO();
        assertThat(moduleDTO1).isNotEqualTo(moduleDTO2);
        moduleDTO2.setId(moduleDTO1.getId());
        assertThat(moduleDTO1).isEqualTo(moduleDTO2);
        moduleDTO2.setId(2L);
        assertThat(moduleDTO1).isNotEqualTo(moduleDTO2);
        moduleDTO1.setId(null);
        assertThat(moduleDTO1).isNotEqualTo(moduleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(moduleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(moduleMapper.fromId(null)).isNull();
    }
}
