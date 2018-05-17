package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.OgWorkPlaceSkills;

import ba.infostudio.com.repository.OgWorkPlaceSkillsRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.OgWorkPlaceSkillsDTO;
import ba.infostudio.com.service.mapper.OgWorkPlaceSkillsMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OgWorkPlaceSkills.
 */
@RestController
@RequestMapping("/api")
public class OgWorkPlaceSkillsResource {

    private final Logger log = LoggerFactory.getLogger(OgWorkPlaceSkillsResource.class);

    private static final String ENTITY_NAME = "ogWorkPlaceSkills";

    private final OgWorkPlaceSkillsRepository ogWorkPlaceSkillsRepository;

    private final OgWorkPlaceSkillsMapper ogWorkPlaceSkillsMapper;

    public OgWorkPlaceSkillsResource(OgWorkPlaceSkillsRepository ogWorkPlaceSkillsRepository, OgWorkPlaceSkillsMapper ogWorkPlaceSkillsMapper) {
        this.ogWorkPlaceSkillsRepository = ogWorkPlaceSkillsRepository;
        this.ogWorkPlaceSkillsMapper = ogWorkPlaceSkillsMapper;
    }

    /**
     * POST  /og-work-place-skills : Create a new ogWorkPlaceSkills.
     *
     * @param ogWorkPlaceSkillsDTO the ogWorkPlaceSkillsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ogWorkPlaceSkillsDTO, or with status 400 (Bad Request) if the ogWorkPlaceSkills has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/og-work-place-skills")
    @Timed
    public ResponseEntity<OgWorkPlaceSkillsDTO> createOgWorkPlaceSkills(@RequestBody OgWorkPlaceSkillsDTO ogWorkPlaceSkillsDTO) throws URISyntaxException {
        log.debug("REST request to save OgWorkPlaceSkills : {}", ogWorkPlaceSkillsDTO);
        if (ogWorkPlaceSkillsDTO.getId() != null) {
            throw new BadRequestAlertException("A new ogWorkPlaceSkills cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OgWorkPlaceSkills ogWorkPlaceSkills = ogWorkPlaceSkillsMapper.toEntity(ogWorkPlaceSkillsDTO);
        ogWorkPlaceSkills = ogWorkPlaceSkillsRepository.save(ogWorkPlaceSkills);
        OgWorkPlaceSkillsDTO result = ogWorkPlaceSkillsMapper.toDto(ogWorkPlaceSkills);
        return ResponseEntity.created(new URI("/api/og-work-place-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /og-work-place-skills : Updates an existing ogWorkPlaceSkills.
     *
     * @param ogWorkPlaceSkillsDTO the ogWorkPlaceSkillsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ogWorkPlaceSkillsDTO,
     * or with status 400 (Bad Request) if the ogWorkPlaceSkillsDTO is not valid,
     * or with status 500 (Internal Server Error) if the ogWorkPlaceSkillsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/og-work-place-skills")
    @Timed
    public ResponseEntity<OgWorkPlaceSkillsDTO> updateOgWorkPlaceSkills(@RequestBody OgWorkPlaceSkillsDTO ogWorkPlaceSkillsDTO) throws URISyntaxException {
        log.debug("REST request to update OgWorkPlaceSkills : {}", ogWorkPlaceSkillsDTO);
        if (ogWorkPlaceSkillsDTO.getId() == null) {
            return createOgWorkPlaceSkills(ogWorkPlaceSkillsDTO);
        }
        OgWorkPlaceSkills ogWorkPlaceSkills = ogWorkPlaceSkillsMapper.toEntity(ogWorkPlaceSkillsDTO);
        ogWorkPlaceSkills = ogWorkPlaceSkillsRepository.save(ogWorkPlaceSkills);
        OgWorkPlaceSkillsDTO result = ogWorkPlaceSkillsMapper.toDto(ogWorkPlaceSkills);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ogWorkPlaceSkillsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /og-work-place-skills : get all the ogWorkPlaceSkills.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ogWorkPlaceSkills in body
     */
    @GetMapping("/og-work-place-skills")
    @Timed
    public ResponseEntity<List<OgWorkPlaceSkillsDTO>> getAllOgWorkPlaceSkills(Pageable pageable) {
        log.debug("REST request to get a page of OgWorkPlaceSkills");
        Page<OgWorkPlaceSkills> page = ogWorkPlaceSkillsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/og-work-place-skills");
        return new ResponseEntity<>(ogWorkPlaceSkillsMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /og-work-place-skills/:id : get the "id" ogWorkPlaceSkills.
     *
     * @param id the id of the ogWorkPlaceSkillsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ogWorkPlaceSkillsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/og-work-place-skills/{id}")
    @Timed
    public ResponseEntity<OgWorkPlaceSkillsDTO> getOgWorkPlaceSkills(@PathVariable Long id) {
        log.debug("REST request to get OgWorkPlaceSkills : {}", id);
        OgWorkPlaceSkills ogWorkPlaceSkills = ogWorkPlaceSkillsRepository.findOne(id);
        OgWorkPlaceSkillsDTO ogWorkPlaceSkillsDTO = ogWorkPlaceSkillsMapper.toDto(ogWorkPlaceSkills);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ogWorkPlaceSkillsDTO));
    }

    /**
     * GET  /og-work-place-skills/workplace/:id : get the "id" ogWorkPlaceSkills by workplace.
     *
     * @param id the id of the ogWorkPlaceSkills to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ogWorkPlaceSkills, or with status 404 (Not Found)
     */
    @GetMapping("/og-work-place-skills/workplace/{id}")
    @Timed
    public ResponseEntity<List<OgWorkPlaceSkills>> getWorkplaceSkillsByWorkplace(@PathVariable Long id) {
        log.debug("REST request to get OgWorkPlaceSkills by workplace: {}", id);
        List<OgWorkPlaceSkills> ogWorkPlaceSkills = ogWorkPlaceSkillsRepository.findByIdWorkPlaceId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ogWorkPlaceSkills));
    }

    /**
     * DELETE  /og-work-place-skills/:id : delete the "id" ogWorkPlaceSkills.
     *
     * @param id the id of the ogWorkPlaceSkillsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/og-work-place-skills/{id}")
    @Timed
    public ResponseEntity<Void> deleteOgWorkPlaceSkills(@PathVariable Long id) {
        log.debug("REST request to delete OgWorkPlaceSkills : {}", id);
        ogWorkPlaceSkillsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
