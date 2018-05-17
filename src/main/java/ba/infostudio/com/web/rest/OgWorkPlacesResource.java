package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.OgWorkPlaces;

import ba.infostudio.com.repository.OgWorkPlacesRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.OgWorkPlacesDTO;
import ba.infostudio.com.service.mapper.OgWorkPlacesMapper;
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
 * REST controller for managing OgWorkPlaces.
 */
@RestController
@RequestMapping("/api")
public class OgWorkPlacesResource {

    private final Logger log = LoggerFactory.getLogger(OgWorkPlacesResource.class);

    private static final String ENTITY_NAME = "ogWorkPlaces";

    private final OgWorkPlacesRepository ogWorkPlacesRepository;

    private final OgWorkPlacesMapper ogWorkPlacesMapper;

    public OgWorkPlacesResource(OgWorkPlacesRepository ogWorkPlacesRepository, OgWorkPlacesMapper ogWorkPlacesMapper) {
        this.ogWorkPlacesRepository = ogWorkPlacesRepository;
        this.ogWorkPlacesMapper = ogWorkPlacesMapper;
    }

    /**
     * POST  /og-work-places : Create a new ogWorkPlaces.
     *
     * @param ogWorkPlacesDTO the ogWorkPlacesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ogWorkPlacesDTO, or with status 400 (Bad Request) if the ogWorkPlaces has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/og-work-places")
    @Timed
    public ResponseEntity<OgWorkPlacesDTO> createOgWorkPlaces(@Valid @RequestBody OgWorkPlacesDTO ogWorkPlacesDTO) throws URISyntaxException {
        log.debug("REST request to save OgWorkPlaces : {}", ogWorkPlacesDTO);
        if (ogWorkPlacesDTO.getId() != null) {
            throw new BadRequestAlertException("A new ogWorkPlaces cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OgWorkPlaces ogWorkPlaces = ogWorkPlacesMapper.toEntity(ogWorkPlacesDTO);
        ogWorkPlaces = ogWorkPlacesRepository.save(ogWorkPlaces);
        OgWorkPlacesDTO result = ogWorkPlacesMapper.toDto(ogWorkPlaces);
        return ResponseEntity.created(new URI("/api/og-work-places/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /og-work-places : Updates an existing ogWorkPlaces.
     *
     * @param ogWorkPlacesDTO the ogWorkPlacesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ogWorkPlacesDTO,
     * or with status 400 (Bad Request) if the ogWorkPlacesDTO is not valid,
     * or with status 500 (Internal Server Error) if the ogWorkPlacesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/og-work-places")
    @Timed
    public ResponseEntity<OgWorkPlacesDTO> updateOgWorkPlaces(@Valid @RequestBody OgWorkPlacesDTO ogWorkPlacesDTO) throws URISyntaxException {
        log.debug("REST request to update OgWorkPlaces : {}", ogWorkPlacesDTO);
        if (ogWorkPlacesDTO.getId() == null) {
            return createOgWorkPlaces(ogWorkPlacesDTO);
        }
        OgWorkPlaces ogWorkPlaces = ogWorkPlacesMapper.toEntity(ogWorkPlacesDTO);
        ogWorkPlaces = ogWorkPlacesRepository.save(ogWorkPlaces);
        OgWorkPlacesDTO result = ogWorkPlacesMapper.toDto(ogWorkPlaces);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ogWorkPlacesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /og-work-places : get all the ogWorkPlaces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ogWorkPlaces in body
     */
    @GetMapping("/og-work-places")
    @Timed
    public ResponseEntity<List<OgWorkPlacesDTO>> getAllOgWorkPlaces(Pageable pageable) {
        log.debug("REST request to get a page of OgWorkPlaces");
        Page<OgWorkPlaces> page = ogWorkPlacesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/og-work-places");
        return new ResponseEntity<>(ogWorkPlacesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /og-work-places/:id : get the "id" ogWorkPlaces.
     *
     * @param id the id of the ogWorkPlacesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ogWorkPlacesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/og-work-places/{id}")
    @Timed
    public ResponseEntity<OgWorkPlacesDTO> getOgWorkPlaces(@PathVariable Long id) {
        log.debug("REST request to get OgWorkPlaces : {}", id);
        OgWorkPlaces ogWorkPlaces = ogWorkPlacesRepository.findOne(id);
        OgWorkPlacesDTO ogWorkPlacesDTO = ogWorkPlacesMapper.toDto(ogWorkPlaces);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ogWorkPlacesDTO));
    }

    /**
     * DELETE  /og-work-places/:id : delete the "id" ogWorkPlaces.
     *
     * @param id the id of the ogWorkPlacesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/og-work-places/{id}")
    @Timed
    public ResponseEntity<Void> deleteOgWorkPlaces(@PathVariable Long id) {
        log.debug("REST request to delete OgWorkPlaces : {}", id);
        ogWorkPlacesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
