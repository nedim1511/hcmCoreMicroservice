package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.RgSkills;

import ba.infostudio.com.repository.RgSkillsRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.RgSkillsDTO;
import ba.infostudio.com.service.mapper.RgSkillsMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RgSkills.
 */
@RestController
@RequestMapping("/api")
public class RgSkillsResource {

    private final Logger log = LoggerFactory.getLogger(RgSkillsResource.class);

    private static final String ENTITY_NAME = "rgSkills";

    private final RgSkillsRepository rgSkillsRepository;

    private final RgSkillsMapper rgSkillsMapper;

    public RgSkillsResource(RgSkillsRepository rgSkillsRepository, RgSkillsMapper rgSkillsMapper) {
        this.rgSkillsRepository = rgSkillsRepository;
        this.rgSkillsMapper = rgSkillsMapper;
    }

    /**
     * POST  /rg-skills : Create a new rgSkills.
     *
     * @param rgSkillsDTO the rgSkillsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rgSkillsDTO, or with status 400 (Bad Request) if the rgSkills has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rg-skills")
    @Timed
    public ResponseEntity<RgSkillsDTO> createRgSkills(@Valid @RequestBody RgSkillsDTO rgSkillsDTO) throws URISyntaxException {
        log.debug("REST request to save RgSkills : {}", rgSkillsDTO);
        if (rgSkillsDTO.getId() != null) {
            throw new BadRequestAlertException("A new rgSkills cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RgSkills rgSkills = rgSkillsMapper.toEntity(rgSkillsDTO);
        rgSkills = rgSkillsRepository.save(rgSkills);
        RgSkillsDTO result = rgSkillsMapper.toDto(rgSkills);
        return ResponseEntity.created(new URI("/api/rg-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rg-skills : Updates an existing rgSkills.
     *
     * @param rgSkillsDTO the rgSkillsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rgSkillsDTO,
     * or with status 400 (Bad Request) if the rgSkillsDTO is not valid,
     * or with status 500 (Internal Server Error) if the rgSkillsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rg-skills")
    @Timed
    public ResponseEntity<RgSkillsDTO> updateRgSkills(@Valid @RequestBody RgSkillsDTO rgSkillsDTO) throws URISyntaxException {
        log.debug("REST request to update RgSkills : {}", rgSkillsDTO);
        if (rgSkillsDTO.getId() == null) {
            return createRgSkills(rgSkillsDTO);
        }
        RgSkills rgSkills = rgSkillsMapper.toEntity(rgSkillsDTO);
        rgSkills = rgSkillsRepository.save(rgSkills);
        RgSkillsDTO result = rgSkillsMapper.toDto(rgSkills);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rgSkillsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rg-skills : get all the rgSkills.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rgSkills in body
     */
    @GetMapping("/rg-skills")
    @Timed
    public ResponseEntity<List<RgSkillsDTO>> getAllRgSkills(Pageable pageable) {
        log.debug("REST request to get a page of RgSkills");
        Page<RgSkills> page = rgSkillsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rg-skills");
        return new ResponseEntity<>(rgSkillsMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /rg-skills/:id : get the "id" rgSkills.
     *
     * @param id the id of the rgSkillsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rgSkillsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rg-skills/{id}")
    @Timed
    public ResponseEntity<RgSkillsDTO> getRgSkills(@PathVariable Long id) {
        log.debug("REST request to get RgSkills : {}", id);
        RgSkills rgSkills = rgSkillsRepository.findOne(id);
        RgSkillsDTO rgSkillsDTO = rgSkillsMapper.toDto(rgSkills);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rgSkillsDTO));
    }

    /**
     * DELETE  /rg-skills/:id : delete the "id" rgSkills.
     *
     * @param id the id of the rgSkillsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rg-skills/{id}")
    @Timed
    public ResponseEntity<Void> deleteRgSkills(@PathVariable Long id) {
        log.debug("REST request to delete RgSkills : {}", id);
        rgSkillsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
