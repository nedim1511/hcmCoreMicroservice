package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.MdDetails;
import ba.infostudio.com.repository.MdDetailsRepository;
import ba.infostudio.com.service.dto.MdDetailsDTO;
import ba.infostudio.com.service.mapper.MdDetailsMapper;
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
 * Test class for the MdDetailsResource REST controller.
 *
 * @see MdDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class MdDetailsResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MANDATORY = "AAAAAAAAAA";
    private static final String UPDATED_MANDATORY = "BBBBBBBBBB";

    @Autowired
    private MdDetailsRepository mdDetailsRepository;

    @Autowired
    private MdDetailsMapper mdDetailsMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMdDetailsMockMvc;

    private MdDetails mdDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MdDetailsResource mdDetailsResource = new MdDetailsResource(mdDetailsRepository, mdDetailsMapper);
        this.restMdDetailsMockMvc = MockMvcBuilders.standaloneSetup(mdDetailsResource)
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
    public static MdDetails createEntity(EntityManager em) {
        MdDetails mdDetails = new MdDetails()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .mandatory(DEFAULT_MANDATORY);
        return mdDetails;
    }

    @Before
    public void initTest() {
        mdDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createMdDetails() throws Exception {
        int databaseSizeBeforeCreate = mdDetailsRepository.findAll().size();

        // Create the MdDetails
        MdDetailsDTO mdDetailsDTO = mdDetailsMapper.toDto(mdDetails);
        restMdDetailsMockMvc.perform(post("/api/md-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the MdDetails in the database
        List<MdDetails> mdDetailsList = mdDetailsRepository.findAll();
        assertThat(mdDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        MdDetails testMdDetails = mdDetailsList.get(mdDetailsList.size() - 1);
        assertThat(testMdDetails.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMdDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMdDetails.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMdDetails.getMandatory()).isEqualTo(DEFAULT_MANDATORY);
    }

    @Test
    @Transactional
    public void createMdDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mdDetailsRepository.findAll().size();

        // Create the MdDetails with an existing ID
        mdDetails.setId(1L);
        MdDetailsDTO mdDetailsDTO = mdDetailsMapper.toDto(mdDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMdDetailsMockMvc.perform(post("/api/md-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MdDetails in the database
        List<MdDetails> mdDetailsList = mdDetailsRepository.findAll();
        assertThat(mdDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mdDetailsRepository.findAll().size();
        // set the field null
        mdDetails.setCode(null);

        // Create the MdDetails, which fails.
        MdDetailsDTO mdDetailsDTO = mdDetailsMapper.toDto(mdDetails);

        restMdDetailsMockMvc.perform(post("/api/md-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<MdDetails> mdDetailsList = mdDetailsRepository.findAll();
        assertThat(mdDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mdDetailsRepository.findAll().size();
        // set the field null
        mdDetails.setName(null);

        // Create the MdDetails, which fails.
        MdDetailsDTO mdDetailsDTO = mdDetailsMapper.toDto(mdDetails);

        restMdDetailsMockMvc.perform(post("/api/md-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<MdDetails> mdDetailsList = mdDetailsRepository.findAll();
        assertThat(mdDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMdDetails() throws Exception {
        // Initialize the database
        mdDetailsRepository.saveAndFlush(mdDetails);

        // Get all the mdDetailsList
        restMdDetailsMockMvc.perform(get("/api/md-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mdDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY.toString())));
    }

    @Test
    @Transactional
    public void getMdDetails() throws Exception {
        // Initialize the database
        mdDetailsRepository.saveAndFlush(mdDetails);

        // Get the mdDetails
        restMdDetailsMockMvc.perform(get("/api/md-details/{id}", mdDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mdDetails.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.mandatory").value(DEFAULT_MANDATORY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMdDetails() throws Exception {
        // Get the mdDetails
        restMdDetailsMockMvc.perform(get("/api/md-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMdDetails() throws Exception {
        // Initialize the database
        mdDetailsRepository.saveAndFlush(mdDetails);
        int databaseSizeBeforeUpdate = mdDetailsRepository.findAll().size();

        // Update the mdDetails
        MdDetails updatedMdDetails = mdDetailsRepository.findOne(mdDetails.getId());
        // Disconnect from session so that the updates on updatedMdDetails are not directly saved in db
        em.detach(updatedMdDetails);
        updatedMdDetails
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .mandatory(UPDATED_MANDATORY);
        MdDetailsDTO mdDetailsDTO = mdDetailsMapper.toDto(updatedMdDetails);

        restMdDetailsMockMvc.perform(put("/api/md-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the MdDetails in the database
        List<MdDetails> mdDetailsList = mdDetailsRepository.findAll();
        assertThat(mdDetailsList).hasSize(databaseSizeBeforeUpdate);
        MdDetails testMdDetails = mdDetailsList.get(mdDetailsList.size() - 1);
        assertThat(testMdDetails.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMdDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMdDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMdDetails.getMandatory()).isEqualTo(UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void updateNonExistingMdDetails() throws Exception {
        int databaseSizeBeforeUpdate = mdDetailsRepository.findAll().size();

        // Create the MdDetails
        MdDetailsDTO mdDetailsDTO = mdDetailsMapper.toDto(mdDetails);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMdDetailsMockMvc.perform(put("/api/md-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mdDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the MdDetails in the database
        List<MdDetails> mdDetailsList = mdDetailsRepository.findAll();
        assertThat(mdDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMdDetails() throws Exception {
        // Initialize the database
        mdDetailsRepository.saveAndFlush(mdDetails);
        int databaseSizeBeforeDelete = mdDetailsRepository.findAll().size();

        // Get the mdDetails
        restMdDetailsMockMvc.perform(delete("/api/md-details/{id}", mdDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MdDetails> mdDetailsList = mdDetailsRepository.findAll();
        assertThat(mdDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MdDetails.class);
        MdDetails mdDetails1 = new MdDetails();
        mdDetails1.setId(1L);
        MdDetails mdDetails2 = new MdDetails();
        mdDetails2.setId(mdDetails1.getId());
        assertThat(mdDetails1).isEqualTo(mdDetails2);
        mdDetails2.setId(2L);
        assertThat(mdDetails1).isNotEqualTo(mdDetails2);
        mdDetails1.setId(null);
        assertThat(mdDetails1).isNotEqualTo(mdDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MdDetailsDTO.class);
        MdDetailsDTO mdDetailsDTO1 = new MdDetailsDTO();
        mdDetailsDTO1.setId(1L);
        MdDetailsDTO mdDetailsDTO2 = new MdDetailsDTO();
        assertThat(mdDetailsDTO1).isNotEqualTo(mdDetailsDTO2);
        mdDetailsDTO2.setId(mdDetailsDTO1.getId());
        assertThat(mdDetailsDTO1).isEqualTo(mdDetailsDTO2);
        mdDetailsDTO2.setId(2L);
        assertThat(mdDetailsDTO1).isNotEqualTo(mdDetailsDTO2);
        mdDetailsDTO1.setId(null);
        assertThat(mdDetailsDTO1).isNotEqualTo(mdDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mdDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mdDetailsMapper.fromId(null)).isNull();
    }
}
