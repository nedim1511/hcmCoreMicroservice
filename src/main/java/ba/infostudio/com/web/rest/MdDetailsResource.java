package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.MdDetails;

import ba.infostudio.com.repository.MdDetailsRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.MdDetailsDTO;
import ba.infostudio.com.service.mapper.MdDetailsMapper;
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
 * REST controller for managing MdDetails.
 */
@RestController
@RequestMapping("/api")
public class MdDetailsResource {

    private final Logger log = LoggerFactory.getLogger(MdDetailsResource.class);

    private static final String ENTITY_NAME = "mdDetails";

    private final MdDetailsRepository mdDetailsRepository;

    private final MdDetailsMapper mdDetailsMapper;

    public MdDetailsResource(MdDetailsRepository mdDetailsRepository, MdDetailsMapper mdDetailsMapper) {
        this.mdDetailsRepository = mdDetailsRepository;
        this.mdDetailsMapper = mdDetailsMapper;
    }

    /**
     * POST  /md-details : Create a new mdDetails.
     *
     * @param mdDetailsDTO the mdDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mdDetailsDTO, or with status 400 (Bad Request) if the mdDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/md-details")
    @Timed
    public ResponseEntity<MdDetailsDTO> createMdDetails(@Valid @RequestBody MdDetailsDTO mdDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save MdDetails : {}", mdDetailsDTO);
        if (mdDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new mdDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MdDetails mdDetails = mdDetailsMapper.toEntity(mdDetailsDTO);
        mdDetails = mdDetailsRepository.save(mdDetails);
        MdDetailsDTO result = mdDetailsMapper.toDto(mdDetails);
        return ResponseEntity.created(new URI("/api/md-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /md-details : Updates an existing mdDetails.
     *
     * @param mdDetailsDTO the mdDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mdDetailsDTO,
     * or with status 400 (Bad Request) if the mdDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the mdDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/md-details")
    @Timed
    public ResponseEntity<MdDetailsDTO> updateMdDetails(@Valid @RequestBody MdDetailsDTO mdDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update MdDetails : {}", mdDetailsDTO);
        if (mdDetailsDTO.getId() == null) {
            return createMdDetails(mdDetailsDTO);
        }
        MdDetails mdDetails = mdDetailsMapper.toEntity(mdDetailsDTO);
        mdDetails = mdDetailsRepository.save(mdDetails);
        MdDetailsDTO result = mdDetailsMapper.toDto(mdDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mdDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /md-details : get all the mdDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mdDetails in body
     */
    @GetMapping("/md-details")
    @Timed
    public ResponseEntity<List<MdDetailsDTO>> getAllMdDetails(Pageable pageable) {
        log.debug("REST request to get a page of MdDetails");
        Page<MdDetails> page = mdDetailsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/md-details");
        return new ResponseEntity<>(mdDetailsMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /md-details/:id : get the "id" mdDetails.
     *
     * @param id the id of the mdDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mdDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/md-details/{id}")
    @Timed
    public ResponseEntity<MdDetailsDTO> getMdDetails(@PathVariable Long id) {
        log.debug("REST request to get MdDetails : {}", id);
        MdDetails mdDetails = mdDetailsRepository.findOne(id);
        MdDetailsDTO mdDetailsDTO = mdDetailsMapper.toDto(mdDetails);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mdDetailsDTO));
    }

    /**
     * DELETE  /md-details/:id : delete the "id" mdDetails.
     *
     * @param id the id of the mdDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/md-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteMdDetails(@PathVariable Long id) {
        log.debug("REST request to delete MdDetails : {}", id);
        mdDetailsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
