package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.OgWorkPlaces;
import ba.infostudio.com.repository.OgWorkPlacesRepository;
import ba.infostudio.com.service.dto.OgWorkPlacesDTO;
import ba.infostudio.com.service.mapper.OgWorkPlacesMapper;
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
 * Test class for the OgWorkPlacesResource REST controller.
 *
 * @see OgWorkPlacesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class OgWorkPlacesResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OgWorkPlacesRepository ogWorkPlacesRepository;

    @Autowired
    private OgWorkPlacesMapper ogWorkPlacesMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOgWorkPlacesMockMvc;

    private OgWorkPlaces ogWorkPlaces;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OgWorkPlacesResource ogWorkPlacesResource = new OgWorkPlacesResource(ogWorkPlacesRepository, ogWorkPlacesMapper);
        this.restOgWorkPlacesMockMvc = MockMvcBuilders.standaloneSetup(ogWorkPlacesResource)
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
    public static OgWorkPlaces createEntity(EntityManager em) {
        OgWorkPlaces ogWorkPlaces = new OgWorkPlaces()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return ogWorkPlaces;
    }

    @Before
    public void initTest() {
        ogWorkPlaces = createEntity(em);
    }

    @Test
    @Transactional
    public void createOgWorkPlaces() throws Exception {
        int databaseSizeBeforeCreate = ogWorkPlacesRepository.findAll().size();

        // Create the OgWorkPlaces
        OgWorkPlacesDTO ogWorkPlacesDTO = ogWorkPlacesMapper.toDto(ogWorkPlaces);
        restOgWorkPlacesMockMvc.perform(post("/api/og-work-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogWorkPlacesDTO)))
            .andExpect(status().isCreated());

        // Validate the OgWorkPlaces in the database
        List<OgWorkPlaces> ogWorkPlacesList = ogWorkPlacesRepository.findAll();
        assertThat(ogWorkPlacesList).hasSize(databaseSizeBeforeCreate + 1);
        OgWorkPlaces testOgWorkPlaces = ogWorkPlacesList.get(ogWorkPlacesList.size() - 1);
        assertThat(testOgWorkPlaces.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOgWorkPlaces.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOgWorkPlaces.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOgWorkPlacesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ogWorkPlacesRepository.findAll().size();

        // Create the OgWorkPlaces with an existing ID
        ogWorkPlaces.setId(1L);
        OgWorkPlacesDTO ogWorkPlacesDTO = ogWorkPlacesMapper.toDto(ogWorkPlaces);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOgWorkPlacesMockMvc.perform(post("/api/og-work-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogWorkPlacesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OgWorkPlaces in the database
        List<OgWorkPlaces> ogWorkPlacesList = ogWorkPlacesRepository.findAll();
        assertThat(ogWorkPlacesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ogWorkPlacesRepository.findAll().size();
        // set the field null
        ogWorkPlaces.setCode(null);

        // Create the OgWorkPlaces, which fails.
        OgWorkPlacesDTO ogWorkPlacesDTO = ogWorkPlacesMapper.toDto(ogWorkPlaces);

        restOgWorkPlacesMockMvc.perform(post("/api/og-work-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogWorkPlacesDTO)))
            .andExpect(status().isBadRequest());

        List<OgWorkPlaces> ogWorkPlacesList = ogWorkPlacesRepository.findAll();
        assertThat(ogWorkPlacesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ogWorkPlacesRepository.findAll().size();
        // set the field null
        ogWorkPlaces.setName(null);

        // Create the OgWorkPlaces, which fails.
        OgWorkPlacesDTO ogWorkPlacesDTO = ogWorkPlacesMapper.toDto(ogWorkPlaces);

        restOgWorkPlacesMockMvc.perform(post("/api/og-work-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogWorkPlacesDTO)))
            .andExpect(status().isBadRequest());

        List<OgWorkPlaces> ogWorkPlacesList = ogWorkPlacesRepository.findAll();
        assertThat(ogWorkPlacesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOgWorkPlaces() throws Exception {
        // Initialize the database
        ogWorkPlacesRepository.saveAndFlush(ogWorkPlaces);

        // Get all the ogWorkPlacesList
        restOgWorkPlacesMockMvc.perform(get("/api/og-work-places?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ogWorkPlaces.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getOgWorkPlaces() throws Exception {
        // Initialize the database
        ogWorkPlacesRepository.saveAndFlush(ogWorkPlaces);

        // Get the ogWorkPlaces
        restOgWorkPlacesMockMvc.perform(get("/api/og-work-places/{id}", ogWorkPlaces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ogWorkPlaces.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOgWorkPlaces() throws Exception {
        // Get the ogWorkPlaces
        restOgWorkPlacesMockMvc.perform(get("/api/og-work-places/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOgWorkPlaces() throws Exception {
        // Initialize the database
        ogWorkPlacesRepository.saveAndFlush(ogWorkPlaces);
        int databaseSizeBeforeUpdate = ogWorkPlacesRepository.findAll().size();

        // Update the ogWorkPlaces
        OgWorkPlaces updatedOgWorkPlaces = ogWorkPlacesRepository.findOne(ogWorkPlaces.getId());
        // Disconnect from session so that the updates on updatedOgWorkPlaces are not directly saved in db
        em.detach(updatedOgWorkPlaces);
        updatedOgWorkPlaces
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        OgWorkPlacesDTO ogWorkPlacesDTO = ogWorkPlacesMapper.toDto(updatedOgWorkPlaces);

        restOgWorkPlacesMockMvc.perform(put("/api/og-work-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogWorkPlacesDTO)))
            .andExpect(status().isOk());

        // Validate the OgWorkPlaces in the database
        List<OgWorkPlaces> ogWorkPlacesList = ogWorkPlacesRepository.findAll();
        assertThat(ogWorkPlacesList).hasSize(databaseSizeBeforeUpdate);
        OgWorkPlaces testOgWorkPlaces = ogWorkPlacesList.get(ogWorkPlacesList.size() - 1);
        assertThat(testOgWorkPlaces.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOgWorkPlaces.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOgWorkPlaces.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOgWorkPlaces() throws Exception {
        int databaseSizeBeforeUpdate = ogWorkPlacesRepository.findAll().size();

        // Create the OgWorkPlaces
        OgWorkPlacesDTO ogWorkPlacesDTO = ogWorkPlacesMapper.toDto(ogWorkPlaces);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOgWorkPlacesMockMvc.perform(put("/api/og-work-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogWorkPlacesDTO)))
            .andExpect(status().isCreated());

        // Validate the OgWorkPlaces in the database
        List<OgWorkPlaces> ogWorkPlacesList = ogWorkPlacesRepository.findAll();
        assertThat(ogWorkPlacesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOgWorkPlaces() throws Exception {
        // Initialize the database
        ogWorkPlacesRepository.saveAndFlush(ogWorkPlaces);
        int databaseSizeBeforeDelete = ogWorkPlacesRepository.findAll().size();

        // Get the ogWorkPlaces
        restOgWorkPlacesMockMvc.perform(delete("/api/og-work-places/{id}", ogWorkPlaces.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OgWorkPlaces> ogWorkPlacesList = ogWorkPlacesRepository.findAll();
        assertThat(ogWorkPlacesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OgWorkPlaces.class);
        OgWorkPlaces ogWorkPlaces1 = new OgWorkPlaces();
        ogWorkPlaces1.setId(1L);
        OgWorkPlaces ogWorkPlaces2 = new OgWorkPlaces();
        ogWorkPlaces2.setId(ogWorkPlaces1.getId());
        assertThat(ogWorkPlaces1).isEqualTo(ogWorkPlaces2);
        ogWorkPlaces2.setId(2L);
        assertThat(ogWorkPlaces1).isNotEqualTo(ogWorkPlaces2);
        ogWorkPlaces1.setId(null);
        assertThat(ogWorkPlaces1).isNotEqualTo(ogWorkPlaces2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OgWorkPlacesDTO.class);
        OgWorkPlacesDTO ogWorkPlacesDTO1 = new OgWorkPlacesDTO();
        ogWorkPlacesDTO1.setId(1L);
        OgWorkPlacesDTO ogWorkPlacesDTO2 = new OgWorkPlacesDTO();
        assertThat(ogWorkPlacesDTO1).isNotEqualTo(ogWorkPlacesDTO2);
        ogWorkPlacesDTO2.setId(ogWorkPlacesDTO1.getId());
        assertThat(ogWorkPlacesDTO1).isEqualTo(ogWorkPlacesDTO2);
        ogWorkPlacesDTO2.setId(2L);
        assertThat(ogWorkPlacesDTO1).isNotEqualTo(ogWorkPlacesDTO2);
        ogWorkPlacesDTO1.setId(null);
        assertThat(ogWorkPlacesDTO1).isNotEqualTo(ogWorkPlacesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ogWorkPlacesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ogWorkPlacesMapper.fromId(null)).isNull();
    }
}
