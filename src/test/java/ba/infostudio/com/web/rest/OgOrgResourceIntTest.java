package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.OgOrg;
import ba.infostudio.com.repository.OgOrgRepository;
import ba.infostudio.com.service.dto.OgOrgDTO;
import ba.infostudio.com.service.mapper.OgOrgMapper;
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
 * Test class for the OgOrgResource REST controller.
 *
 * @see OgOrgResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class OgOrgResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OgOrgRepository ogOrgRepository;

    @Autowired
    private OgOrgMapper ogOrgMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOgOrgMockMvc;

    private OgOrg ogOrg;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OgOrgResource ogOrgResource = new OgOrgResource(ogOrgRepository, ogOrgMapper);
        this.restOgOrgMockMvc = MockMvcBuilders.standaloneSetup(ogOrgResource)
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
    public static OgOrg createEntity(EntityManager em) {
        OgOrg ogOrg = new OgOrg()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return ogOrg;
    }

    @Before
    public void initTest() {
        ogOrg = createEntity(em);
    }

    @Test
    @Transactional
    public void createOgOrg() throws Exception {
        int databaseSizeBeforeCreate = ogOrgRepository.findAll().size();

        // Create the OgOrg
        OgOrgDTO ogOrgDTO = ogOrgMapper.toDto(ogOrg);
        restOgOrgMockMvc.perform(post("/api/og-orgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogOrgDTO)))
            .andExpect(status().isCreated());

        // Validate the OgOrg in the database
        List<OgOrg> ogOrgList = ogOrgRepository.findAll();
        assertThat(ogOrgList).hasSize(databaseSizeBeforeCreate + 1);
        OgOrg testOgOrg = ogOrgList.get(ogOrgList.size() - 1);
        assertThat(testOgOrg.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOgOrg.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOgOrg.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOgOrgWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ogOrgRepository.findAll().size();

        // Create the OgOrg with an existing ID
        ogOrg.setId(1L);
        OgOrgDTO ogOrgDTO = ogOrgMapper.toDto(ogOrg);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOgOrgMockMvc.perform(post("/api/og-orgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogOrgDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OgOrg in the database
        List<OgOrg> ogOrgList = ogOrgRepository.findAll();
        assertThat(ogOrgList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ogOrgRepository.findAll().size();
        // set the field null
        ogOrg.setCode(null);

        // Create the OgOrg, which fails.
        OgOrgDTO ogOrgDTO = ogOrgMapper.toDto(ogOrg);

        restOgOrgMockMvc.perform(post("/api/og-orgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogOrgDTO)))
            .andExpect(status().isBadRequest());

        List<OgOrg> ogOrgList = ogOrgRepository.findAll();
        assertThat(ogOrgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ogOrgRepository.findAll().size();
        // set the field null
        ogOrg.setName(null);

        // Create the OgOrg, which fails.
        OgOrgDTO ogOrgDTO = ogOrgMapper.toDto(ogOrg);

        restOgOrgMockMvc.perform(post("/api/og-orgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogOrgDTO)))
            .andExpect(status().isBadRequest());

        List<OgOrg> ogOrgList = ogOrgRepository.findAll();
        assertThat(ogOrgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOgOrgs() throws Exception {
        // Initialize the database
        ogOrgRepository.saveAndFlush(ogOrg);

        // Get all the ogOrgList
        restOgOrgMockMvc.perform(get("/api/og-orgs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ogOrg.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getOgOrg() throws Exception {
        // Initialize the database
        ogOrgRepository.saveAndFlush(ogOrg);

        // Get the ogOrg
        restOgOrgMockMvc.perform(get("/api/og-orgs/{id}", ogOrg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ogOrg.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOgOrg() throws Exception {
        // Get the ogOrg
        restOgOrgMockMvc.perform(get("/api/og-orgs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOgOrg() throws Exception {
        // Initialize the database
        ogOrgRepository.saveAndFlush(ogOrg);
        int databaseSizeBeforeUpdate = ogOrgRepository.findAll().size();

        // Update the ogOrg
        OgOrg updatedOgOrg = ogOrgRepository.findOne(ogOrg.getId());
        // Disconnect from session so that the updates on updatedOgOrg are not directly saved in db
        em.detach(updatedOgOrg);
        updatedOgOrg
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        OgOrgDTO ogOrgDTO = ogOrgMapper.toDto(updatedOgOrg);

        restOgOrgMockMvc.perform(put("/api/og-orgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogOrgDTO)))
            .andExpect(status().isOk());

        // Validate the OgOrg in the database
        List<OgOrg> ogOrgList = ogOrgRepository.findAll();
        assertThat(ogOrgList).hasSize(databaseSizeBeforeUpdate);
        OgOrg testOgOrg = ogOrgList.get(ogOrgList.size() - 1);
        assertThat(testOgOrg.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOgOrg.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOgOrg.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOgOrg() throws Exception {
        int databaseSizeBeforeUpdate = ogOrgRepository.findAll().size();

        // Create the OgOrg
        OgOrgDTO ogOrgDTO = ogOrgMapper.toDto(ogOrg);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOgOrgMockMvc.perform(put("/api/og-orgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ogOrgDTO)))
            .andExpect(status().isCreated());

        // Validate the OgOrg in the database
        List<OgOrg> ogOrgList = ogOrgRepository.findAll();
        assertThat(ogOrgList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOgOrg() throws Exception {
        // Initialize the database
        ogOrgRepository.saveAndFlush(ogOrg);
        int databaseSizeBeforeDelete = ogOrgRepository.findAll().size();

        // Get the ogOrg
        restOgOrgMockMvc.perform(delete("/api/og-orgs/{id}", ogOrg.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OgOrg> ogOrgList = ogOrgRepository.findAll();
        assertThat(ogOrgList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OgOrg.class);
        OgOrg ogOrg1 = new OgOrg();
        ogOrg1.setId(1L);
        OgOrg ogOrg2 = new OgOrg();
        ogOrg2.setId(ogOrg1.getId());
        assertThat(ogOrg1).isEqualTo(ogOrg2);
        ogOrg2.setId(2L);
        assertThat(ogOrg1).isNotEqualTo(ogOrg2);
        ogOrg1.setId(null);
        assertThat(ogOrg1).isNotEqualTo(ogOrg2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OgOrgDTO.class);
        OgOrgDTO ogOrgDTO1 = new OgOrgDTO();
        ogOrgDTO1.setId(1L);
        OgOrgDTO ogOrgDTO2 = new OgOrgDTO();
        assertThat(ogOrgDTO1).isNotEqualTo(ogOrgDTO2);
        ogOrgDTO2.setId(ogOrgDTO1.getId());
        assertThat(ogOrgDTO1).isEqualTo(ogOrgDTO2);
        ogOrgDTO2.setId(2L);
        assertThat(ogOrgDTO1).isNotEqualTo(ogOrgDTO2);
        ogOrgDTO1.setId(null);
        assertThat(ogOrgDTO1).isNotEqualTo(ogOrgDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ogOrgMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ogOrgMapper.fromId(null)).isNull();
    }
}
