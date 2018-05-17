package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.RgQualifications;

import ba.infostudio.com.repository.RgQualificationsRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.RgQualificationsDTO;
import ba.infostudio.com.service.mapper.RgQualificationsMapper;
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
 * REST controller for managing RgQualifications.
 */
@RestController
@RequestMapping("/api")
public class RgQualificationsResource {

    private final Logger log = LoggerFactory.getLogger(RgQualificationsResource.class);

    private static final String ENTITY_NAME = "rgQualifications";

    private final RgQualificationsRepository rgQualificationsRepository;

    private final RgQualificationsMapper rgQualificationsMapper;

    public RgQualificationsResource(RgQualificationsRepository rgQualificationsRepository, RgQualificationsMapper rgQualificationsMapper) {
        this.rgQualificationsRepository = rgQualificationsRepository;
        this.rgQualificationsMapper = rgQualificationsMapper;
    }

    /**
     * POST  /rg-qualifications : Create a new rgQualifications.
     *
     * @param rgQualificationsDTO the rgQualificationsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rgQualificationsDTO, or with status 400 (Bad Request) if the rgQualifications has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rg-qualifications")
    @Timed
    public ResponseEntity<RgQualificationsDTO> createRgQualifications(@Valid @RequestBody RgQualificationsDTO rgQualificationsDTO) throws URISyntaxException {
        log.debug("REST request to save RgQualifications : {}", rgQualificationsDTO);
        if (rgQualificationsDTO.getId() != null) {
            throw new BadRequestAlertException("A new rgQualifications cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RgQualifications rgQualifications = rgQualificationsMapper.toEntity(rgQualificationsDTO);
        rgQualifications = rgQualificationsRepository.save(rgQualifications);
        RgQualificationsDTO result = rgQualificationsMapper.toDto(rgQualifications);
        return ResponseEntity.created(new URI("/api/rg-qualifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rg-qualifications : Updates an existing rgQualifications.
     *
     * @param rgQualificationsDTO the rgQualificationsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rgQualificationsDTO,
     * or with status 400 (Bad Request) if the rgQualificationsDTO is not valid,
     * or with status 500 (Internal Server Error) if the rgQualificationsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rg-qualifications")
    @Timed
    public ResponseEntity<RgQualificationsDTO> updateRgQualifications(@Valid @RequestBody RgQualificationsDTO rgQualificationsDTO) throws URISyntaxException {
        log.debug("REST request to update RgQualifications : {}", rgQualificationsDTO);
        if (rgQualificationsDTO.getId() == null) {
            return createRgQualifications(rgQualificationsDTO);
        }
        RgQualifications rgQualifications = rgQualificationsMapper.toEntity(rgQualificationsDTO);
        rgQualifications = rgQualificationsRepository.save(rgQualifications);
        RgQualificationsDTO result = rgQualificationsMapper.toDto(rgQualifications);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rgQualificationsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rg-qualifications : get all the rgQualifications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rgQualifications in body
     */
    @GetMapping("/rg-qualifications")
    @Timed
    public ResponseEntity<List<RgQualificationsDTO>> getAllRgQualifications(Pageable pageable) {
        log.debug("REST request to get a page of RgQualifications");
        Page<RgQualifications> page = rgQualificationsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rg-qualifications");
        return new ResponseEntity<>(rgQualificationsMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /rg-qualifications/:id : get the "id" rgQualifications.
     *
     * @param id the id of the rgQualificationsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rgQualificationsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rg-qualifications/{id}")
    @Timed
    public ResponseEntity<RgQualificationsDTO> getRgQualifications(@PathVariable Long id) {
        log.debug("REST request to get RgQualifications : {}", id);
        RgQualifications rgQualifications = rgQualificationsRepository.findOne(id);
        RgQualificationsDTO rgQualificationsDTO = rgQualificationsMapper.toDto(rgQualifications);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rgQualificationsDTO));
    }

    /**
     * DELETE  /rg-qualifications/:id : delete the "id" rgQualifications.
     *
     * @param id the id of the rgQualificationsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rg-qualifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteRgQualifications(@PathVariable Long id) {
        log.debug("REST request to delete RgQualifications : {}", id);
        rgQualificationsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
