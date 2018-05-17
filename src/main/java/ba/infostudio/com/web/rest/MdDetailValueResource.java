package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.MdDetailValue;

import ba.infostudio.com.repository.MdDetailValueRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.MdDetailValueDTO;
import ba.infostudio.com.service.mapper.MdDetailValueMapper;
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
 * REST controller for managing MdDetailValue.
 */
@RestController
@RequestMapping("/api")
public class MdDetailValueResource {

    private final Logger log = LoggerFactory.getLogger(MdDetailValueResource.class);

    private static final String ENTITY_NAME = "mdDetailValue";

    private final MdDetailValueRepository mdDetailValueRepository;

    private final MdDetailValueMapper mdDetailValueMapper;

    public MdDetailValueResource(MdDetailValueRepository mdDetailValueRepository, MdDetailValueMapper mdDetailValueMapper) {
        this.mdDetailValueRepository = mdDetailValueRepository;
        this.mdDetailValueMapper = mdDetailValueMapper;
    }

    /**
     * POST  /md-detail-values : Create a new mdDetailValue.
     *
     * @param mdDetailValueDTO the mdDetailValueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mdDetailValueDTO, or with status 400 (Bad Request) if the mdDetailValue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/md-detail-values")
    @Timed
    public ResponseEntity<MdDetailValueDTO> createMdDetailValue(@Valid @RequestBody MdDetailValueDTO mdDetailValueDTO) throws URISyntaxException {
        log.debug("REST request to save MdDetailValue : {}", mdDetailValueDTO);
        if (mdDetailValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new mdDetailValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MdDetailValue mdDetailValue = mdDetailValueMapper.toEntity(mdDetailValueDTO);
        mdDetailValue = mdDetailValueRepository.save(mdDetailValue);
        MdDetailValueDTO result = mdDetailValueMapper.toDto(mdDetailValue);
        return ResponseEntity.created(new URI("/api/md-detail-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /md-detail-values : Updates an existing mdDetailValue.
     *
     * @param mdDetailValueDTO the mdDetailValueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mdDetailValueDTO,
     * or with status 400 (Bad Request) if the mdDetailValueDTO is not valid,
     * or with status 500 (Internal Server Error) if the mdDetailValueDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/md-detail-values")
    @Timed
    public ResponseEntity<MdDetailValueDTO> updateMdDetailValue(@Valid @RequestBody MdDetailValueDTO mdDetailValueDTO) throws URISyntaxException {
        log.debug("REST request to update MdDetailValue : {}", mdDetailValueDTO);
        if (mdDetailValueDTO.getId() == null) {
            return createMdDetailValue(mdDetailValueDTO);
        }
        MdDetailValue mdDetailValue = mdDetailValueMapper.toEntity(mdDetailValueDTO);
        mdDetailValue = mdDetailValueRepository.save(mdDetailValue);
        MdDetailValueDTO result = mdDetailValueMapper.toDto(mdDetailValue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mdDetailValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /md-detail-values : get all the mdDetailValues.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mdDetailValues in body
     */
    @GetMapping("/md-detail-values")
    @Timed
    public ResponseEntity<List<MdDetailValueDTO>> getAllMdDetailValues(Pageable pageable) {
        log.debug("REST request to get a page of MdDetailValues");
        Page<MdDetailValue> page = mdDetailValueRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/md-detail-values");
        return new ResponseEntity<>(mdDetailValueMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /md-detail-values/:id : get the "id" mdDetailValue.
     *
     * @param id the id of the mdDetailValueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mdDetailValueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/md-detail-values/{id}")
    @Timed
    public ResponseEntity<MdDetailValueDTO> getMdDetailValue(@PathVariable Long id) {
        log.debug("REST request to get MdDetailValue : {}", id);
        MdDetailValue mdDetailValue = mdDetailValueRepository.findOne(id);
        MdDetailValueDTO mdDetailValueDTO = mdDetailValueMapper.toDto(mdDetailValue);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mdDetailValueDTO));
    }

    /**
     * DELETE  /md-detail-values/:id : delete the "id" mdDetailValue.
     *
     * @param id the id of the mdDetailValueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/md-detail-values/{id}")
    @Timed
    public ResponseEntity<Void> deleteMdDetailValue(@PathVariable Long id) {
        log.debug("REST request to delete MdDetailValue : {}", id);
        mdDetailValueRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
