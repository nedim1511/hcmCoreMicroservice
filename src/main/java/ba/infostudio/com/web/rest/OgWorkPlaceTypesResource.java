package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.OgWorkPlaceTypes;

import ba.infostudio.com.repository.OgWorkPlaceTypesRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.OgWorkPlaceTypesDTO;
import ba.infostudio.com.service.mapper.OgWorkPlaceTypesMapper;
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
 * REST controller for managing OgWorkPlaceTypes.
 */
@RestController
@RequestMapping("/api")
public class OgWorkPlaceTypesResource {

    private final Logger log = LoggerFactory.getLogger(OgWorkPlaceTypesResource.class);

    private static final String ENTITY_NAME = "ogWorkPlaceTypes";

    private final OgWorkPlaceTypesRepository ogWorkPlaceTypesRepository;

    private final OgWorkPlaceTypesMapper ogWorkPlaceTypesMapper;

    public OgWorkPlaceTypesResource(OgWorkPlaceTypesRepository ogWorkPlaceTypesRepository, OgWorkPlaceTypesMapper ogWorkPlaceTypesMapper) {
        this.ogWorkPlaceTypesRepository = ogWorkPlaceTypesRepository;
        this.ogWorkPlaceTypesMapper = ogWorkPlaceTypesMapper;
    }

    /**
     * POST  /og-work-place-types : Create a new ogWorkPlaceTypes.
     *
     * @param ogWorkPlaceTypesDTO the ogWorkPlaceTypesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ogWorkPlaceTypesDTO, or with status 400 (Bad Request) if the ogWorkPlaceTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/og-work-place-types")
    @Timed
    public ResponseEntity<OgWorkPlaceTypesDTO> createOgWorkPlaceTypes(@Valid @RequestBody OgWorkPlaceTypesDTO ogWorkPlaceTypesDTO) throws URISyntaxException {
        log.debug("REST request to save OgWorkPlaceTypes : {}", ogWorkPlaceTypesDTO);
        if (ogWorkPlaceTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new ogWorkPlaceTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OgWorkPlaceTypes ogWorkPlaceTypes = ogWorkPlaceTypesMapper.toEntity(ogWorkPlaceTypesDTO);
        ogWorkPlaceTypes = ogWorkPlaceTypesRepository.save(ogWorkPlaceTypes);
        OgWorkPlaceTypesDTO result = ogWorkPlaceTypesMapper.toDto(ogWorkPlaceTypes);
        return ResponseEntity.created(new URI("/api/og-work-place-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /og-work-place-types : Updates an existing ogWorkPlaceTypes.
     *
     * @param ogWorkPlaceTypesDTO the ogWorkPlaceTypesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ogWorkPlaceTypesDTO,
     * or with status 400 (Bad Request) if the ogWorkPlaceTypesDTO is not valid,
     * or with status 500 (Internal Server Error) if the ogWorkPlaceTypesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/og-work-place-types")
    @Timed
    public ResponseEntity<OgWorkPlaceTypesDTO> updateOgWorkPlaceTypes(@Valid @RequestBody OgWorkPlaceTypesDTO ogWorkPlaceTypesDTO) throws URISyntaxException {
        log.debug("REST request to update OgWorkPlaceTypes : {}", ogWorkPlaceTypesDTO);
        if (ogWorkPlaceTypesDTO.getId() == null) {
            return createOgWorkPlaceTypes(ogWorkPlaceTypesDTO);
        }
        OgWorkPlaceTypes ogWorkPlaceTypes = ogWorkPlaceTypesMapper.toEntity(ogWorkPlaceTypesDTO);
        ogWorkPlaceTypes = ogWorkPlaceTypesRepository.save(ogWorkPlaceTypes);
        OgWorkPlaceTypesDTO result = ogWorkPlaceTypesMapper.toDto(ogWorkPlaceTypes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ogWorkPlaceTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /og-work-place-types : get all the ogWorkPlaceTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ogWorkPlaceTypes in body
     */
    @GetMapping("/og-work-place-types")
    @Timed
    public ResponseEntity<List<OgWorkPlaceTypesDTO>> getAllOgWorkPlaceTypes(Pageable pageable) {
        log.debug("REST request to get a page of OgWorkPlaceTypes");
        Page<OgWorkPlaceTypes> page = ogWorkPlaceTypesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/og-work-place-types");
        return new ResponseEntity<>(ogWorkPlaceTypesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /og-work-place-types/:id : get the "id" ogWorkPlaceTypes.
     *
     * @param id the id of the ogWorkPlaceTypesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ogWorkPlaceTypesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/og-work-place-types/{id}")
    @Timed
    public ResponseEntity<OgWorkPlaceTypesDTO> getOgWorkPlaceTypes(@PathVariable Long id) {
        log.debug("REST request to get OgWorkPlaceTypes : {}", id);
        OgWorkPlaceTypes ogWorkPlaceTypes = ogWorkPlaceTypesRepository.findOne(id);
        OgWorkPlaceTypesDTO ogWorkPlaceTypesDTO = ogWorkPlaceTypesMapper.toDto(ogWorkPlaceTypes);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ogWorkPlaceTypesDTO));
    }

    /**
     * DELETE  /og-work-place-types/:id : delete the "id" ogWorkPlaceTypes.
     *
     * @param id the id of the ogWorkPlaceTypesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/og-work-place-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteOgWorkPlaceTypes(@PathVariable Long id) {
        log.debug("REST request to delete OgWorkPlaceTypes : {}", id);
        ogWorkPlaceTypesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
