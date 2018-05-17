package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.DmDocumentTypes;

import ba.infostudio.com.repository.DmDocumentTypesRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.DmDocumentTypesDTO;
import ba.infostudio.com.service.mapper.DmDocumentTypesMapper;
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
 * REST controller for managing DmDocumentTypes.
 */
@RestController
@RequestMapping("/api")
public class DmDocumentTypesResource {

    private final Logger log = LoggerFactory.getLogger(DmDocumentTypesResource.class);

    private static final String ENTITY_NAME = "dmDocumentTypes";

    private final DmDocumentTypesRepository dmDocumentTypesRepository;

    private final DmDocumentTypesMapper dmDocumentTypesMapper;

    public DmDocumentTypesResource(DmDocumentTypesRepository dmDocumentTypesRepository, DmDocumentTypesMapper dmDocumentTypesMapper) {
        this.dmDocumentTypesRepository = dmDocumentTypesRepository;
        this.dmDocumentTypesMapper = dmDocumentTypesMapper;
    }

    /**
     * POST  /dm-document-types : Create a new dmDocumentTypes.
     *
     * @param dmDocumentTypesDTO the dmDocumentTypesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dmDocumentTypesDTO, or with status 400 (Bad Request) if the dmDocumentTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dm-document-types")
    @Timed
    public ResponseEntity<DmDocumentTypesDTO> createDmDocumentTypes(@Valid @RequestBody DmDocumentTypesDTO dmDocumentTypesDTO) throws URISyntaxException {
        log.debug("REST request to save DmDocumentTypes : {}", dmDocumentTypesDTO);
        if (dmDocumentTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new dmDocumentTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DmDocumentTypes dmDocumentTypes = dmDocumentTypesMapper.toEntity(dmDocumentTypesDTO);
        dmDocumentTypes = dmDocumentTypesRepository.save(dmDocumentTypes);
        DmDocumentTypesDTO result = dmDocumentTypesMapper.toDto(dmDocumentTypes);
        return ResponseEntity.created(new URI("/api/dm-document-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dm-document-types : Updates an existing dmDocumentTypes.
     *
     * @param dmDocumentTypesDTO the dmDocumentTypesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dmDocumentTypesDTO,
     * or with status 400 (Bad Request) if the dmDocumentTypesDTO is not valid,
     * or with status 500 (Internal Server Error) if the dmDocumentTypesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dm-document-types")
    @Timed
    public ResponseEntity<DmDocumentTypesDTO> updateDmDocumentTypes(@Valid @RequestBody DmDocumentTypesDTO dmDocumentTypesDTO) throws URISyntaxException {
        log.debug("REST request to update DmDocumentTypes : {}", dmDocumentTypesDTO);
        if (dmDocumentTypesDTO.getId() == null) {
            return createDmDocumentTypes(dmDocumentTypesDTO);
        }
        DmDocumentTypes dmDocumentTypes = dmDocumentTypesMapper.toEntity(dmDocumentTypesDTO);
        dmDocumentTypes = dmDocumentTypesRepository.save(dmDocumentTypes);
        DmDocumentTypesDTO result = dmDocumentTypesMapper.toDto(dmDocumentTypes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dmDocumentTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dm-document-types : get all the dmDocumentTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dmDocumentTypes in body
     */
    @GetMapping("/dm-document-types")
    @Timed
    public ResponseEntity<List<DmDocumentTypesDTO>> getAllDmDocumentTypes(Pageable pageable) {
        log.debug("REST request to get a page of DmDocumentTypes");
        Page<DmDocumentTypes> page = dmDocumentTypesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dm-document-types");
        return new ResponseEntity<>(dmDocumentTypesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /dm-document-types/:id : get the "id" dmDocumentTypes.
     *
     * @param id the id of the dmDocumentTypesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dmDocumentTypesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dm-document-types/{id}")
    @Timed
    public ResponseEntity<DmDocumentTypesDTO> getDmDocumentTypes(@PathVariable Long id) {
        log.debug("REST request to get DmDocumentTypes : {}", id);
        DmDocumentTypes dmDocumentTypes = dmDocumentTypesRepository.findOne(id);
        DmDocumentTypesDTO dmDocumentTypesDTO = dmDocumentTypesMapper.toDto(dmDocumentTypes);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dmDocumentTypesDTO));
    }

    /**
     * DELETE  /dm-document-types/:id : delete the "id" dmDocumentTypes.
     *
     * @param id the id of the dmDocumentTypesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dm-document-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteDmDocumentTypes(@PathVariable Long id) {
        log.debug("REST request to delete DmDocumentTypes : {}", id);
        dmDocumentTypesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
