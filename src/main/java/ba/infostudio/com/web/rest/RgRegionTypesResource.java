package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.RgRegionTypes;

import ba.infostudio.com.repository.RgRegionTypesRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.RgRegionTypesDTO;
import ba.infostudio.com.service.mapper.RgRegionTypesMapper;
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
 * REST controller for managing RgRegionTypes.
 */
@RestController
@RequestMapping("/api")
public class RgRegionTypesResource {

    private final Logger log = LoggerFactory.getLogger(RgRegionTypesResource.class);

    private static final String ENTITY_NAME = "rgRegionTypes";

    private final RgRegionTypesRepository rgRegionTypesRepository;

    private final RgRegionTypesMapper rgRegionTypesMapper;

    public RgRegionTypesResource(RgRegionTypesRepository rgRegionTypesRepository, RgRegionTypesMapper rgRegionTypesMapper) {
        this.rgRegionTypesRepository = rgRegionTypesRepository;
        this.rgRegionTypesMapper = rgRegionTypesMapper;
    }

    /**
     * POST  /rg-region-types : Create a new rgRegionTypes.
     *
     * @param rgRegionTypesDTO the rgRegionTypesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rgRegionTypesDTO, or with status 400 (Bad Request) if the rgRegionTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rg-region-types")
    @Timed
    public ResponseEntity<RgRegionTypesDTO> createRgRegionTypes(@Valid @RequestBody RgRegionTypesDTO rgRegionTypesDTO) throws URISyntaxException {
        log.debug("REST request to save RgRegionTypes : {}", rgRegionTypesDTO);
        if (rgRegionTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new rgRegionTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RgRegionTypes rgRegionTypes = rgRegionTypesMapper.toEntity(rgRegionTypesDTO);
        rgRegionTypes = rgRegionTypesRepository.save(rgRegionTypes);
        RgRegionTypesDTO result = rgRegionTypesMapper.toDto(rgRegionTypes);
        return ResponseEntity.created(new URI("/api/rg-region-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rg-region-types : Updates an existing rgRegionTypes.
     *
     * @param rgRegionTypesDTO the rgRegionTypesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rgRegionTypesDTO,
     * or with status 400 (Bad Request) if the rgRegionTypesDTO is not valid,
     * or with status 500 (Internal Server Error) if the rgRegionTypesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rg-region-types")
    @Timed
    public ResponseEntity<RgRegionTypesDTO> updateRgRegionTypes(@Valid @RequestBody RgRegionTypesDTO rgRegionTypesDTO) throws URISyntaxException {
        log.debug("REST request to update RgRegionTypes : {}", rgRegionTypesDTO);
        if (rgRegionTypesDTO.getId() == null) {
            return createRgRegionTypes(rgRegionTypesDTO);
        }
        RgRegionTypes rgRegionTypes = rgRegionTypesMapper.toEntity(rgRegionTypesDTO);
        rgRegionTypes = rgRegionTypesRepository.save(rgRegionTypes);
        RgRegionTypesDTO result = rgRegionTypesMapper.toDto(rgRegionTypes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rgRegionTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rg-region-types : get all the rgRegionTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rgRegionTypes in body
     */
    @GetMapping("/rg-region-types")
    @Timed
    public ResponseEntity<List<RgRegionTypesDTO>> getAllRgRegionTypes(Pageable pageable) {
        log.debug("REST request to get a page of RgRegionTypes");
        Page<RgRegionTypes> page = rgRegionTypesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rg-region-types");
        return new ResponseEntity<>(rgRegionTypesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /rg-region-types/:id : get the "id" rgRegionTypes.
     *
     * @param id the id of the rgRegionTypesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rgRegionTypesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rg-region-types/{id}")
    @Timed
    public ResponseEntity<RgRegionTypesDTO> getRgRegionTypes(@PathVariable Long id) {
        log.debug("REST request to get RgRegionTypes : {}", id);
        RgRegionTypes rgRegionTypes = rgRegionTypesRepository.findOne(id);
        RgRegionTypesDTO rgRegionTypesDTO = rgRegionTypesMapper.toDto(rgRegionTypes);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rgRegionTypesDTO));
    }

    /**
     * DELETE  /rg-region-types/:id : delete the "id" rgRegionTypes.
     *
     * @param id the id of the rgRegionTypesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rg-region-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteRgRegionTypes(@PathVariable Long id) {
        log.debug("REST request to delete RgRegionTypes : {}", id);
        rgRegionTypesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
