package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.MdHeaders;

import ba.infostudio.com.repository.MdHeadersRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.MdHeadersDTO;
import ba.infostudio.com.service.mapper.MdHeadersMapper;
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
 * REST controller for managing MdHeaders.
 */
@RestController
@RequestMapping("/api")
public class MdHeadersResource {

    private final Logger log = LoggerFactory.getLogger(MdHeadersResource.class);

    private static final String ENTITY_NAME = "mdHeaders";

    private final MdHeadersRepository mdHeadersRepository;

    private final MdHeadersMapper mdHeadersMapper;

    public MdHeadersResource(MdHeadersRepository mdHeadersRepository, MdHeadersMapper mdHeadersMapper) {
        this.mdHeadersRepository = mdHeadersRepository;
        this.mdHeadersMapper = mdHeadersMapper;
    }

    /**
     * POST  /md-headers : Create a new mdHeaders.
     *
     * @param mdHeadersDTO the mdHeadersDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mdHeadersDTO, or with status 400 (Bad Request) if the mdHeaders has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/md-headers")
    @Timed
    public ResponseEntity<MdHeadersDTO> createMdHeaders(@Valid @RequestBody MdHeadersDTO mdHeadersDTO) throws URISyntaxException {
        log.debug("REST request to save MdHeaders : {}", mdHeadersDTO);
        if (mdHeadersDTO.getId() != null) {
            throw new BadRequestAlertException("A new mdHeaders cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MdHeaders mdHeaders = mdHeadersMapper.toEntity(mdHeadersDTO);
        mdHeaders = mdHeadersRepository.save(mdHeaders);
        MdHeadersDTO result = mdHeadersMapper.toDto(mdHeaders);
        return ResponseEntity.created(new URI("/api/md-headers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /md-headers : Updates an existing mdHeaders.
     *
     * @param mdHeadersDTO the mdHeadersDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mdHeadersDTO,
     * or with status 400 (Bad Request) if the mdHeadersDTO is not valid,
     * or with status 500 (Internal Server Error) if the mdHeadersDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/md-headers")
    @Timed
    public ResponseEntity<MdHeadersDTO> updateMdHeaders(@Valid @RequestBody MdHeadersDTO mdHeadersDTO) throws URISyntaxException {
        log.debug("REST request to update MdHeaders : {}", mdHeadersDTO);
        if (mdHeadersDTO.getId() == null) {
            return createMdHeaders(mdHeadersDTO);
        }
        MdHeaders mdHeaders = mdHeadersMapper.toEntity(mdHeadersDTO);
        mdHeaders = mdHeadersRepository.save(mdHeaders);
        MdHeadersDTO result = mdHeadersMapper.toDto(mdHeaders);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mdHeadersDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /md-headers : get all the mdHeaders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mdHeaders in body
     */
    @GetMapping("/md-headers")
    @Timed
    public ResponseEntity<List<MdHeadersDTO>> getAllMdHeaders(Pageable pageable) {
        log.debug("REST request to get a page of MdHeaders");
        Page<MdHeaders> page = mdHeadersRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/md-headers");
        return new ResponseEntity<>(mdHeadersMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /md-headers/:id : get the "id" mdHeaders.
     *
     * @param id the id of the mdHeadersDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mdHeadersDTO, or with status 404 (Not Found)
     */
    @GetMapping("/md-headers/{id}")
    @Timed
    public ResponseEntity<MdHeadersDTO> getMdHeaders(@PathVariable Long id) {
        log.debug("REST request to get MdHeaders : {}", id);
        MdHeaders mdHeaders = mdHeadersRepository.findOne(id);
        MdHeadersDTO mdHeadersDTO = mdHeadersMapper.toDto(mdHeaders);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mdHeadersDTO));
    }

    /**
     * DELETE  /md-headers/:id : delete the "id" mdHeaders.
     *
     * @param id the id of the mdHeadersDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/md-headers/{id}")
    @Timed
    public ResponseEntity<Void> deleteMdHeaders(@PathVariable Long id) {
        log.debug("REST request to delete MdHeaders : {}", id);
        mdHeadersRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
