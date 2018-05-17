package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.MdHeaderTypes;

import ba.infostudio.com.repository.MdHeaderTypesRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.MdHeaderTypesDTO;
import ba.infostudio.com.service.mapper.MdHeaderTypesMapper;
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
 * REST controller for managing MdHeaderTypes.
 */
@RestController
@RequestMapping("/api")
public class MdHeaderTypesResource {

    private final Logger log = LoggerFactory.getLogger(MdHeaderTypesResource.class);

    private static final String ENTITY_NAME = "mdHeaderTypes";

    private final MdHeaderTypesRepository mdHeaderTypesRepository;

    private final MdHeaderTypesMapper mdHeaderTypesMapper;

    public MdHeaderTypesResource(MdHeaderTypesRepository mdHeaderTypesRepository, MdHeaderTypesMapper mdHeaderTypesMapper) {
        this.mdHeaderTypesRepository = mdHeaderTypesRepository;
        this.mdHeaderTypesMapper = mdHeaderTypesMapper;
    }

    /**
     * POST  /md-header-types : Create a new mdHeaderTypes.
     *
     * @param mdHeaderTypesDTO the mdHeaderTypesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mdHeaderTypesDTO, or with status 400 (Bad Request) if the mdHeaderTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/md-header-types")
    @Timed
    public ResponseEntity<MdHeaderTypesDTO> createMdHeaderTypes(@Valid @RequestBody MdHeaderTypesDTO mdHeaderTypesDTO) throws URISyntaxException {
        log.debug("REST request to save MdHeaderTypes : {}", mdHeaderTypesDTO);
        if (mdHeaderTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new mdHeaderTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MdHeaderTypes mdHeaderTypes = mdHeaderTypesMapper.toEntity(mdHeaderTypesDTO);
        mdHeaderTypes = mdHeaderTypesRepository.save(mdHeaderTypes);
        MdHeaderTypesDTO result = mdHeaderTypesMapper.toDto(mdHeaderTypes);
        return ResponseEntity.created(new URI("/api/md-header-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /md-header-types : Updates an existing mdHeaderTypes.
     *
     * @param mdHeaderTypesDTO the mdHeaderTypesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mdHeaderTypesDTO,
     * or with status 400 (Bad Request) if the mdHeaderTypesDTO is not valid,
     * or with status 500 (Internal Server Error) if the mdHeaderTypesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/md-header-types")
    @Timed
    public ResponseEntity<MdHeaderTypesDTO> updateMdHeaderTypes(@Valid @RequestBody MdHeaderTypesDTO mdHeaderTypesDTO) throws URISyntaxException {
        log.debug("REST request to update MdHeaderTypes : {}", mdHeaderTypesDTO);
        if (mdHeaderTypesDTO.getId() == null) {
            return createMdHeaderTypes(mdHeaderTypesDTO);
        }
        MdHeaderTypes mdHeaderTypes = mdHeaderTypesMapper.toEntity(mdHeaderTypesDTO);
        mdHeaderTypes = mdHeaderTypesRepository.save(mdHeaderTypes);
        MdHeaderTypesDTO result = mdHeaderTypesMapper.toDto(mdHeaderTypes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mdHeaderTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /md-header-types : get all the mdHeaderTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mdHeaderTypes in body
     */
    @GetMapping("/md-header-types")
    @Timed
    public ResponseEntity<List<MdHeaderTypesDTO>> getAllMdHeaderTypes(Pageable pageable) {
        log.debug("REST request to get a page of MdHeaderTypes");
        Page<MdHeaderTypes> page = mdHeaderTypesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/md-header-types");
        return new ResponseEntity<>(mdHeaderTypesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /md-header-types/:id : get the "id" mdHeaderTypes.
     *
     * @param id the id of the mdHeaderTypesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mdHeaderTypesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/md-header-types/{id}")
    @Timed
    public ResponseEntity<MdHeaderTypesDTO> getMdHeaderTypes(@PathVariable Long id) {
        log.debug("REST request to get MdHeaderTypes : {}", id);
        MdHeaderTypes mdHeaderTypes = mdHeaderTypesRepository.findOne(id);
        MdHeaderTypesDTO mdHeaderTypesDTO = mdHeaderTypesMapper.toDto(mdHeaderTypes);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mdHeaderTypesDTO));
    }

    /**
     * DELETE  /md-header-types/:id : delete the "id" mdHeaderTypes.
     *
     * @param id the id of the mdHeaderTypesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/md-header-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteMdHeaderTypes(@PathVariable Long id) {
        log.debug("REST request to delete MdHeaderTypes : {}", id);
        mdHeaderTypesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
