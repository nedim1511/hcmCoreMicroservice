package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.CoAnnouncements;
import ba.infostudio.com.repository.CoAnnouncementsRepository;
import ba.infostudio.com.service.CoAnnouncementsService;
import ba.infostudio.com.service.dto.CoAnnouncementsDTO;
import ba.infostudio.com.service.mapper.CoAnnouncementsMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static ba.infostudio.com.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CoAnnouncementsResource REST controller.
 *
 * @see CoAnnouncementsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class CoAnnouncementsResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CoAnnouncementsRepository coAnnouncementsRepository;

    @Autowired
    private CoAnnouncementsMapper coAnnouncementsMapper;

    @Autowired
    private CoAnnouncementsService coAnnouncementsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCoAnnouncementsMockMvc;

    private CoAnnouncements coAnnouncements;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoAnnouncementsResource coAnnouncementsResource = new CoAnnouncementsResource(coAnnouncementsService);
        this.restCoAnnouncementsMockMvc = MockMvcBuilders.standaloneSetup(coAnnouncementsResource)
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
    public static CoAnnouncements createEntity(EntityManager em) {
        CoAnnouncements coAnnouncements = new CoAnnouncements()
            .title(DEFAULT_TITLE)
            .message(DEFAULT_MESSAGE)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return coAnnouncements;
    }

    @Before
    public void initTest() {
        coAnnouncements = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoAnnouncements() throws Exception {
        int databaseSizeBeforeCreate = coAnnouncementsRepository.findAll().size();

        // Create the CoAnnouncements
        CoAnnouncementsDTO coAnnouncementsDTO = coAnnouncementsMapper.toDto(coAnnouncements);
        restCoAnnouncementsMockMvc.perform(post("/api/co-announcements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coAnnouncementsDTO)))
            .andExpect(status().isCreated());

        // Validate the CoAnnouncements in the database
        List<CoAnnouncements> coAnnouncementsList = coAnnouncementsRepository.findAll();
        assertThat(coAnnouncementsList).hasSize(databaseSizeBeforeCreate + 1);
        CoAnnouncements testCoAnnouncements = coAnnouncementsList.get(coAnnouncementsList.size() - 1);
        assertThat(testCoAnnouncements.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCoAnnouncements.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testCoAnnouncements.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCoAnnouncements.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createCoAnnouncementsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coAnnouncementsRepository.findAll().size();

        // Create the CoAnnouncements with an existing ID
        coAnnouncements.setId(1L);
        CoAnnouncementsDTO coAnnouncementsDTO = coAnnouncementsMapper.toDto(coAnnouncements);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoAnnouncementsMockMvc.perform(post("/api/co-announcements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coAnnouncementsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CoAnnouncements in the database
        List<CoAnnouncements> coAnnouncementsList = coAnnouncementsRepository.findAll();
        assertThat(coAnnouncementsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = coAnnouncementsRepository.findAll().size();
        // set the field null
        coAnnouncements.setTitle(null);

        // Create the CoAnnouncements, which fails.
        CoAnnouncementsDTO coAnnouncementsDTO = coAnnouncementsMapper.toDto(coAnnouncements);

        restCoAnnouncementsMockMvc.perform(post("/api/co-announcements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coAnnouncementsDTO)))
            .andExpect(status().isBadRequest());

        List<CoAnnouncements> coAnnouncementsList = coAnnouncementsRepository.findAll();
        assertThat(coAnnouncementsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = coAnnouncementsRepository.findAll().size();
        // set the field null
        coAnnouncements.setMessage(null);

        // Create the CoAnnouncements, which fails.
        CoAnnouncementsDTO coAnnouncementsDTO = coAnnouncementsMapper.toDto(coAnnouncements);

        restCoAnnouncementsMockMvc.perform(post("/api/co-announcements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coAnnouncementsDTO)))
            .andExpect(status().isBadRequest());

        List<CoAnnouncements> coAnnouncementsList = coAnnouncementsRepository.findAll();
        assertThat(coAnnouncementsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = coAnnouncementsRepository.findAll().size();
        // set the field null
        coAnnouncements.setValidFrom(null);

        // Create the CoAnnouncements, which fails.
        CoAnnouncementsDTO coAnnouncementsDTO = coAnnouncementsMapper.toDto(coAnnouncements);

        restCoAnnouncementsMockMvc.perform(post("/api/co-announcements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coAnnouncementsDTO)))
            .andExpect(status().isBadRequest());

        List<CoAnnouncements> coAnnouncementsList = coAnnouncementsRepository.findAll();
        assertThat(coAnnouncementsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = coAnnouncementsRepository.findAll().size();
        // set the field null
        coAnnouncements.setValidTo(null);

        // Create the CoAnnouncements, which fails.
        CoAnnouncementsDTO coAnnouncementsDTO = coAnnouncementsMapper.toDto(coAnnouncements);

        restCoAnnouncementsMockMvc.perform(post("/api/co-announcements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coAnnouncementsDTO)))
            .andExpect(status().isBadRequest());

        List<CoAnnouncements> coAnnouncementsList = coAnnouncementsRepository.findAll();
        assertThat(coAnnouncementsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCoAnnouncements() throws Exception {
        // Initialize the database
        coAnnouncementsRepository.saveAndFlush(coAnnouncements);

        // Get all the coAnnouncementsList
        restCoAnnouncementsMockMvc.perform(get("/api/co-announcements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coAnnouncements.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }

    @Test
    @Transactional
    public void getCoAnnouncements() throws Exception {
        // Initialize the database
        coAnnouncementsRepository.saveAndFlush(coAnnouncements);

        // Get the coAnnouncements
        restCoAnnouncementsMockMvc.perform(get("/api/co-announcements/{id}", coAnnouncements.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coAnnouncements.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCoAnnouncements() throws Exception {
        // Get the coAnnouncements
        restCoAnnouncementsMockMvc.perform(get("/api/co-announcements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoAnnouncements() throws Exception {
        // Initialize the database
        coAnnouncementsRepository.saveAndFlush(coAnnouncements);
        int databaseSizeBeforeUpdate = coAnnouncementsRepository.findAll().size();

        // Update the coAnnouncements
        CoAnnouncements updatedCoAnnouncements = coAnnouncementsRepository.findOne(coAnnouncements.getId());
        // Disconnect from session so that the updates on updatedCoAnnouncements are not directly saved in db
        em.detach(updatedCoAnnouncements);
        updatedCoAnnouncements
            .title(UPDATED_TITLE)
            .message(UPDATED_MESSAGE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        CoAnnouncementsDTO coAnnouncementsDTO = coAnnouncementsMapper.toDto(updatedCoAnnouncements);

        restCoAnnouncementsMockMvc.perform(put("/api/co-announcements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coAnnouncementsDTO)))
            .andExpect(status().isOk());

        // Validate the CoAnnouncements in the database
        List<CoAnnouncements> coAnnouncementsList = coAnnouncementsRepository.findAll();
        assertThat(coAnnouncementsList).hasSize(databaseSizeBeforeUpdate);
        CoAnnouncements testCoAnnouncements = coAnnouncementsList.get(coAnnouncementsList.size() - 1);
        assertThat(testCoAnnouncements.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCoAnnouncements.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testCoAnnouncements.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCoAnnouncements.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingCoAnnouncements() throws Exception {
        int databaseSizeBeforeUpdate = coAnnouncementsRepository.findAll().size();

        // Create the CoAnnouncements
        CoAnnouncementsDTO coAnnouncementsDTO = coAnnouncementsMapper.toDto(coAnnouncements);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCoAnnouncementsMockMvc.perform(put("/api/co-announcements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coAnnouncementsDTO)))
            .andExpect(status().isCreated());

        // Validate the CoAnnouncements in the database
        List<CoAnnouncements> coAnnouncementsList = coAnnouncementsRepository.findAll();
        assertThat(coAnnouncementsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCoAnnouncements() throws Exception {
        // Initialize the database
        coAnnouncementsRepository.saveAndFlush(coAnnouncements);
        int databaseSizeBeforeDelete = coAnnouncementsRepository.findAll().size();

        // Get the coAnnouncements
        restCoAnnouncementsMockMvc.perform(delete("/api/co-announcements/{id}", coAnnouncements.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CoAnnouncements> coAnnouncementsList = coAnnouncementsRepository.findAll();
        assertThat(coAnnouncementsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoAnnouncements.class);
        CoAnnouncements coAnnouncements1 = new CoAnnouncements();
        coAnnouncements1.setId(1L);
        CoAnnouncements coAnnouncements2 = new CoAnnouncements();
        coAnnouncements2.setId(coAnnouncements1.getId());
        assertThat(coAnnouncements1).isEqualTo(coAnnouncements2);
        coAnnouncements2.setId(2L);
        assertThat(coAnnouncements1).isNotEqualTo(coAnnouncements2);
        coAnnouncements1.setId(null);
        assertThat(coAnnouncements1).isNotEqualTo(coAnnouncements2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoAnnouncementsDTO.class);
        CoAnnouncementsDTO coAnnouncementsDTO1 = new CoAnnouncementsDTO();
        coAnnouncementsDTO1.setId(1L);
        CoAnnouncementsDTO coAnnouncementsDTO2 = new CoAnnouncementsDTO();
        assertThat(coAnnouncementsDTO1).isNotEqualTo(coAnnouncementsDTO2);
        coAnnouncementsDTO2.setId(coAnnouncementsDTO1.getId());
        assertThat(coAnnouncementsDTO1).isEqualTo(coAnnouncementsDTO2);
        coAnnouncementsDTO2.setId(2L);
        assertThat(coAnnouncementsDTO1).isNotEqualTo(coAnnouncementsDTO2);
        coAnnouncementsDTO1.setId(null);
        assertThat(coAnnouncementsDTO1).isNotEqualTo(coAnnouncementsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(coAnnouncementsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(coAnnouncementsMapper.fromId(null)).isNull();
    }
}
