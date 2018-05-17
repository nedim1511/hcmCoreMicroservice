package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.OgWorkPlaceTypes;
import ba.infostudio.com.repository.OgWorkPlaceTypesRepository;
import ba.infostudio.com.service.dto.OgWorkPlaceTypesDTO;
import ba.infostudio.com.service.mapper.OgWorkPlaceTypesMapper;
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
 * Test class for the OgWorkPlaceTypesResource REST controller.
 *
 * @see OgWorkPlaceTypesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class OgWorkPlaceTypesResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OgWorkPlaceTypesRepository ogWorkPlaceTypesRepository;

    @Autowired
    private OgWorkPlaceTypesMapper ogWorkPlaceTypesMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOgWorkPlaceTypesMockMvc;

    private OgWorkPlaceTypes ogWorkPlaceTypes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OgWorkPlaceTypesResource ogWorkPlaceTypesResource = new OgWorkPlaceTypesResource(ogWorkPlaceTypesRepository, ogWorkPlaceTypesMapper);
        this.restOgWorkPlaceTypesMockMvc = MockMvcBuilders.standaloneSetup(ogWorkPlaceTypesResource)
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
    public static OgWorkPlaceTypes createEntity(EntityManager em) {
        OgWorkPlaceTypes ogWorkPlaceTypes = new OgWorkPlaceTypes()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return ogWorkPlaceTypes;
    }

    @Before
    public void initTest() {
        ogWorkPlaceTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createOgWorkPlaceTypes() throws Exception {
        int databaseSizeBeforeCreate = ogWorkPlaceTypesRepository.findAll().size();

        // Create the OgWorkPlaceTypes
        OgWorkPlaceTypesDTO ogWorkPlaceTypesDTO = ogWorkPlaceTypesMapper.toDto(ogWorkPlaceTypes);
        restOgWorkPlaceTypesMockMvc.perform(post("/api/og-work-place-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogWorkPlaceTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the OgWorkPlaceTypes in the database
        List<OgWorkPlaceTypes> ogWorkPlaceTypesList = ogWorkPlaceTypesRepository.findAll();
        assertThat(ogWorkPlaceTypesList).hasSize(databaseSizeBeforeCreate + 1);
        OgWorkPlaceTypes testOgWorkPlaceTypes = ogWorkPlaceTypesList.get(ogWorkPlaceTypesList.size() - 1);
        assertThat(testOgWorkPlaceTypes.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOgWorkPlaceTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOgWorkPlaceTypes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOgWorkPlaceTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ogWorkPlaceTypesRepository.findAll().size();

        // Create the OgWorkPlaceTypes with an existing ID
        ogWorkPlaceTypes.setId(1L);
        OgWorkPlaceTypesDTO ogWorkPlaceTypesDTO = ogWorkPlaceTypesMapper.toDto(ogWorkPlaceTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOgWorkPlaceTypesMockMvc.perform(post("/api/og-work-place-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogWorkPlaceTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OgWorkPlaceTypes in the database
        List<OgWorkPlaceTypes> ogWorkPlaceTypesList = ogWorkPlaceTypesRepository.findAll();
        assertThat(ogWorkPlaceTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ogWorkPlaceTypesRepository.findAll().size();
        // set the field null
        ogWorkPlaceTypes.setCode(null);

        // Create the OgWorkPlaceTypes, which fails.
        OgWorkPlaceTypesDTO ogWorkPlaceTypesDTO = ogWorkPlaceTypesMapper.toDto(ogWorkPlaceTypes);

        restOgWorkPlaceTypesMockMvc.perform(post("/api/og-work-place-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogWorkPlaceTypesDTO)))
            .andExpect(status().isBadRequest());

        List<OgWorkPlaceTypes> ogWorkPlaceTypesList = ogWorkPlaceTypesRepository.findAll();
        assertThat(ogWorkPlaceTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ogWorkPlaceTypesRepository.findAll().size();
        // set the field null
        ogWorkPlaceTypes.setName(null);

        // Create the OgWorkPlaceTypes, which fails.
        OgWorkPlaceTypesDTO ogWorkPlaceTypesDTO = ogWorkPlaceTypesMapper.toDto(ogWorkPlaceTypes);

        restOgWorkPlaceTypesMockMvc.perform(post("/api/og-work-place-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogWorkPlaceTypesDTO)))
            .andExpect(status().isBadRequest());

        List<OgWorkPlaceTypes> ogWorkPlaceTypesList = ogWorkPlaceTypesRepository.findAll();
        assertThat(ogWorkPlaceTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOgWorkPlaceTypes() throws Exception {
        // Initialize the database
        ogWorkPlaceTypesRepository.saveAndFlush(ogWorkPlaceTypes);

        // Get all the ogWorkPlaceTypesList
        restOgWorkPlaceTypesMockMvc.perform(get("/api/og-work-place-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ogWorkPlaceTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getOgWorkPlaceTypes() throws Exception {
        // Initialize the database
        ogWorkPlaceTypesRepository.saveAndFlush(ogWorkPlaceTypes);

        // Get the ogWorkPlaceTypes
        restOgWorkPlaceTypesMockMvc.perform(get("/api/og-work-place-types/{id}", ogWorkPlaceTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ogWorkPlaceTypes.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOgWorkPlaceTypes() throws Exception {
        // Get the ogWorkPlaceTypes
        restOgWorkPlaceTypesMockMvc.perform(get("/api/og-work-place-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOgWorkPlaceTypes() throws Exception {
        // Initialize the database
        ogWorkPlaceTypesRepository.saveAndFlush(ogWorkPlaceTypes);
        int databaseSizeBeforeUpdate = ogWorkPlaceTypesRepository.findAll().size();

        // Update the ogWorkPlaceTypes
        OgWorkPlaceTypes updatedOgWorkPlaceTypes = ogWorkPlaceTypesRepository.findOne(ogWorkPlaceTypes.getId());
        // Disconnect from session so that the updates on updatedOgWorkPlaceTypes are not directly saved in db
        em.detach(updatedOgWorkPlaceTypes);
        updatedOgWorkPlaceTypes
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        OgWorkPlaceTypesDTO ogWorkPlaceTypesDTO = ogWorkPlaceTypesMapper.toDto(updatedOgWorkPlaceTypes);

        restOgWorkPlaceTypesMockMvc.perform(put("/api/og-work-place-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogWorkPlaceTypesDTO)))
            .andExpect(status().isOk());

        // Validate the OgWorkPlaceTypes in the database
        List<OgWorkPlaceTypes> ogWorkPlaceTypesList = ogWorkPlaceTypesRepository.findAll();
        assertThat(ogWorkPlaceTypesList).hasSize(databaseSizeBeforeUpdate);
        OgWorkPlaceTypes testOgWorkPlaceTypes = ogWorkPlaceTypesList.get(ogWorkPlaceTypesList.size() - 1);
        assertThat(testOgWorkPlaceTypes.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOgWorkPlaceTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOgWorkPlaceTypes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOgWorkPlaceTypes() throws Exception {
        int databaseSizeBeforeUpdate = ogWorkPlaceTypesRepository.findAll().size();

        // Create the OgWorkPlaceTypes
        OgWorkPlaceTypesDTO ogWorkPlaceTypesDTO = ogWorkPlaceTypesMapper.toDto(ogWorkPlaceTypes);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOgWorkPlaceTypesMockMvc.perform(put("/api/og-work-place-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogWorkPlaceTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the OgWorkPlaceTypes in the database
        List<OgWorkPlaceTypes> ogWorkPlaceTypesList = ogWorkPlaceTypesRepository.findAll();
        assertThat(ogWorkPlaceTypesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOgWorkPlaceTypes() throws Exception {
        // Initialize the database
        ogWorkPlaceTypesRepository.saveAndFlush(ogWorkPlaceTypes);
        int databaseSizeBeforeDelete = ogWorkPlaceTypesRepository.findAll().size();

        // Get the ogWorkPlaceTypes
        restOgWorkPlaceTypesMockMvc.perform(delete("/api/og-work-place-types/{id}", ogWorkPlaceTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OgWorkPlaceTypes> ogWorkPlaceTypesList = ogWorkPlaceTypesRepository.findAll();
        assertThat(ogWorkPlaceTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OgWorkPlaceTypes.class);
        OgWorkPlaceTypes ogWorkPlaceTypes1 = new OgWorkPlaceTypes();
        ogWorkPlaceTypes1.setId(1L);
        OgWorkPlaceTypes ogWorkPlaceTypes2 = new OgWorkPlaceTypes();
        ogWorkPlaceTypes2.setId(ogWorkPlaceTypes1.getId());
        assertThat(ogWorkPlaceTypes1).isEqualTo(ogWorkPlaceTypes2);
        ogWorkPlaceTypes2.setId(2L);
        assertThat(ogWorkPlaceTypes1).isNotEqualTo(ogWorkPlaceTypes2);
        ogWorkPlaceTypes1.setId(null);
        assertThat(ogWorkPlaceTypes1).isNotEqualTo(ogWorkPlaceTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OgWorkPlaceTypesDTO.class);
        OgWorkPlaceTypesDTO ogWorkPlaceTypesDTO1 = new OgWorkPlaceTypesDTO();
        ogWorkPlaceTypesDTO1.setId(1L);
        OgWorkPlaceTypesDTO ogWorkPlaceTypesDTO2 = new OgWorkPlaceTypesDTO();
        assertThat(ogWorkPlaceTypesDTO1).isNotEqualTo(ogWorkPlaceTypesDTO2);
        ogWorkPlaceTypesDTO2.setId(ogWorkPlaceTypesDTO1.getId());
        assertThat(ogWorkPlaceTypesDTO1).isEqualTo(ogWorkPlaceTypesDTO2);
        ogWorkPlaceTypesDTO2.setId(2L);
        assertThat(ogWorkPlaceTypesDTO1).isNotEqualTo(ogWorkPlaceTypesDTO2);
        ogWorkPlaceTypesDTO1.setId(null);
        assertThat(ogWorkPlaceTypesDTO1).isNotEqualTo(ogWorkPlaceTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ogWorkPlaceTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ogWorkPlaceTypesMapper.fromId(null)).isNull();
    }
}
