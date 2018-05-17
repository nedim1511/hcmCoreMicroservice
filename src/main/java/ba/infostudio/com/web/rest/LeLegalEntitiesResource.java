package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.LeLegalEntities;

import ba.infostudio.com.repository.LeLegalEntitiesRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.LeLegalEntitiesDTO;
import ba.infostudio.com.service.mapper.LeLegalEntitiesMapper;
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
 * REST controller for managing LeLegalEntities.
 */
@RestController
@RequestMapping("/api")
public class LeLegalEntitiesResource {

    private final Logger log = LoggerFactory.getLogger(LeLegalEntitiesResource.class);

    private static final String ENTITY_NAME = "leLegalEntities";

    private final LeLegalEntitiesRepository leLegalEntitiesRepository;

    private final LeLegalEntitiesMapper leLegalEntitiesMapper;

    public LeLegalEntitiesResource(LeLegalEntitiesRepository leLegalEntitiesRepository, LeLegalEntitiesMapper leLegalEntitiesMapper) {
        this.leLegalEntitiesRepository = leLegalEntitiesRepository;
        this.leLegalEntitiesMapper = leLegalEntitiesMapper;
    }

    /**
     * POST  /le-legal-entities : Create a new leLegalEntities.
     *
     * @param leLegalEntitiesDTO the leLegalEntitiesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new leLegalEntitiesDTO, or with status 400 (Bad Request) if the leLegalEntities has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/le-legal-entities")
    @Timed
    public ResponseEntity<LeLegalEntitiesDTO> createLeLegalEntities(@Valid @RequestBody LeLegalEntitiesDTO leLegalEntitiesDTO) throws URISyntaxException {
        log.debug("REST request to save LeLegalEntities : {}", leLegalEntitiesDTO);
        if (leLegalEntitiesDTO.getId() != null) {
            throw new BadRequestAlertException("A new leLegalEntities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeLegalEntities leLegalEntities = leLegalEntitiesMapper.toEntity(leLegalEntitiesDTO);
        leLegalEntities = leLegalEntitiesRepository.save(leLegalEntities);
        LeLegalEntitiesDTO result = leLegalEntitiesMapper.toDto(leLegalEntities);
        return ResponseEntity.created(new URI("/api/le-legal-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /le-legal-entities : Updates an existing leLegalEntities.
     *
     * @param leLegalEntitiesDTO the leLegalEntitiesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated leLegalEntitiesDTO,
     * or with status 400 (Bad Request) if the leLegalEntitiesDTO is not valid,
     * or with status 500 (Internal Server Error) if the leLegalEntitiesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/le-legal-entities")
    @Timed
    public ResponseEntity<LeLegalEntitiesDTO> updateLeLegalEntities(@Valid @RequestBody LeLegalEntitiesDTO leLegalEntitiesDTO) throws URISyntaxException {
        log.debug("REST request to update LeLegalEntities : {}", leLegalEntitiesDTO);
        if (leLegalEntitiesDTO.getId() == null) {
            return createLeLegalEntities(leLegalEntitiesDTO);
        }
        LeLegalEntities leLegalEntities = leLegalEntitiesMapper.toEntity(leLegalEntitiesDTO);
        leLegalEntities = leLegalEntitiesRepository.save(leLegalEntities);
        LeLegalEntitiesDTO result = leLegalEntitiesMapper.toDto(leLegalEntities);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, leLegalEntitiesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /le-legal-entities : get all the leLegalEntities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of leLegalEntities in body
     */
    @GetMapping("/le-legal-entities")
    @Timed
    public ResponseEntity<List<LeLegalEntitiesDTO>> getAllLeLegalEntities(Pageable pageable) {
        log.debug("REST request to get a page of LeLegalEntities");
        Page<LeLegalEntities> page = leLegalEntitiesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/le-legal-entities");
        return new ResponseEntity<>(leLegalEntitiesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /le-legal-entities/:id : get the "id" leLegalEntities.
     *
     * @param id the id of the leLegalEntitiesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the leLegalEntitiesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/le-legal-entities/{id}")
    @Timed
    public ResponseEntity<LeLegalEntitiesDTO> getLeLegalEntities(@PathVariable Long id) {
        log.debug("REST request to get LeLegalEntities : {}", id);
        LeLegalEntities leLegalEntities = leLegalEntitiesRepository.findOne(id);
        LeLegalEntitiesDTO leLegalEntitiesDTO = leLegalEntitiesMapper.toDto(leLegalEntities);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(leLegalEntitiesDTO));
    }

    /**
     * GET  /le-legal-entities/type/:id : get the leLegalEntities by entity type.
     *
     * @param id the idEntityType of the leLegalEntities to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the leLegalEntities, or with status 404 (Not Found)
     */
    @GetMapping("/le-legal-entities/entityType/{id}")
    @Timed
    public ResponseEntity<List<LeLegalEntities>> getLeLegalEntitiesByEntityType(@PathVariable Long id) {
        log.debug("REST request to get LeLegalEntities by entity type : {}", id);
        List<LeLegalEntities> leLegalEntities = leLegalEntitiesRepository.findByIdEntityTypeId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(leLegalEntities));
    }

    /**
     * DELETE  /le-legal-entities/:id : delete the "id" leLegalEntities.
     *
     * @param id the id of the leLegalEntitiesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/le-legal-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteLeLegalEntities(@PathVariable Long id) {
        log.debug("REST request to delete LeLegalEntities : {}", id);
        leLegalEntitiesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
