package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.RgIdentificationTypes;

import ba.infostudio.com.repository.RgIdentificationTypesRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.RgIdentificationTypesDTO;
import ba.infostudio.com.service.mapper.RgIdentificationTypesMapper;
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
 * REST controller for managing RgIdentificationTypes.
 */
@RestController
@RequestMapping("/api")
public class RgIdentificationTypesResource {

    private final Logger log = LoggerFactory.getLogger(RgIdentificationTypesResource.class);

    private static final String ENTITY_NAME = "rgIdentificationTypes";

    private final RgIdentificationTypesRepository rgIdentificationTypesRepository;

    private final RgIdentificationTypesMapper rgIdentificationTypesMapper;

    public RgIdentificationTypesResource(RgIdentificationTypesRepository rgIdentificationTypesRepository, RgIdentificationTypesMapper rgIdentificationTypesMapper) {
        this.rgIdentificationTypesRepository = rgIdentificationTypesRepository;
        this.rgIdentificationTypesMapper = rgIdentificationTypesMapper;
    }

    /**
     * POST  /rg-identification-types : Create a new rgIdentificationTypes.
     *
     * @param rgIdentificationTypesDTO the rgIdentificationTypesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rgIdentificationTypesDTO, or with status 400 (Bad Request) if the rgIdentificationTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rg-identification-types")
    @Timed
    public ResponseEntity<RgIdentificationTypesDTO> createRgIdentificationTypes(@Valid @RequestBody RgIdentificationTypesDTO rgIdentificationTypesDTO) throws URISyntaxException {
        log.debug("REST request to save RgIdentificationTypes : {}", rgIdentificationTypesDTO);
        if (rgIdentificationTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new rgIdentificationTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RgIdentificationTypes rgIdentificationTypes = rgIdentificationTypesMapper.toEntity(rgIdentificationTypesDTO);
        rgIdentificationTypes = rgIdentificationTypesRepository.save(rgIdentificationTypes);
        RgIdentificationTypesDTO result = rgIdentificationTypesMapper.toDto(rgIdentificationTypes);
        return ResponseEntity.created(new URI("/api/rg-identification-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rg-identification-types : Updates an existing rgIdentificationTypes.
     *
     * @param rgIdentificationTypesDTO the rgIdentificationTypesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rgIdentificationTypesDTO,
     * or with status 400 (Bad Request) if the rgIdentificationTypesDTO is not valid,
     * or with status 500 (Internal Server Error) if the rgIdentificationTypesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rg-identification-types")
    @Timed
    public ResponseEntity<RgIdentificationTypesDTO> updateRgIdentificationTypes(@Valid @RequestBody RgIdentificationTypesDTO rgIdentificationTypesDTO) throws URISyntaxException {
        log.debug("REST request to update RgIdentificationTypes : {}", rgIdentificationTypesDTO);
        if (rgIdentificationTypesDTO.getId() == null) {
            return createRgIdentificationTypes(rgIdentificationTypesDTO);
        }
        RgIdentificationTypes rgIdentificationTypes = rgIdentificationTypesMapper.toEntity(rgIdentificationTypesDTO);
        rgIdentificationTypes = rgIdentificationTypesRepository.save(rgIdentificationTypes);
        RgIdentificationTypesDTO result = rgIdentificationTypesMapper.toDto(rgIdentificationTypes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rgIdentificationTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rg-identification-types : get all the rgIdentificationTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rgIdentificationTypes in body
     */
    @GetMapping("/rg-identification-types")
    @Timed
    public ResponseEntity<List<RgIdentificationTypesDTO>> getAllRgIdentificationTypes(Pageable pageable) {
        log.debug("REST request to get a page of RgIdentificationTypes");
        Page<RgIdentificationTypes> page = rgIdentificationTypesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rg-identification-types");
        return new ResponseEntity<>(rgIdentificationTypesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /rg-identification-types/:id : get the "id" rgIdentificationTypes.
     *
     * @param id the id of the rgIdentificationTypesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rgIdentificationTypesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rg-identification-types/{id}")
    @Timed
    public ResponseEntity<RgIdentificationTypesDTO> getRgIdentificationTypes(@PathVariable Long id) {
        log.debug("REST request to get RgIdentificationTypes : {}", id);
        RgIdentificationTypes rgIdentificationTypes = rgIdentificationTypesRepository.findOne(id);
        RgIdentificationTypesDTO rgIdentificationTypesDTO = rgIdentificationTypesMapper.toDto(rgIdentificationTypes);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rgIdentificationTypesDTO));
    }

    /**
     * DELETE  /rg-identification-types/:id : delete the "id" rgIdentificationTypes.
     *
     * @param id the id of the rgIdentificationTypesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rg-identification-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteRgIdentificationTypes(@PathVariable Long id) {
        log.debug("REST request to delete RgIdentificationTypes : {}", id);
        rgIdentificationTypesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
