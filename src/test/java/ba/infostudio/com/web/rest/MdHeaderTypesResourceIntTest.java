package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.MdHeaderTypes;
import ba.infostudio.com.repository.MdHeaderTypesRepository;
import ba.infostudio.com.service.dto.MdHeaderTypesDTO;
import ba.infostudio.com.service.mapper.MdHeaderTypesMapper;
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
 * Test class for the MdHeaderTypesResource REST controller.
 *
 * @see MdHeaderTypesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class MdHeaderTypesResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MODUL = "AAAAAAAAAA";
    private static final String UPDATED_MODUL = "BBBBBBBBBB";

    @Autowired
    private MdHeaderTypesRepository mdHeaderTypesRepository;

    @Autowired
    private MdHeaderTypesMapper mdHeaderTypesMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMdHeaderTypesMockMvc;

    private MdHeaderTypes mdHeaderTypes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MdHeaderTypesResource mdHeaderTypesResource = new MdHeaderTypesResource(mdHeaderTypesRepository, mdHeaderTypesMapper);
        this.restMdHeaderTypesMockMvc = MockMvcBuilders.standaloneSetup(mdHeaderTypesResource)
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
    public static MdHeaderTypes createEntity(EntityManager em) {
        MdHeaderTypes mdHeaderTypes = new MdHeaderTypes()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .modul(DEFAULT_MODUL);
        return mdHeaderTypes;
    }

    @Before
    public void initTest() {
        mdHeaderTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createMdHeaderTypes() throws Exception {
        int databaseSizeBeforeCreate = mdHeaderTypesRepository.findAll().size();

        // Create the MdHeaderTypes
        MdHeaderTypesDTO mdHeaderTypesDTO = mdHeaderTypesMapper.toDto(mdHeaderTypes);
        restMdHeaderTypesMockMvc.perform(post("/api/md-header-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdHeaderTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the MdHeaderTypes in the database
        List<MdHeaderTypes> mdHeaderTypesList = mdHeaderTypesRepository.findAll();
        assertThat(mdHeaderTypesList).hasSize(databaseSizeBeforeCreate + 1);
        MdHeaderTypes testMdHeaderTypes = mdHeaderTypesList.get(mdHeaderTypesList.size() - 1);
        assertThat(testMdHeaderTypes.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMdHeaderTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMdHeaderTypes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMdHeaderTypes.getModul()).isEqualTo(DEFAULT_MODUL);
    }

    @Test
    @Transactional
    public void createMdHeaderTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mdHeaderTypesRepository.findAll().size();

        // Create the MdHeaderTypes with an existing ID
        mdHeaderTypes.setId(1L);
        MdHeaderTypesDTO mdHeaderTypesDTO = mdHeaderTypesMapper.toDto(mdHeaderTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMdHeaderTypesMockMvc.perform(post("/api/md-header-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdHeaderTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MdHeaderTypes in the database
        List<MdHeaderTypes> mdHeaderTypesList = mdHeaderTypesRepository.findAll();
        assertThat(mdHeaderTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mdHeaderTypesRepository.findAll().size();
        // set the field null
        mdHeaderTypes.setCode(null);

        // Create the MdHeaderTypes, which fails.
        MdHeaderTypesDTO mdHeaderTypesDTO = mdHeaderTypesMapper.toDto(mdHeaderTypes);

        restMdHeaderTypesMockMvc.perform(post("/api/md-header-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdHeaderTypesDTO)))
            .andExpect(status().isBadRequest());

        List<MdHeaderTypes> mdHeaderTypesList = mdHeaderTypesRepository.findAll();
        assertThat(mdHeaderTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mdHeaderTypesRepository.findAll().size();
        // set the field null
        mdHeaderTypes.setName(null);

        // Create the MdHeaderTypes, which fails.
        MdHeaderTypesDTO mdHeaderTypesDTO = mdHeaderTypesMapper.toDto(mdHeaderTypes);

        restMdHeaderTypesMockMvc.perform(post("/api/md-header-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdHeaderTypesDTO)))
            .andExpect(status().isBadRequest());

        List<MdHeaderTypes> mdHeaderTypesList = mdHeaderTypesRepository.findAll();
        assertThat(mdHeaderTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMdHeaderTypes() throws Exception {
        // Initialize the database
        mdHeaderTypesRepository.saveAndFlush(mdHeaderTypes);

        // Get all the mdHeaderTypesList
        restMdHeaderTypesMockMvc.perform(get("/api/md-header-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mdHeaderTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].modul").value(hasItem(DEFAULT_MODUL.toString())));
    }

    @Test
    @Transactional
    public void getMdHeaderTypes() throws Exception {
        // Initialize the database
        mdHeaderTypesRepository.saveAndFlush(mdHeaderTypes);

        // Get the mdHeaderTypes
        restMdHeaderTypesMockMvc.perform(get("/api/md-header-types/{id}", mdHeaderTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mdHeaderTypes.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.modul").value(DEFAULT_MODUL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMdHeaderTypes() throws Exception {
        // Get the mdHeaderTypes
        restMdHeaderTypesMockMvc.perform(get("/api/md-header-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMdHeaderTypes() throws Exception {
        // Initialize the database
        mdHeaderTypesRepository.saveAndFlush(mdHeaderTypes);
        int databaseSizeBeforeUpdate = mdHeaderTypesRepository.findAll().size();

        // Update the mdHeaderTypes
        MdHeaderTypes updatedMdHeaderTypes = mdHeaderTypesRepository.findOne(mdHeaderTypes.getId());
        // Disconnect from session so that the updates on updatedMdHeaderTypes are not directly saved in db
        em.detach(updatedMdHeaderTypes);
        updatedMdHeaderTypes
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .modul(UPDATED_MODUL);
        MdHeaderTypesDTO mdHeaderTypesDTO = mdHeaderTypesMapper.toDto(updatedMdHeaderTypes);

        restMdHeaderTypesMockMvc.perform(put("/api/md-header-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdHeaderTypesDTO)))
            .andExpect(status().isOk());

        // Validate the MdHeaderTypes in the database
        List<MdHeaderTypes> mdHeaderTypesList = mdHeaderTypesRepository.findAll();
        assertThat(mdHeaderTypesList).hasSize(databaseSizeBeforeUpdate);
        MdHeaderTypes testMdHeaderTypes = mdHeaderTypesList.get(mdHeaderTypesList.size() - 1);
        assertThat(testMdHeaderTypes.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMdHeaderTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMdHeaderTypes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMdHeaderTypes.getModul()).isEqualTo(UPDATED_MODUL);
    }

    @Test
    @Transactional
    public void updateNonExistingMdHeaderTypes() throws Exception {
        int databaseSizeBeforeUpdate = mdHeaderTypesRepository.findAll().size();

        // Create the MdHeaderTypes
        MdHeaderTypesDTO mdHeaderTypesDTO = mdHeaderTypesMapper.toDto(mdHeaderTypes);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMdHeaderTypesMockMvc.perform(put("/api/md-header-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdHeaderTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the MdHeaderTypes in the database
        List<MdHeaderTypes> mdHeaderTypesList = mdHeaderTypesRepository.findAll();
        assertThat(mdHeaderTypesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMdHeaderTypes() throws Exception {
        // Initialize the database
        mdHeaderTypesRepository.saveAndFlush(mdHeaderTypes);
        int databaseSizeBeforeDelete = mdHeaderTypesRepository.findAll().size();

        // Get the mdHeaderTypes
        restMdHeaderTypesMockMvc.perform(delete("/api/md-header-types/{id}", mdHeaderTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MdHeaderTypes> mdHeaderTypesList = mdHeaderTypesRepository.findAll();
        assertThat(mdHeaderTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MdHeaderTypes.class);
        MdHeaderTypes mdHeaderTypes1 = new MdHeaderTypes();
        mdHeaderTypes1.setId(1L);
        MdHeaderTypes mdHeaderTypes2 = new MdHeaderTypes();
        mdHeaderTypes2.setId(mdHeaderTypes1.getId());
        assertThat(mdHeaderTypes1).isEqualTo(mdHeaderTypes2);
        mdHeaderTypes2.setId(2L);
        assertThat(mdHeaderTypes1).isNotEqualTo(mdHeaderTypes2);
        mdHeaderTypes1.setId(null);
        assertThat(mdHeaderTypes1).isNotEqualTo(mdHeaderTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MdHeaderTypesDTO.class);
        MdHeaderTypesDTO mdHeaderTypesDTO1 = new MdHeaderTypesDTO();
        mdHeaderTypesDTO1.setId(1L);
        MdHeaderTypesDTO mdHeaderTypesDTO2 = new MdHeaderTypesDTO();
        assertThat(mdHeaderTypesDTO1).isNotEqualTo(mdHeaderTypesDTO2);
        mdHeaderTypesDTO2.setId(mdHeaderTypesDTO1.getId());
        assertThat(mdHeaderTypesDTO1).isEqualTo(mdHeaderTypesDTO2);
        mdHeaderTypesDTO2.setId(2L);
        assertThat(mdHeaderTypesDTO1).isNotEqualTo(mdHeaderTypesDTO2);
        mdHeaderTypesDTO1.setId(null);
        assertThat(mdHeaderTypesDTO1).isNotEqualTo(mdHeaderTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mdHeaderTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mdHeaderTypesMapper.fromId(null)).isNull();
    }
}
