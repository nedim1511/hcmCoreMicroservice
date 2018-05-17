package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.MdDataTypes;

import ba.infostudio.com.repository.MdDataTypesRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.MdDataTypesDTO;
import ba.infostudio.com.service.mapper.MdDataTypesMapper;
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
 * REST controller for managing MdDataTypes.
 */
@RestController
@RequestMapping("/api")
public class MdDataTypesResource {

    private final Logger log = LoggerFactory.getLogger(MdDataTypesResource.class);

    private static final String ENTITY_NAME = "mdDataTypes";

    private final MdDataTypesRepository mdDataTypesRepository;

    private final MdDataTypesMapper mdDataTypesMapper;

    public MdDataTypesResource(MdDataTypesRepository mdDataTypesRepository, MdDataTypesMapper mdDataTypesMapper) {
        this.mdDataTypesRepository = mdDataTypesRepository;
        this.mdDataTypesMapper = mdDataTypesMapper;
    }

    /**
     * POST  /md-data-types : Create a new mdDataTypes.
     *
     * @param mdDataTypesDTO the mdDataTypesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mdDataTypesDTO, or with status 400 (Bad Request) if the mdDataTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/md-data-types")
    @Timed
    public ResponseEntity<MdDataTypesDTO> createMdDataTypes(@Valid @RequestBody MdDataTypesDTO mdDataTypesDTO) throws URISyntaxException {
        log.debug("REST request to save MdDataTypes : {}", mdDataTypesDTO);
        if (mdDataTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new mdDataTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MdDataTypes mdDataTypes = mdDataTypesMapper.toEntity(mdDataTypesDTO);
        mdDataTypes = mdDataTypesRepository.save(mdDataTypes);
        MdDataTypesDTO result = mdDataTypesMapper.toDto(mdDataTypes);
        return ResponseEntity.created(new URI("/api/md-data-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /md-data-types : Updates an existing mdDataTypes.
     *
     * @param mdDataTypesDTO the mdDataTypesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mdDataTypesDTO,
     * or with status 400 (Bad Request) if the mdDataTypesDTO is not valid,
     * or with status 500 (Internal Server Error) if the mdDataTypesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/md-data-types")
    @Timed
    public ResponseEntity<MdDataTypesDTO> updateMdDataTypes(@Valid @RequestBody MdDataTypesDTO mdDataTypesDTO) throws URISyntaxException {
        log.debug("REST request to update MdDataTypes : {}", mdDataTypesDTO);
        if (mdDataTypesDTO.getId() == null) {
            return createMdDataTypes(mdDataTypesDTO);
        }
        MdDataTypes mdDataTypes = mdDataTypesMapper.toEntity(mdDataTypesDTO);
        mdDataTypes = mdDataTypesRepository.save(mdDataTypes);
        MdDataTypesDTO result = mdDataTypesMapper.toDto(mdDataTypes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mdDataTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /md-data-types : get all the mdDataTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mdDataTypes in body
     */
    @GetMapping("/md-data-types")
    @Timed
    public ResponseEntity<List<MdDataTypesDTO>> getAllMdDataTypes(Pageable pageable) {
        log.debug("REST request to get a page of MdDataTypes");
        Page<MdDataTypes> page = mdDataTypesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/md-data-types");
        return new ResponseEntity<>(mdDataTypesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /md-data-types/:id : get the "id" mdDataTypes.
     *
     * @param id the id of the mdDataTypesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mdDataTypesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/md-data-types/{id}")
    @Timed
    public ResponseEntity<MdDataTypesDTO> getMdDataTypes(@PathVariable Long id) {
        log.debug("REST request to get MdDataTypes : {}", id);
        MdDataTypes mdDataTypes = mdDataTypesRepository.findOne(id);
        MdDataTypesDTO mdDataTypesDTO = mdDataTypesMapper.toDto(mdDataTypes);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mdDataTypesDTO));
    }

    /**
     * DELETE  /md-data-types/:id : delete the "id" mdDataTypes.
     *
     * @param id the id of the mdDataTypesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/md-data-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteMdDataTypes(@PathVariable Long id) {
        log.debug("REST request to delete MdDataTypes : {}", id);
        mdDataTypesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
