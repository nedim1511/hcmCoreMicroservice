package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.DmDocumentTypes;
import ba.infostudio.com.repository.DmDocumentTypesRepository;
import ba.infostudio.com.service.dto.DmDocumentTypesDTO;
import ba.infostudio.com.service.mapper.DmDocumentTypesMapper;
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
 * Test class for the DmDocumentTypesResource REST controller.
 *
 * @see DmDocumentTypesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class DmDocumentTypesResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DmDocumentTypesRepository dmDocumentTypesRepository;

    @Autowired
    private DmDocumentTypesMapper dmDocumentTypesMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDmDocumentTypesMockMvc;

    private DmDocumentTypes dmDocumentTypes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DmDocumentTypesResource dmDocumentTypesResource = new DmDocumentTypesResource(dmDocumentTypesRepository, dmDocumentTypesMapper);
        this.restDmDocumentTypesMockMvc = MockMvcBuilders.standaloneSetup(dmDocumentTypesResource)
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
    public static DmDocumentTypes createEntity(EntityManager em) {
        DmDocumentTypes dmDocumentTypes = new DmDocumentTypes()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return dmDocumentTypes;
    }

    @Before
    public void initTest() {
        dmDocumentTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createDmDocumentTypes() throws Exception {
        int databaseSizeBeforeCreate = dmDocumentTypesRepository.findAll().size();

        // Create the DmDocumentTypes
        DmDocumentTypesDTO dmDocumentTypesDTO = dmDocumentTypesMapper.toDto(dmDocumentTypes);
        restDmDocumentTypesMockMvc.perform(post("/api/dm-document-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dmDocumentTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the DmDocumentTypes in the database
        List<DmDocumentTypes> dmDocumentTypesList = dmDocumentTypesRepository.findAll();
        assertThat(dmDocumentTypesList).hasSize(databaseSizeBeforeCreate + 1);
        DmDocumentTypes testDmDocumentTypes = dmDocumentTypesList.get(dmDocumentTypesList.size() - 1);
        assertThat(testDmDocumentTypes.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDmDocumentTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDmDocumentTypes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createDmDocumentTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dmDocumentTypesRepository.findAll().size();

        // Create the DmDocumentTypes with an existing ID
        dmDocumentTypes.setId(1L);
        DmDocumentTypesDTO dmDocumentTypesDTO = dmDocumentTypesMapper.toDto(dmDocumentTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDmDocumentTypesMockMvc.perform(post("/api/dm-document-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dmDocumentTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DmDocumentTypes in the database
        List<DmDocumentTypes> dmDocumentTypesList = dmDocumentTypesRepository.findAll();
        assertThat(dmDocumentTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dmDocumentTypesRepository.findAll().size();
        // set the field null
        dmDocumentTypes.setCode(null);

        // Create the DmDocumentTypes, which fails.
        DmDocumentTypesDTO dmDocumentTypesDTO = dmDocumentTypesMapper.toDto(dmDocumentTypes);

        restDmDocumentTypesMockMvc.perform(post("/api/dm-document-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dmDocumentTypesDTO)))
            .andExpect(status().isBadRequest());

        List<DmDocumentTypes> dmDocumentTypesList = dmDocumentTypesRepository.findAll();
        assertThat(dmDocumentTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dmDocumentTypesRepository.findAll().size();
        // set the field null
        dmDocumentTypes.setName(null);

        // Create the DmDocumentTypes, which fails.
        DmDocumentTypesDTO dmDocumentTypesDTO = dmDocumentTypesMapper.toDto(dmDocumentTypes);

        restDmDocumentTypesMockMvc.perform(post("/api/dm-document-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dmDocumentTypesDTO)))
            .andExpect(status().isBadRequest());

        List<DmDocumentTypes> dmDocumentTypesList = dmDocumentTypesRepository.findAll();
        assertThat(dmDocumentTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDmDocumentTypes() throws Exception {
        // Initialize the database
        dmDocumentTypesRepository.saveAndFlush(dmDocumentTypes);

        // Get all the dmDocumentTypesList
        restDmDocumentTypesMockMvc.perform(get("/api/dm-document-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dmDocumentTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getDmDocumentTypes() throws Exception {
        // Initialize the database
        dmDocumentTypesRepository.saveAndFlush(dmDocumentTypes);

        // Get the dmDocumentTypes
        restDmDocumentTypesMockMvc.perform(get("/api/dm-document-types/{id}", dmDocumentTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dmDocumentTypes.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDmDocumentTypes() throws Exception {
        // Get the dmDocumentTypes
        restDmDocumentTypesMockMvc.perform(get("/api/dm-document-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDmDocumentTypes() throws Exception {
        // Initialize the database
        dmDocumentTypesRepository.saveAndFlush(dmDocumentTypes);
        int databaseSizeBeforeUpdate = dmDocumentTypesRepository.findAll().size();

        // Update the dmDocumentTypes
        DmDocumentTypes updatedDmDocumentTypes = dmDocumentTypesRepository.findOne(dmDocumentTypes.getId());
        // Disconnect from session so that the updates on updatedDmDocumentTypes are not directly saved in db
        em.detach(updatedDmDocumentTypes);
        updatedDmDocumentTypes
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        DmDocumentTypesDTO dmDocumentTypesDTO = dmDocumentTypesMapper.toDto(updatedDmDocumentTypes);

        restDmDocumentTypesMockMvc.perform(put("/api/dm-document-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dmDocumentTypesDTO)))
            .andExpect(status().isOk());

        // Validate the DmDocumentTypes in the database
        List<DmDocumentTypes> dmDocumentTypesList = dmDocumentTypesRepository.findAll();
        assertThat(dmDocumentTypesList).hasSize(databaseSizeBeforeUpdate);
        DmDocumentTypes testDmDocumentTypes = dmDocumentTypesList.get(dmDocumentTypesList.size() - 1);
        assertThat(testDmDocumentTypes.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDmDocumentTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDmDocumentTypes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingDmDocumentTypes() throws Exception {
        int databaseSizeBeforeUpdate = dmDocumentTypesRepository.findAll().size();

        // Create the DmDocumentTypes
        DmDocumentTypesDTO dmDocumentTypesDTO = dmDocumentTypesMapper.toDto(dmDocumentTypes);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDmDocumentTypesMockMvc.perform(put("/api/dm-document-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dmDocumentTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the DmDocumentTypes in the database
        List<DmDocumentTypes> dmDocumentTypesList = dmDocumentTypesRepository.findAll();
        assertThat(dmDocumentTypesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDmDocumentTypes() throws Exception {
        // Initialize the database
        dmDocumentTypesRepository.saveAndFlush(dmDocumentTypes);
        int databaseSizeBeforeDelete = dmDocumentTypesRepository.findAll().size();

        // Get the dmDocumentTypes
        restDmDocumentTypesMockMvc.perform(delete("/api/dm-document-types/{id}", dmDocumentTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DmDocumentTypes> dmDocumentTypesList = dmDocumentTypesRepository.findAll();
        assertThat(dmDocumentTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DmDocumentTypes.class);
        DmDocumentTypes dmDocumentTypes1 = new DmDocumentTypes();
        dmDocumentTypes1.setId(1L);
        DmDocumentTypes dmDocumentTypes2 = new DmDocumentTypes();
        dmDocumentTypes2.setId(dmDocumentTypes1.getId());
        assertThat(dmDocumentTypes1).isEqualTo(dmDocumentTypes2);
        dmDocumentTypes2.setId(2L);
        assertThat(dmDocumentTypes1).isNotEqualTo(dmDocumentTypes2);
        dmDocumentTypes1.setId(null);
        assertThat(dmDocumentTypes1).isNotEqualTo(dmDocumentTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DmDocumentTypesDTO.class);
        DmDocumentTypesDTO dmDocumentTypesDTO1 = new DmDocumentTypesDTO();
        dmDocumentTypesDTO1.setId(1L);
        DmDocumentTypesDTO dmDocumentTypesDTO2 = new DmDocumentTypesDTO();
        assertThat(dmDocumentTypesDTO1).isNotEqualTo(dmDocumentTypesDTO2);
        dmDocumentTypesDTO2.setId(dmDocumentTypesDTO1.getId());
        assertThat(dmDocumentTypesDTO1).isEqualTo(dmDocumentTypesDTO2);
        dmDocumentTypesDTO2.setId(2L);
        assertThat(dmDocumentTypesDTO1).isNotEqualTo(dmDocumentTypesDTO2);
        dmDocumentTypesDTO1.setId(null);
        assertThat(dmDocumentTypesDTO1).isNotEqualTo(dmDocumentTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dmDocumentTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dmDocumentTypesMapper.fromId(null)).isNull();
    }
}
