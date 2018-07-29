package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.CoFiles;
import ba.infostudio.com.repository.CoFilesRepository;
import ba.infostudio.com.service.dto.CoFilesDTO;
import ba.infostudio.com.service.mapper.CoFilesMapper;
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
 * Test class for the CoFilesResource REST controller.
 *
 * @see CoFilesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class CoFilesResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ID_DOCUMENT_LINK = 1;
    private static final Integer UPDATED_ID_DOCUMENT_LINK = 2;

    @Autowired
    private CoFilesRepository coFilesRepository;

    @Autowired
    private CoFilesMapper coFilesMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCoFilesMockMvc;

    private CoFiles coFiles;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoFilesResource coFilesResource = new CoFilesResource(coFilesRepository, coFilesMapper);
        this.restCoFilesMockMvc = MockMvcBuilders.standaloneSetup(coFilesResource)
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
    public static CoFiles createEntity(EntityManager em) {
        CoFiles coFiles = new CoFiles()
            .title(DEFAULT_TITLE)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO)
            .idDocumentLink(DEFAULT_ID_DOCUMENT_LINK);
        return coFiles;
    }

    @Before
    public void initTest() {
        coFiles = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoFiles() throws Exception {
        int databaseSizeBeforeCreate = coFilesRepository.findAll().size();

        // Create the CoFiles
        CoFilesDTO coFilesDTO = coFilesMapper.toDto(coFiles);
        restCoFilesMockMvc.perform(post("/api/co-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coFilesDTO)))
            .andExpect(status().isCreated());

        // Validate the CoFiles in the database
        List<CoFiles> coFilesList = coFilesRepository.findAll();
        assertThat(coFilesList).hasSize(databaseSizeBeforeCreate + 1);
        CoFiles testCoFiles = coFilesList.get(coFilesList.size() - 1);
        assertThat(testCoFiles.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCoFiles.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCoFiles.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testCoFiles.getIdDocumentLink()).isEqualTo(DEFAULT_ID_DOCUMENT_LINK);
    }

    @Test
    @Transactional
    public void createCoFilesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coFilesRepository.findAll().size();

        // Create the CoFiles with an existing ID
        coFiles.setId(1L);
        CoFilesDTO coFilesDTO = coFilesMapper.toDto(coFiles);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoFilesMockMvc.perform(post("/api/co-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coFilesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CoFiles in the database
        List<CoFiles> coFilesList = coFilesRepository.findAll();
        assertThat(coFilesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = coFilesRepository.findAll().size();
        // set the field null
        coFiles.setTitle(null);

        // Create the CoFiles, which fails.
        CoFilesDTO coFilesDTO = coFilesMapper.toDto(coFiles);

        restCoFilesMockMvc.perform(post("/api/co-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coFilesDTO)))
            .andExpect(status().isBadRequest());

        List<CoFiles> coFilesList = coFilesRepository.findAll();
        assertThat(coFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = coFilesRepository.findAll().size();
        // set the field null
        coFiles.setValidFrom(null);

        // Create the CoFiles, which fails.
        CoFilesDTO coFilesDTO = coFilesMapper.toDto(coFiles);

        restCoFilesMockMvc.perform(post("/api/co-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coFilesDTO)))
            .andExpect(status().isBadRequest());

        List<CoFiles> coFilesList = coFilesRepository.findAll();
        assertThat(coFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = coFilesRepository.findAll().size();
        // set the field null
        coFiles.setValidTo(null);

        // Create the CoFiles, which fails.
        CoFilesDTO coFilesDTO = coFilesMapper.toDto(coFiles);

        restCoFilesMockMvc.perform(post("/api/co-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coFilesDTO)))
            .andExpect(status().isBadRequest());

        List<CoFiles> coFilesList = coFilesRepository.findAll();
        assertThat(coFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdDocumentLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = coFilesRepository.findAll().size();
        // set the field null
        coFiles.setIdDocumentLink(null);

        // Create the CoFiles, which fails.
        CoFilesDTO coFilesDTO = coFilesMapper.toDto(coFiles);

        restCoFilesMockMvc.perform(post("/api/co-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coFilesDTO)))
            .andExpect(status().isBadRequest());

        List<CoFiles> coFilesList = coFilesRepository.findAll();
        assertThat(coFilesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCoFiles() throws Exception {
        // Initialize the database
        coFilesRepository.saveAndFlush(coFiles);

        // Get all the coFilesList
        restCoFilesMockMvc.perform(get("/api/co-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coFiles.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].idDocumentLink").value(hasItem(DEFAULT_ID_DOCUMENT_LINK)));
    }

    @Test
    @Transactional
    public void getCoFiles() throws Exception {
        // Initialize the database
        coFilesRepository.saveAndFlush(coFiles);

        // Get the coFiles
        restCoFilesMockMvc.perform(get("/api/co-files/{id}", coFiles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coFiles.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
            .andExpect(jsonPath("$.idDocumentLink").value(DEFAULT_ID_DOCUMENT_LINK));
    }

    @Test
    @Transactional
    public void getNonExistingCoFiles() throws Exception {
        // Get the coFiles
        restCoFilesMockMvc.perform(get("/api/co-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoFiles() throws Exception {
        // Initialize the database
        coFilesRepository.saveAndFlush(coFiles);
        int databaseSizeBeforeUpdate = coFilesRepository.findAll().size();

        // Update the coFiles
        CoFiles updatedCoFiles = coFilesRepository.findOne(coFiles.getId());
        // Disconnect from session so that the updates on updatedCoFiles are not directly saved in db
        em.detach(updatedCoFiles);
        updatedCoFiles
            .title(UPDATED_TITLE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .idDocumentLink(UPDATED_ID_DOCUMENT_LINK);
        CoFilesDTO coFilesDTO = coFilesMapper.toDto(updatedCoFiles);

        restCoFilesMockMvc.perform(put("/api/co-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coFilesDTO)))
            .andExpect(status().isOk());

        // Validate the CoFiles in the database
        List<CoFiles> coFilesList = coFilesRepository.findAll();
        assertThat(coFilesList).hasSize(databaseSizeBeforeUpdate);
        CoFiles testCoFiles = coFilesList.get(coFilesList.size() - 1);
        assertThat(testCoFiles.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCoFiles.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCoFiles.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testCoFiles.getIdDocumentLink()).isEqualTo(UPDATED_ID_DOCUMENT_LINK);
    }

    @Test
    @Transactional
    public void updateNonExistingCoFiles() throws Exception {
        int databaseSizeBeforeUpdate = coFilesRepository.findAll().size();

        // Create the CoFiles
        CoFilesDTO coFilesDTO = coFilesMapper.toDto(coFiles);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCoFilesMockMvc.perform(put("/api/co-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coFilesDTO)))
            .andExpect(status().isCreated());

        // Validate the CoFiles in the database
        List<CoFiles> coFilesList = coFilesRepository.findAll();
        assertThat(coFilesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCoFiles() throws Exception {
        // Initialize the database
        coFilesRepository.saveAndFlush(coFiles);
        int databaseSizeBeforeDelete = coFilesRepository.findAll().size();

        // Get the coFiles
        restCoFilesMockMvc.perform(delete("/api/co-files/{id}", coFiles.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CoFiles> coFilesList = coFilesRepository.findAll();
        assertThat(coFilesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoFiles.class);
        CoFiles coFiles1 = new CoFiles();
        coFiles1.setId(1L);
        CoFiles coFiles2 = new CoFiles();
        coFiles2.setId(coFiles1.getId());
        assertThat(coFiles1).isEqualTo(coFiles2);
        coFiles2.setId(2L);
        assertThat(coFiles1).isNotEqualTo(coFiles2);
        coFiles1.setId(null);
        assertThat(coFiles1).isNotEqualTo(coFiles2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoFilesDTO.class);
        CoFilesDTO coFilesDTO1 = new CoFilesDTO();
        coFilesDTO1.setId(1L);
        CoFilesDTO coFilesDTO2 = new CoFilesDTO();
        assertThat(coFilesDTO1).isNotEqualTo(coFilesDTO2);
        coFilesDTO2.setId(coFilesDTO1.getId());
        assertThat(coFilesDTO1).isEqualTo(coFilesDTO2);
        coFilesDTO2.setId(2L);
        assertThat(coFilesDTO1).isNotEqualTo(coFilesDTO2);
        coFilesDTO1.setId(null);
        assertThat(coFilesDTO1).isNotEqualTo(coFilesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(coFilesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(coFilesMapper.fromId(null)).isNull();
    }
}
