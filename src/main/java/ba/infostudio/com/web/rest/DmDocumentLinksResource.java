package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.DmDocumentLinks;

import ba.infostudio.com.repository.DmDocumentLinksRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.DmDocumentLinksDTO;
import ba.infostudio.com.service.mapper.DmDocumentLinksMapper;
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
 * REST controller for managing DmDocumentLinks.
 */
@RestController
@RequestMapping("/api")
public class DmDocumentLinksResource {

    private final Logger log = LoggerFactory.getLogger(DmDocumentLinksResource.class);

    private static final String ENTITY_NAME = "dmDocumentLinks";

    private final DmDocumentLinksRepository dmDocumentLinksRepository;

    private final DmDocumentLinksMapper dmDocumentLinksMapper;

    public DmDocumentLinksResource(DmDocumentLinksRepository dmDocumentLinksRepository, DmDocumentLinksMapper dmDocumentLinksMapper) {
        this.dmDocumentLinksRepository = dmDocumentLinksRepository;
        this.dmDocumentLinksMapper = dmDocumentLinksMapper;
    }

    /**
     * POST  /dm-document-links : Create a new dmDocumentLinks.
     *
     * @param dmDocumentLinksDTO the dmDocumentLinksDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dmDocumentLinksDTO, or with status 400 (Bad Request) if the dmDocumentLinks has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dm-document-links")
    @Timed
    public ResponseEntity<DmDocumentLinksDTO> createDmDocumentLinks(@RequestBody DmDocumentLinksDTO dmDocumentLinksDTO) throws URISyntaxException {
        log.debug("REST request to save DmDocumentLinks : {}", dmDocumentLinksDTO);
        if (dmDocumentLinksDTO.getId() != null) {
            throw new BadRequestAlertException("A new dmDocumentLinks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DmDocumentLinks dmDocumentLinks = dmDocumentLinksMapper.toEntity(dmDocumentLinksDTO);
        dmDocumentLinks = dmDocumentLinksRepository.save(dmDocumentLinks);
        DmDocumentLinksDTO result = dmDocumentLinksMapper.toDto(dmDocumentLinks);
        return ResponseEntity.created(new URI("/api/dm-document-links/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dm-document-links : Updates an existing dmDocumentLinks.
     *
     * @param dmDocumentLinksDTO the dmDocumentLinksDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dmDocumentLinksDTO,
     * or with status 400 (Bad Request) if the dmDocumentLinksDTO is not valid,
     * or with status 500 (Internal Server Error) if the dmDocumentLinksDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dm-document-links")
    @Timed
    public ResponseEntity<DmDocumentLinksDTO> updateDmDocumentLinks(@RequestBody DmDocumentLinksDTO dmDocumentLinksDTO) throws URISyntaxException {
        log.debug("REST request to update DmDocumentLinks : {}", dmDocumentLinksDTO);
        if (dmDocumentLinksDTO.getId() == null) {
            return createDmDocumentLinks(dmDocumentLinksDTO);
        }
        DmDocumentLinks dmDocumentLinks = dmDocumentLinksMapper.toEntity(dmDocumentLinksDTO);
        dmDocumentLinks = dmDocumentLinksRepository.save(dmDocumentLinks);
        DmDocumentLinksDTO result = dmDocumentLinksMapper.toDto(dmDocumentLinks);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dmDocumentLinksDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dm-document-links : get all the dmDocumentLinks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dmDocumentLinks in body
     */
    @GetMapping("/dm-document-links")
    @Timed
    public ResponseEntity<List<DmDocumentLinksDTO>> getAllDmDocumentLinks(Pageable pageable) {
        log.debug("REST request to get a page of DmDocumentLinks");
        Page<DmDocumentLinks> page = dmDocumentLinksRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dm-document-links");
        return new ResponseEntity<>(dmDocumentLinksMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /dm-document-links/:id : get the "id" dmDocumentLinks.
     *
     * @param id the id of the dmDocumentLinksDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dmDocumentLinksDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dm-document-links/{id}")
    @Timed
    public ResponseEntity<DmDocumentLinksDTO> getDmDocumentLinks(@PathVariable Long id) {
        log.debug("REST request to get DmDocumentLinks : {}", id);
        DmDocumentLinks dmDocumentLinks = dmDocumentLinksRepository.findOne(id);
        DmDocumentLinksDTO dmDocumentLinksDTO = dmDocumentLinksMapper.toDto(dmDocumentLinks);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dmDocumentLinksDTO));
    }

    /**
     * DELETE  /dm-document-links/:id : delete the "id" dmDocumentLinks.
     *
     * @param id the id of the dmDocumentLinksDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dm-document-links/{id}")
    @Timed
    public ResponseEntity<Void> deleteDmDocumentLinks(@PathVariable Long id) {
        log.debug("REST request to delete DmDocumentLinks : {}", id);
        dmDocumentLinksRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
