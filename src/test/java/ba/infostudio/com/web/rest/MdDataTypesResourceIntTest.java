package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.MdDataTypes;
import ba.infostudio.com.repository.MdDataTypesRepository;
import ba.infostudio.com.service.dto.MdDataTypesDTO;
import ba.infostudio.com.service.mapper.MdDataTypesMapper;
import ba.infostudio.com.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.util.List;

import static ba.infostudio.com.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MdDataTypesResource REST controller.
 *
 * @see MdDataTypesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class MdDataTypesResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private MdDataTypesRepository mdDataTypesRepository;

    @Autowired
    private MdDataTypesMapper mdDataTypesMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMdDataTypesMockMvc;

    private MdDataTypes mdDataTypes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MdDataTypesResource mdDataTypesResource = new MdDataTypesResource(mdDataTypesRepository, mdDataTypesMapper);
        this.restMdDataTypesMockMvc = MockMvcBuilders.standaloneSetup(mdDataTypesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MdDataTypes createEntity(EntityManager em) {
        MdDataTypes mdDataTypes = new MdDataTypes()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return mdDataTypes;
    }

    @Before
    public void initTest() {
        mdDataTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createMdDataTypes() throws Exception {
        int databaseSizeBeforeCreate = mdDataTypesRepository.findAll().size();

        // Create the MdDataTypes
        MdDataTypesDTO mdDataTypesDTO = mdDataTypesMapper.toDto(mdDataTypes);
        restMdDataTypesMockMvc.perform(post("/api/md-data-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDataTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the MdDataTypes in the database
        List<MdDataTypes> mdDataTypesList = mdDataTypesRepository.findAll();
        assertThat(mdDataTypesList).hasSize(databaseSizeBeforeCreate + 1);
        MdDataTypes testMdDataTypes = mdDataTypesList.get(mdDataTypesList.size() - 1);
        assertThat(testMdDataTypes.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMdDataTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMdDataTypes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createMdDataTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mdDataTypesRepository.findAll().size();

        // Create the MdDataTypes with an existing ID
        mdDataTypes.setId(1L);
        MdDataTypesDTO mdDataTypesDTO = mdDataTypesMapper.toDto(mdDataTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMdDataTypesMockMvc.perform(post("/api/md-data-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDataTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MdDataTypes in the database
        List<MdDataTypes> mdDataTypesList = mdDataTypesRepository.findAll();
        assertThat(mdDataTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mdDataTypesRepository.findAll().size();
        // set the field null
        mdDataTypes.setCode(null);

        // Create the MdDataTypes, which fails.
        MdDataTypesDTO mdDataTypesDTO = mdDataTypesMapper.toDto(mdDataTypes);

        restMdDataTypesMockMvc.perform(post("/api/md-data-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDataTypesDTO)))
            .andExpect(status().isBadRequest());

        List<MdDataTypes> mdDataTypesList = mdDataTypesRepository.findAll();
        assertThat(mdDataTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mdDataTypesRepository.findAll().size();
        // set the field null
        mdDataTypes.setName(null);

        // Create the MdDataTypes, which fails.
        MdDataTypesDTO mdDataTypesDTO = mdDataTypesMapper.toDto(mdDataTypes);

        restMdDataTypesMockMvc.perform(post("/api/md-data-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDataTypesDTO)))
            .andExpect(status().isBadRequest());

        List<MdDataTypes> mdDataTypesList = mdDataTypesRepository.findAll();
        assertThat(mdDataTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMdDataTypes() throws Exception {
        // Initialize the database
        mdDataTypesRepository.saveAndFlush(mdDataTypes);

        // Get all the mdDataTypesList
        restMdDataTypesMockMvc.perform(get("/api/md-data-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mdDataTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getMdDataTypes() throws Exception {
        // Initialize the database
        mdDataTypesRepository.saveAndFlush(mdDataTypes);

        // Get the mdDataTypes
        restMdDataTypesMockMvc.perform(get("/api/md-data-types/{id}", mdDataTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mdDataTypes.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMdDataTypes() throws Exception {
        // Get the mdDataTypes
        restMdDataTypesMockMvc.perform(get("/api/md-data-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMdDataTypes() throws Exception {
        // Initialize the database
        mdDataTypesRepository.saveAndFlush(mdDataTypes);
        int databaseSizeBeforeUpdate = mdDataTypesRepository.findAll().size();

        // Update the mdDataTypes
        MdDataTypes updatedMdDataTypes = mdDataTypesRepository.findOne(mdDataTypes.getId());
        // Disconnect from session so that the updates on updatedMdDataTypes are not directly saved in db
        em.detach(updatedMdDataTypes);
        updatedMdDataTypes
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        MdDataTypesDTO mdDataTypesDTO = mdDataTypesMapper.toDto(updatedMdDataTypes);

        restMdDataTypesMockMvc.perform(put("/api/md-data-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDataTypesDTO)))
            .andExpect(status().isOk());

        // Validate the MdDataTypes in the database
        List<MdDataTypes> mdDataTypesList = mdDataTypesRepository.findAll();
        assertThat(mdDataTypesList).hasSize(databaseSizeBeforeUpdate);
        MdDataTypes testMdDataTypes = mdDataTypesList.get(mdDataTypesList.size() - 1);
        assertThat(testMdDataTypes.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMdDataTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMdDataTypes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingMdDataTypes() throws Exception {
        int databaseSizeBeforeUpdate = mdDataTypesRepository.findAll().size();

        // Create the MdDataTypes
        MdDataTypesDTO mdDataTypesDTO = mdDataTypesMapper.toDto(mdDataTypes);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMdDataTypesMockMvc.perform(put("/api/md-data-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDataTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the MdDataTypes in the database
        List<MdDataTypes> mdDataTypesList = mdDataTypesRepository.findAll();
        assertThat(mdDataTypesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMdDataTypes() throws Exception {
        // Initialize the database
        mdDataTypesRepository.saveAndFlush(mdDataTypes);
        int databaseSizeBeforeDelete = mdDataTypesRepository.findAll().size();

        // Get the mdDataTypes
        restMdDataTypesMockMvc.perform(delete("/api/md-data-types/{id}", mdDataTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MdDataTypes> mdDataTypesList = mdDataTypesRepository.findAll();
        assertThat(mdDataTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MdDataTypes.class);
        MdDataTypes mdDataTypes1 = new MdDataTypes();
        mdDataTypes1.setId(1L);
        MdDataTypes mdDataTypes2 = new MdDataTypes();
        mdDataTypes2.setId(mdDataTypes1.getId());
        assertThat(mdDataTypes1).isEqualTo(mdDataTypes2);
        mdDataTypes2.setId(2L);
        assertThat(mdDataTypes1).isNotEqualTo(mdDataTypes2);
        mdDataTypes1.setId(null);
        assertThat(mdDataTypes1).isNotEqualTo(mdDataTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MdDataTypesDTO.class);
        MdDataTypesDTO mdDataTypesDTO1 = new MdDataTypesDTO();
        mdDataTypesDTO1.setId(1L);
        MdDataTypesDTO mdDataTypesDTO2 = new MdDataTypesDTO();
        assertThat(mdDataTypesDTO1).isNotEqualTo(mdDataTypesDTO2);
        mdDataTypesDTO2.setId(mdDataTypesDTO1.getId());
        assertThat(mdDataTypesDTO1).isEqualTo(mdDataTypesDTO2);
        mdDataTypesDTO2.setId(2L);
        assertThat(mdDataTypesDTO1).isNotEqualTo(mdDataTypesDTO2);
        mdDataTypesDTO1.setId(null);
        assertThat(mdDataTypesDTO1).isNotEqualTo(mdDataTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mdDataTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mdDataTypesMapper.fromId(null)).isNull();
    }
}
