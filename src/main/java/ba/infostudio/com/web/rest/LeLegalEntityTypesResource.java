package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.LeLegalEntityTypes;

import ba.infostudio.com.repository.LeLegalEntityTypesRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.LeLegalEntityTypesDTO;
import ba.infostudio.com.service.mapper.LeLegalEntityTypesMapper;
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
 * REST controller for managing LeLegalEntityTypes.
 */
@RestController
@RequestMapping("/api")
public class LeLegalEntityTypesResource {

    private final Logger log = LoggerFactory.getLogger(LeLegalEntityTypesResource.class);

    private static final String ENTITY_NAME = "leLegalEntityTypes";

    private final LeLegalEntityTypesRepository leLegalEntityTypesRepository;

    private final LeLegalEntityTypesMapper leLegalEntityTypesMapper;

    public LeLegalEntityTypesResource(LeLegalEntityTypesRepository leLegalEntityTypesRepository, LeLegalEntityTypesMapper leLegalEntityTypesMapper) {
        this.leLegalEntityTypesRepository = leLegalEntityTypesRepository;
        this.leLegalEntityTypesMapper = leLegalEntityTypesMapper;
    }

    /**
     * POST  /le-legal-entity-types : Create a new leLegalEntityTypes.
     *
     * @param leLegalEntityTypesDTO the leLegalEntityTypesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new leLegalEntityTypesDTO, or with status 400 (Bad Request) if the leLegalEntityTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/le-legal-entity-types")
    @Timed
    public ResponseEntity<LeLegalEntityTypesDTO> createLeLegalEntityTypes(@Valid @RequestBody LeLegalEntityTypesDTO leLegalEntityTypesDTO) throws URISyntaxException {
        log.debug("REST request to save LeLegalEntityTypes : {}", leLegalEntityTypesDTO);
        if (leLegalEntityTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new leLegalEntityTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeLegalEntityTypes leLegalEntityTypes = leLegalEntityTypesMapper.toEntity(leLegalEntityTypesDTO);
        leLegalEntityTypes = leLegalEntityTypesRepository.save(leLegalEntityTypes);
        LeLegalEntityTypesDTO result = leLegalEntityTypesMapper.toDto(leLegalEntityTypes);
        return ResponseEntity.created(new URI("/api/le-legal-entity-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /le-legal-entity-types : Updates an existing leLegalEntityTypes.
     *
     * @param leLegalEntityTypesDTO the leLegalEntityTypesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated leLegalEntityTypesDTO,
     * or with status 400 (Bad Request) if the leLegalEntityTypesDTO is not valid,
     * or with status 500 (Internal Server Error) if the leLegalEntityTypesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/le-legal-entity-types")
    @Timed
    public ResponseEntity<LeLegalEntityTypesDTO> updateLeLegalEntityTypes(@Valid @RequestBody LeLegalEntityTypesDTO leLegalEntityTypesDTO) throws URISyntaxException {
        log.debug("REST request to update LeLegalEntityTypes : {}", leLegalEntityTypesDTO);
        if (leLegalEntityTypesDTO.getId() == null) {
            return createLeLegalEntityTypes(leLegalEntityTypesDTO);
        }
        LeLegalEntityTypes leLegalEntityTypes = leLegalEntityTypesMapper.toEntity(leLegalEntityTypesDTO);
        leLegalEntityTypes = leLegalEntityTypesRepository.save(leLegalEntityTypes);
        LeLegalEntityTypesDTO result = leLegalEntityTypesMapper.toDto(leLegalEntityTypes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, leLegalEntityTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /le-legal-entity-types : get all the leLegalEntityTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of leLegalEntityTypes in body
     */
    @GetMapping("/le-legal-entity-types")
    @Timed
    public ResponseEntity<List<LeLegalEntityTypesDTO>> getAllLeLegalEntityTypes(Pageable pageable) {
        log.debug("REST request to get a page of LeLegalEntityTypes");
        Page<LeLegalEntityTypes> page = leLegalEntityTypesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/le-legal-entity-types");
        return new ResponseEntity<>(leLegalEntityTypesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /le-legal-entity-types/:id : get the "id" leLegalEntityTypes.
     *
     * @param id the id of the leLegalEntityTypesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the leLegalEntityTypesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/le-legal-entity-types/{id}")
    @Timed
    public ResponseEntity<LeLegalEntityTypesDTO> getLeLegalEntityTypes(@PathVariable Long id) {
        log.debug("REST request to get LeLegalEntityTypes : {}", id);
        LeLegalEntityTypes leLegalEntityTypes = leLegalEntityTypesRepository.findOne(id);
        LeLegalEntityTypesDTO leLegalEntityTypesDTO = leLegalEntityTypesMapper.toDto(leLegalEntityTypes);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(leLegalEntityTypesDTO));
    }

    /**
     * DELETE  /le-legal-entity-types/:id : delete the "id" leLegalEntityTypes.
     *
     * @param id the id of the leLegalEntityTypesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/le-legal-entity-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteLeLegalEntityTypes(@PathVariable Long id) {
        log.debug("REST request to delete LeLegalEntityTypes : {}", id);
        leLegalEntityTypesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
