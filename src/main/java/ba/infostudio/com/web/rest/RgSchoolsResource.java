package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.RgSchools;

import ba.infostudio.com.repository.RgSchoolsRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.RgSchoolsDTO;
import ba.infostudio.com.service.mapper.RgSchoolsMapper;
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
 * REST controller for managing RgSchools.
 */
@RestController
@RequestMapping("/api")
public class RgSchoolsResource {

    private final Logger log = LoggerFactory.getLogger(RgSchoolsResource.class);

    private static final String ENTITY_NAME = "rgSchools";

    private final RgSchoolsRepository rgSchoolsRepository;

    private final RgSchoolsMapper rgSchoolsMapper;

    public RgSchoolsResource(RgSchoolsRepository rgSchoolsRepository, RgSchoolsMapper rgSchoolsMapper) {
        this.rgSchoolsRepository = rgSchoolsRepository;
        this.rgSchoolsMapper = rgSchoolsMapper;
    }

    /**
     * POST  /rg-schools : Create a new rgSchools.
     *
     * @param rgSchoolsDTO the rgSchoolsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rgSchoolsDTO, or with status 400 (Bad Request) if the rgSchools has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rg-schools")
    @Timed
    public ResponseEntity<RgSchoolsDTO> createRgSchools(@Valid @RequestBody RgSchoolsDTO rgSchoolsDTO) throws URISyntaxException {
        log.debug("REST request to save RgSchools : {}", rgSchoolsDTO);
        if (rgSchoolsDTO.getId() != null) {
            throw new BadRequestAlertException("A new rgSchools cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RgSchools rgSchools = rgSchoolsMapper.toEntity(rgSchoolsDTO);
        rgSchools = rgSchoolsRepository.save(rgSchools);
        RgSchoolsDTO result = rgSchoolsMapper.toDto(rgSchools);
        return ResponseEntity.created(new URI("/api/rg-schools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rg-schools : Updates an existing rgSchools.
     *
     * @param rgSchoolsDTO the rgSchoolsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rgSchoolsDTO,
     * or with status 400 (Bad Request) if the rgSchoolsDTO is not valid,
     * or with status 500 (Internal Server Error) if the rgSchoolsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rg-schools")
    @Timed
    public ResponseEntity<RgSchoolsDTO> updateRgSchools(@Valid @RequestBody RgSchoolsDTO rgSchoolsDTO) throws URISyntaxException {
        log.debug("REST request to update RgSchools : {}", rgSchoolsDTO);
        if (rgSchoolsDTO.getId() == null) {
            return createRgSchools(rgSchoolsDTO);
        }
        RgSchools rgSchools = rgSchoolsMapper.toEntity(rgSchoolsDTO);
        rgSchools = rgSchoolsRepository.save(rgSchools);
        RgSchoolsDTO result = rgSchoolsMapper.toDto(rgSchools);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rgSchoolsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rg-schools : get all the rgSchools.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rgSchools in body
     */
    @GetMapping("/rg-schools")
    @Timed
    public ResponseEntity<List<RgSchoolsDTO>> getAllRgSchools(Pageable pageable) {
        log.debug("REST request to get a page of RgSchools");
        Page<RgSchools> page = rgSchoolsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rg-schools");
        return new ResponseEntity<>(rgSchoolsMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /rg-schools/:id : get the "id" rgSchools.
     *
     * @param id the id of the rgSchoolsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rgSchoolsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rg-schools/{id}")
    @Timed
    public ResponseEntity<RgSchoolsDTO> getRgSchools(@PathVariable Long id) {
        log.debug("REST request to get RgSchools : {}", id);
        RgSchools rgSchools = rgSchoolsRepository.findOne(id);
        RgSchoolsDTO rgSchoolsDTO = rgSchoolsMapper.toDto(rgSchools);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rgSchoolsDTO));
    }

    /**
     * DELETE  /rg-schools/:id : delete the "id" rgSchools.
     *
     * @param id the id of the rgSchoolsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rg-schools/{id}")
    @Timed
    public ResponseEntity<Void> deleteRgSchools(@PathVariable Long id) {
        log.debug("REST request to delete RgSchools : {}", id);
        rgSchoolsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
