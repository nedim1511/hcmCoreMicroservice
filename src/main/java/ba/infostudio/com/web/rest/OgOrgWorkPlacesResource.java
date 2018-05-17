package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.OgOrgWorkPlaces;

import ba.infostudio.com.repository.OgOrgWorkPlacesRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.OgOrgWorkPlacesDTO;
import ba.infostudio.com.service.mapper.OgOrgWorkPlacesMapper;
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
 * REST controller for managing OgOrgWorkPlaces.
 */
@RestController
@RequestMapping("/api")
public class OgOrgWorkPlacesResource {

    private final Logger log = LoggerFactory.getLogger(OgOrgWorkPlacesResource.class);

    private static final String ENTITY_NAME = "ogOrgWorkPlaces";

    private final OgOrgWorkPlacesRepository ogOrgWorkPlacesRepository;

    private final OgOrgWorkPlacesMapper ogOrgWorkPlacesMapper;

    public OgOrgWorkPlacesResource(OgOrgWorkPlacesRepository ogOrgWorkPlacesRepository, OgOrgWorkPlacesMapper ogOrgWorkPlacesMapper) {
        this.ogOrgWorkPlacesRepository = ogOrgWorkPlacesRepository;
        this.ogOrgWorkPlacesMapper = ogOrgWorkPlacesMapper;
    }

    /**
     * POST  /og-org-work-places : Create a new ogOrgWorkPlaces.
     *
     * @param ogOrgWorkPlacesDTO the ogOrgWorkPlacesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ogOrgWorkPlacesDTO, or with status 400 (Bad Request) if the ogOrgWorkPlaces has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/og-org-work-places")
    @Timed
    public ResponseEntity<OgOrgWorkPlacesDTO> createOgOrgWorkPlaces(@RequestBody OgOrgWorkPlacesDTO ogOrgWorkPlacesDTO) throws URISyntaxException {
        log.debug("REST request to save OgOrgWorkPlaces : {}", ogOrgWorkPlacesDTO);
        if (ogOrgWorkPlacesDTO.getId() != null) {
            throw new BadRequestAlertException("A new ogOrgWorkPlaces cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OgOrgWorkPlaces ogOrgWorkPlaces = ogOrgWorkPlacesMapper.toEntity(ogOrgWorkPlacesDTO);
        ogOrgWorkPlaces = ogOrgWorkPlacesRepository.save(ogOrgWorkPlaces);
        OgOrgWorkPlacesDTO result = ogOrgWorkPlacesMapper.toDto(ogOrgWorkPlaces);
        return ResponseEntity.created(new URI("/api/og-org-work-places/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /og-org-work-places : Updates an existing ogOrgWorkPlaces.
     *
     * @param ogOrgWorkPlacesDTO the ogOrgWorkPlacesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ogOrgWorkPlacesDTO,
     * or with status 400 (Bad Request) if the ogOrgWorkPlacesDTO is not valid,
     * or with status 500 (Internal Server Error) if the ogOrgWorkPlacesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/og-org-work-places")
    @Timed
    public ResponseEntity<OgOrgWorkPlacesDTO> updateOgOrgWorkPlaces(@RequestBody OgOrgWorkPlacesDTO ogOrgWorkPlacesDTO) throws URISyntaxException {
        log.debug("REST request to update OgOrgWorkPlaces : {}", ogOrgWorkPlacesDTO);
        if (ogOrgWorkPlacesDTO.getId() == null) {
            return createOgOrgWorkPlaces(ogOrgWorkPlacesDTO);
        }
        OgOrgWorkPlaces ogOrgWorkPlaces = ogOrgWorkPlacesMapper.toEntity(ogOrgWorkPlacesDTO);
        ogOrgWorkPlaces = ogOrgWorkPlacesRepository.save(ogOrgWorkPlaces);
        OgOrgWorkPlacesDTO result = ogOrgWorkPlacesMapper.toDto(ogOrgWorkPlaces);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ogOrgWorkPlacesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /og-org-work-places : get all the ogOrgWorkPlaces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ogOrgWorkPlaces in body
     */
    @GetMapping("/og-org-work-places")
    @Timed
    public ResponseEntity<List<OgOrgWorkPlacesDTO>> getAllOgOrgWorkPlaces(Pageable pageable) {
        log.debug("REST request to get a page of OgOrgWorkPlaces");
        Page<OgOrgWorkPlaces> page = ogOrgWorkPlacesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/og-org-work-places");
        return new ResponseEntity<>(ogOrgWorkPlacesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /og-org-work-places/:id : get the "id" ogOrgWorkPlaces.
     *
     * @param id the id of the ogOrgWorkPlacesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ogOrgWorkPlacesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/og-org-work-places/{id}")
    @Timed
    public ResponseEntity<OgOrgWorkPlacesDTO> getOgOrgWorkPlaces(@PathVariable Long id) {
        log.debug("REST request to get OgOrgWorkPlaces : {}", id);
        OgOrgWorkPlaces ogOrgWorkPlaces = ogOrgWorkPlacesRepository.findOne(id);
        OgOrgWorkPlacesDTO ogOrgWorkPlacesDTO = ogOrgWorkPlacesMapper.toDto(ogOrgWorkPlaces);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ogOrgWorkPlacesDTO));
    }

    /**
     * DELETE  /og-org-work-places/:id : delete the "id" ogOrgWorkPlaces.
     *
     * @param id the id of the ogOrgWorkPlacesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/og-org-work-places/{id}")
    @Timed
    public ResponseEntity<Void> deleteOgOrgWorkPlaces(@PathVariable Long id) {
        log.debug("REST request to delete OgOrgWorkPlaces : {}", id);
        ogOrgWorkPlacesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
