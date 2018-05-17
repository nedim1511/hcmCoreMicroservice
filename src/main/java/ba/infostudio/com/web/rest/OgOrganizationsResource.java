package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.OgOrganizations;

import ba.infostudio.com.repository.OgOrganizationsRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.OgOrganizationsDTO;
import ba.infostudio.com.service.mapper.OgOrganizationsMapper;
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
 * REST controller for managing OgOrganizations.
 */
@RestController
@RequestMapping("/api")
public class OgOrganizationsResource {

    private final Logger log = LoggerFactory.getLogger(OgOrganizationsResource.class);

    private static final String ENTITY_NAME = "ogOrganizations";

    private final OgOrganizationsRepository ogOrganizationsRepository;

    private final OgOrganizationsMapper ogOrganizationsMapper;

    public OgOrganizationsResource(OgOrganizationsRepository ogOrganizationsRepository, OgOrganizationsMapper ogOrganizationsMapper) {
        this.ogOrganizationsRepository = ogOrganizationsRepository;
        this.ogOrganizationsMapper = ogOrganizationsMapper;
    }

    /**
     * POST  /og-organizations : Create a new ogOrganizations.
     *
     * @param ogOrganizationsDTO the ogOrganizationsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ogOrganizationsDTO, or with status 400 (Bad Request) if the ogOrganizations has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/og-organizations")
    @Timed
    public ResponseEntity<OgOrganizationsDTO> createOgOrganizations(@Valid @RequestBody OgOrganizationsDTO ogOrganizationsDTO) throws URISyntaxException {
        log.debug("REST request to save OgOrganizations : {}", ogOrganizationsDTO);
        if (ogOrganizationsDTO.getId() != null) {
            throw new BadRequestAlertException("A new ogOrganizations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OgOrganizations ogOrganizations = ogOrganizationsMapper.toEntity(ogOrganizationsDTO);
        ogOrganizations = ogOrganizationsRepository.save(ogOrganizations);
        OgOrganizationsDTO result = ogOrganizationsMapper.toDto(ogOrganizations);
        return ResponseEntity.created(new URI("/api/og-organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /og-organizations : Updates an existing ogOrganizations.
     *
     * @param ogOrganizationsDTO the ogOrganizationsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ogOrganizationsDTO,
     * or with status 400 (Bad Request) if the ogOrganizationsDTO is not valid,
     * or with status 500 (Internal Server Error) if the ogOrganizationsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/og-organizations")
    @Timed
    public ResponseEntity<OgOrganizationsDTO> updateOgOrganizations(@Valid @RequestBody OgOrganizationsDTO ogOrganizationsDTO) throws URISyntaxException {
        log.debug("REST request to update OgOrganizations : {}", ogOrganizationsDTO);
        if (ogOrganizationsDTO.getId() == null) {
            return createOgOrganizations(ogOrganizationsDTO);
        }
        OgOrganizations ogOrganizations = ogOrganizationsMapper.toEntity(ogOrganizationsDTO);
        ogOrganizations = ogOrganizationsRepository.save(ogOrganizations);
        OgOrganizationsDTO result = ogOrganizationsMapper.toDto(ogOrganizations);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ogOrganizationsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /og-organizations : get all the ogOrganizations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ogOrganizations in body
     */
    @GetMapping("/og-organizations")
    @Timed
    public ResponseEntity<List<OgOrganizationsDTO>> getAllOgOrganizations(Pageable pageable) {
        log.debug("REST request to get a page of OgOrganizations");
        Page<OgOrganizations> page = ogOrganizationsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/og-organizations");
        return new ResponseEntity<>(ogOrganizationsMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /og-organizations/:id : get the "id" ogOrganizations.
     *
     * @param id the id of the ogOrganizationsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ogOrganizationsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/og-organizations/{id}")
    @Timed
    public ResponseEntity<OgOrganizationsDTO> getOgOrganizations(@PathVariable Long id) {
        log.debug("REST request to get OgOrganizations : {}", id);
        OgOrganizations ogOrganizations = ogOrganizationsRepository.findOne(id);
        OgOrganizationsDTO ogOrganizationsDTO = ogOrganizationsMapper.toDto(ogOrganizations);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ogOrganizationsDTO));
    }

    /**
     * DELETE  /og-organizations/:id : delete the "id" ogOrganizations.
     *
     * @param id the id of the ogOrganizationsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/og-organizations/{id}")
    @Timed
    public ResponseEntity<Void> deleteOgOrganizations(@PathVariable Long id) {
        log.debug("REST request to delete OgOrganizations : {}", id);
        ogOrganizationsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
