package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.RgSkillGrades;

import ba.infostudio.com.repository.RgSkillGradesRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.RgSkillGradesDTO;
import ba.infostudio.com.service.mapper.RgSkillGradesMapper;
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
 * REST controller for managing RgSkillGrades.
 */
@RestController
@RequestMapping("/api")
public class RgSkillGradesResource {

    private final Logger log = LoggerFactory.getLogger(RgSkillGradesResource.class);

    private static final String ENTITY_NAME = "rgSkillGrades";

    private final RgSkillGradesRepository rgSkillGradesRepository;

    private final RgSkillGradesMapper rgSkillGradesMapper;

    public RgSkillGradesResource(RgSkillGradesRepository rgSkillGradesRepository, RgSkillGradesMapper rgSkillGradesMapper) {
        this.rgSkillGradesRepository = rgSkillGradesRepository;
        this.rgSkillGradesMapper = rgSkillGradesMapper;
    }

    /**
     * POST  /rg-skill-grades : Create a new rgSkillGrades.
     *
     * @param rgSkillGradesDTO the rgSkillGradesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rgSkillGradesDTO, or with status 400 (Bad Request) if the rgSkillGrades has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rg-skill-grades")
    @Timed
    public ResponseEntity<RgSkillGradesDTO> createRgSkillGrades(@Valid @RequestBody RgSkillGradesDTO rgSkillGradesDTO) throws URISyntaxException {
        log.debug("REST request to save RgSkillGrades : {}", rgSkillGradesDTO);
        if (rgSkillGradesDTO.getId() != null) {
            throw new BadRequestAlertException("A new rgSkillGrades cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RgSkillGrades rgSkillGrades = rgSkillGradesMapper.toEntity(rgSkillGradesDTO);
        rgSkillGrades = rgSkillGradesRepository.save(rgSkillGrades);
        RgSkillGradesDTO result = rgSkillGradesMapper.toDto(rgSkillGrades);
        return ResponseEntity.created(new URI("/api/rg-skill-grades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rg-skill-grades : Updates an existing rgSkillGrades.
     *
     * @param rgSkillGradesDTO the rgSkillGradesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rgSkillGradesDTO,
     * or with status 400 (Bad Request) if the rgSkillGradesDTO is not valid,
     * or with status 500 (Internal Server Error) if the rgSkillGradesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rg-skill-grades")
    @Timed
    public ResponseEntity<RgSkillGradesDTO> updateRgSkillGrades(@Valid @RequestBody RgSkillGradesDTO rgSkillGradesDTO) throws URISyntaxException {
        log.debug("REST request to update RgSkillGrades : {}", rgSkillGradesDTO);
        if (rgSkillGradesDTO.getId() == null) {
            return createRgSkillGrades(rgSkillGradesDTO);
        }
        RgSkillGrades rgSkillGrades = rgSkillGradesMapper.toEntity(rgSkillGradesDTO);
        rgSkillGrades = rgSkillGradesRepository.save(rgSkillGrades);
        RgSkillGradesDTO result = rgSkillGradesMapper.toDto(rgSkillGrades);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rgSkillGradesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rg-skill-grades : get all the rgSkillGrades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rgSkillGrades in body
     */
    @GetMapping("/rg-skill-grades")
    @Timed
    public ResponseEntity<List<RgSkillGradesDTO>> getAllRgSkillGrades(Pageable pageable) {
        log.debug("REST request to get a page of RgSkillGrades");
        Page<RgSkillGrades> page = rgSkillGradesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rg-skill-grades");
        return new ResponseEntity<>(rgSkillGradesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /rg-skill-grades/:id : get the "id" rgSkillGrades.
     *
     * @param id the id of the rgSkillGradesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rgSkillGradesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rg-skill-grades/{id}")
    @Timed
    public ResponseEntity<RgSkillGradesDTO> getRgSkillGrades(@PathVariable Long id) {
        log.debug("REST request to get RgSkillGrades : {}", id);
        RgSkillGrades rgSkillGrades = rgSkillGradesRepository.findOne(id);
        RgSkillGradesDTO rgSkillGradesDTO = rgSkillGradesMapper.toDto(rgSkillGrades);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rgSkillGradesDTO));
    }

    /**
     * DELETE  /rg-skill-grades/:id : delete the "id" rgSkillGrades.
     *
     * @param id the id of the rgSkillGradesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rg-skill-grades/{id}")
    @Timed
    public ResponseEntity<Void> deleteRgSkillGrades(@PathVariable Long id) {
        log.debug("REST request to delete RgSkillGrades : {}", id);
        rgSkillGradesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
