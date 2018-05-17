package ba.infostudio.com.web.rest;

import ba.infostudio.com.HcmCoreMicroserviceApp;

import ba.infostudio.com.domain.DmDocumentLinks;
import ba.infostudio.com.repository.DmDocumentLinksRepository;
import ba.infostudio.com.service.dto.DmDocumentLinksDTO;
import ba.infostudio.com.service.mapper.DmDocumentLinksMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static ba.infostudio.com.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DmDocumentLinksResource REST controller.
 *
 * @see DmDocumentLinksResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcmCoreMicroserviceApp.class)
public class DmDocumentLinksResourceIntTest {

    private static final String DEFAULT_DOCUMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_URI = "AAAAAAAAAA";
    private static final String UPDATED_URI = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOCUMENT_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENT_BLOB = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_DOCUMENT_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENT_BLOB_CONTENT_TYPE = "image/png";

    @Autowired
    private DmDocumentLinksRepository dmDocumentLinksRepository;

    @Autowired
    private DmDocumentLinksMapper dmDocumentLinksMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDmDocumentLinksMockMvc;

    private DmDocumentLinks dmDocumentLinks;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DmDocumentLinksResource dmDocumentLinksResource = new DmDocumentLinksResource(dmDocumentLinksRepository, dmDocumentLinksMapper);
        this.restDmDocumentLinksMockMvc = MockMvcBuilders.standaloneSetup(dmDocumentLinksResource)
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
    public static DmDocumentLinks createEntity(EntityManager em) {
        DmDocumentLinks dmDocumentLinks = new DmDocumentLinks()
            .documentName(DEFAULT_DOCUMENT_NAME)
            .fileName(DEFAULT_FILE_NAME)
            .filePath(DEFAULT_FILE_PATH)
            .uri(DEFAULT_URI)
            .documentBlob(DEFAULT_DOCUMENT_BLOB)
            .documentBlobContentType(DEFAULT_DOCUMENT_BLOB_CONTENT_TYPE);
        return dmDocumentLinks;
    }

    @Before
    public void initTest() {
        dmDocumentLinks = createEntity(em);
    }

    @Test
    @Transactional
    public void createDmDocumentLinks() throws Exception {
        int databaseSizeBeforeCreate = dmDocumentLinksRepository.findAll().size();

        // Create the DmDocumentLinks
        DmDocumentLinksDTO dmDocumentLinksDTO = dmDocumentLinksMapper.toDto(dmDocumentLinks);
        restDmDocumentLinksMockMvc.perform(post("/api/dm-document-links")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dmDocumentLinksDTO)))
            .andExpect(status().isCreated());

        // Validate the DmDocumentLinks in the database
        List<DmDocumentLinks> dmDocumentLinksList = dmDocumentLinksRepository.findAll();
        assertThat(dmDocumentLinksList).hasSize(databaseSizeBeforeCreate + 1);
        DmDocumentLinks testDmDocumentLinks = dmDocumentLinksList.get(dmDocumentLinksList.size() - 1);
        assertThat(testDmDocumentLinks.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
        assertThat(testDmDocumentLinks.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testDmDocumentLinks.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
        assertThat(testDmDocumentLinks.getUri()).isEqualTo(DEFAULT_URI);
        assertThat(testDmDocumentLinks.getDocumentBlob()).isEqualTo(DEFAULT_DOCUMENT_BLOB);
        assertThat(testDmDocumentLinks.getDocumentBlobContentType()).isEqualTo(DEFAULT_DOCUMENT_BLOB_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createDmDocumentLinksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dmDocumentLinksRepository.findAll().size();

        // Create the DmDocumentLinks with an existing ID
        dmDocumentLinks.setId(1L);
        DmDocumentLinksDTO dmDocumentLinksDTO = dmDocumentLinksMapper.toDto(dmDocumentLinks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDmDocumentLinksMockMvc.perform(post("/api/dm-document-links")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dmDocumentLinksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DmDocumentLinks in the database
        List<DmDocumentLinks> dmDocumentLinksList = dmDocumentLinksRepository.findAll();
        assertThat(dmDocumentLinksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDmDocumentLinks() throws Exception {
        // Initialize the database
        dmDocumentLinksRepository.saveAndFlush(dmDocumentLinks);

        // Get all the dmDocumentLinksList
        restDmDocumentLinksMockMvc.perform(get("/api/dm-document-links?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dmDocumentLinks.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH.toString())))
            .andExpect(jsonPath("$.[*].uri").value(hasItem(DEFAULT_URI.toString())))
            .andExpect(jsonPath("$.[*].documentBlobContentType").value(hasItem(DEFAULT_DOCUMENT_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_BLOB))));
    }

    @Test
    @Transactional
    public void getDmDocumentLinks() throws Exception {
        // Initialize the database
        dmDocumentLinksRepository.saveAndFlush(dmDocumentLinks);

        // Get the dmDocumentLinks
        restDmDocumentLinksMockMvc.perform(get("/api/dm-document-links/{id}", dmDocumentLinks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dmDocumentLinks.getId().intValue()))
            .andExpect(jsonPath("$.documentName").value(DEFAULT_DOCUMENT_NAME.toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH.toString()))
            .andExpect(jsonPath("$.uri").value(DEFAULT_URI.toString()))
            .andExpect(jsonPath("$.documentBlobContentType").value(DEFAULT_DOCUMENT_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.documentBlob").value(Base64Utils.encodeToString(DEFAULT_DOCUMENT_BLOB)));
    }

    @Test
    @Transactional
    public void getNonExistingDmDocumentLinks() throws Exception {
        // Get the dmDocumentLinks
        restDmDocumentLinksMockMvc.perform(get("/api/dm-document-links/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDmDocumentLinks() throws Exception {
        // Initialize the database
        dmDocumentLinksRepository.saveAndFlush(dmDocumentLinks);
        int databaseSizeBeforeUpdate = dmDocumentLinksRepository.findAll().size();

        // Update the dmDocumentLinks
        DmDocumentLinks updatedDmDocumentLinks = dmDocumentLinksRepository.findOne(dmDocumentLinks.getId());
        // Disconnect from session so that the updates on updatedDmDocumentLinks are not directly saved in db
        em.detach(updatedDmDocumentLinks);
        updatedDmDocumentLinks
            .documentName(UPDATED_DOCUMENT_NAME)
            .fileName(UPDATED_FILE_NAME)
            .filePath(UPDATED_FILE_PATH)
            .uri(UPDATED_URI)
            .documentBlob(UPDATED_DOCUMENT_BLOB)
            .documentBlobContentType(UPDATED_DOCUMENT_BLOB_CONTENT_TYPE);
        DmDocumentLinksDTO dmDocumentLinksDTO = dmDocumentLinksMapper.toDto(updatedDmDocumentLinks);

        restDmDocumentLinksMockMvc.perform(put("/api/dm-document-links")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dmDocumentLinksDTO)))
            .andExpect(status().isOk());

        // Validate the DmDocumentLinks in the database
        List<DmDocumentLinks> dmDocumentLinksList = dmDocumentLinksRepository.findAll();
        assertThat(dmDocumentLinksList).hasSize(databaseSizeBeforeUpdate);
        DmDocumentLinks testDmDocumentLinks = dmDocumentLinksList.get(dmDocumentLinksList.size() - 1);
        assertThat(testDmDocumentLinks.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testDmDocumentLinks.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testDmDocumentLinks.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testDmDocumentLinks.getUri()).isEqualTo(UPDATED_URI);
        assertThat(testDmDocumentLinks.getDocumentBlob()).isEqualTo(UPDATED_DOCUMENT_BLOB);
        assertThat(testDmDocumentLinks.getDocumentBlobContentType()).isEqualTo(UPDATED_DOCUMENT_BLOB_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDmDocumentLinks() throws Exception {
        int databaseSizeBeforeUpdate = dmDocumentLinksRepository.findAll().size();

        // Create the DmDocumentLinks
        DmDocumentLinksDTO dmDocumentLinksDTO = dmDocumentLinksMapper.toDto(dmDocumentLinks);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDmDocumentLinksMockMvc.perform(put("/api/dm-document-links")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dmDocumentLinksDTO)))
            .andExpect(status().isCreated());

        // Validate the DmDocumentLinks in the database
        List<DmDocumentLinks> dmDocumentLinksList = dmDocumentLinksRepository.findAll();
        assertThat(dmDocumentLinksList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDmDocumentLinks() throws Exception {
        // Initialize the database
        dmDocumentLinksRepository.saveAndFlush(dmDocumentLinks);
        int databaseSizeBeforeDelete = dmDocumentLinksRepository.findAll().size();

        // Get the dmDocumentLinks
        restDmDocumentLinksMockMvc.perform(delete("/api/dm-document-links/{id}", dmDocumentLinks.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DmDocumentLinks> dmDocumentLinksList = dmDocumentLinksRepository.findAll();
        assertThat(dmDocumentLinksList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DmDocumentLinks.class);
        DmDocumentLinks dmDocumentLinks1 = new DmDocumentLinks();
        dmDocumentLinks1.setId(1L);
        DmDocumentLinks dmDocumentLinks2 = new DmDocumentLinks();
        dmDocumentLinks2.setId(dmDocumentLinks1.getId());
        assertThat(dmDocumentLinks1).isEqualTo(dmDocumentLinks2);
        dmDocumentLinks2.setId(2L);
        assertThat(dmDocumentLinks1).isNotEqualTo(dmDocumentLinks2);
        dmDocumentLinks1.setId(null);
        assertThat(dmDocumentLinks1).isNotEqualTo(dmDocumentLinks2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DmDocumentLinksDTO.class);
        DmDocumentLinksDTO dmDocumentLinksDTO1 = new DmDocumentLinksDTO();
        dmDocumentLinksDTO1.setId(1L);
        DmDocumentLinksDTO dmDocumentLinksDTO2 = new DmDocumentLinksDTO();
        assertThat(dmDocumentLinksDTO1).isNotEqualTo(dmDocumentLinksDTO2);
        dmDocumentLinksDTO2.setId(dmDocumentLinksDTO1.getId());
        assertThat(dmDocumentLinksDTO1).isEqualTo(dmDocumentLinksDTO2);
        dmDocumentLinksDTO2.setId(2L);
        assertThat(dmDocumentLinksDTO1).isNotEqualTo(dmDocumentLinksDTO2);
        dmDocumentLinksDTO1.setId(null);
        assertThat(dmDocumentLinksDTO1).isNotEqualTo(dmDocumentLinksDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dmDocumentLinksMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dmDocumentLinksMapper.fromId(null)).isNull();
    }
}
