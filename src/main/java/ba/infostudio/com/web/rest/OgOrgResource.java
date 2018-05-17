package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.OgOrg;

import ba.infostudio.com.repository.OgOrgRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.OgOrgDTO;
import ba.infostudio.com.service.mapper.OgOrgMapper;
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
 * REST controller for managing OgOrg.
 */
@RestController
@RequestMapping("/api")
public class OgOrgResource {

    private final Logger log = LoggerFactory.getLogger(OgOrgResource.class);

    private static final String ENTITY_NAME = "ogOrg";

    private final OgOrgRepository ogOrgRepository;

    private final OgOrgMapper ogOrgMapper;

    public OgOrgResource(OgOrgRepository ogOrgRepository, OgOrgMapper ogOrgMapper) {
        this.ogOrgRepository = ogOrgRepository;
        this.ogOrgMapper = ogOrgMapper;
    }

    /**
     * POST  /og-orgs : Create a new ogOrg.
     *
     * @param ogOrgDTO the ogOrgDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ogOrgDTO, or with status 400 (Bad Request) if the ogOrg has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/og-orgs")
    @Timed
    public ResponseEntity<OgOrgDTO> createOgOrg(@Valid @RequestBody OgOrgDTO ogOrgDTO) throws URISyntaxException {
        log.debug("REST request to save OgOrg : {}", ogOrgDTO);
        if (ogOrgDTO.getId() != null) {
            throw new BadRequestAlertException("A new ogOrg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OgOrg ogOrg = ogOrgMapper.toEntity(ogOrgDTO);
        ogOrg = ogOrgRepository.save(ogOrg);
        OgOrgDTO result = ogOrgMapper.toDto(ogOrg);
        return ResponseEntity.created(new URI("/api/og-orgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /og-orgs : Updates an existing ogOrg.
     *
     * @param ogOrgDTO the ogOrgDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ogOrgDTO,
     * or with status 400 (Bad Request) if the ogOrgDTO is not valid,
     * or with status 500 (Internal Server Error) if the ogOrgDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/og-orgs")
    @Timed
    public ResponseEntity<OgOrgDTO> updateOgOrg(@Valid @RequestBody OgOrgDTO ogOrgDTO) throws URISyntaxException {
        log.debug("REST request to update OgOrg : {}", ogOrgDTO);
        if (ogOrgDTO.getId() == null) {
            return createOgOrg(ogOrgDTO);
        }
        OgOrg ogOrg = ogOrgMapper.toEntity(ogOrgDTO);
        ogOrg = ogOrgRepository.save(ogOrg);
        OgOrgDTO result = ogOrgMapper.toDto(ogOrg);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ogOrgDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /og-orgs : get all the ogOrgs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ogOrgs in body
     */
    @GetMapping("/og-orgs")
    @Timed
    public ResponseEntity<List<OgOrgDTO>> getAllOgOrgs(Pageable pageable) {
        log.debug("REST request to get a page of OgOrgs");
        Page<OgOrg> page = ogOrgRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/og-orgs");
        return new ResponseEntity<>(ogOrgMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /og-orgs/:id : get the "id" ogOrg.
     *
     * @param id the id of the ogOrgDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ogOrgDTO, or with status 404 (Not Found)
     */
    @GetMapping("/og-orgs/{id}")
    @Timed
    public ResponseEntity<OgOrgDTO> getOgOrg(@PathVariable Long id) {
        log.debug("REST request to get OgOrg : {}", id);
        OgOrg ogOrg = ogOrgRepository.findOne(id);
        OgOrgDTO ogOrgDTO = ogOrgMapper.toDto(ogOrg);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ogOrgDTO));
    }

    /**
     * DELETE  /og-orgs/:id : delete the "id" ogOrg.
     *
     * @param id the id of the ogOrgDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/og-orgs/{id}")
    @Timed
    public ResponseEntity<Void> deleteOgOrg(@PathVariable Long id) {
        log.debug("REST request to delete OgOrg : {}", id);
        ogOrgRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
