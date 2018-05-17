package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.RgContactTypes;

import ba.infostudio.com.repository.RgContactTypesRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.RgContactTypesDTO;
import ba.infostudio.com.service.mapper.RgContactTypesMapper;
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
 * REST controller for managing RgContactTypes.
 */
@RestController
@RequestMapping("/api")
public class RgContactTypesResource {

    private final Logger log = LoggerFactory.getLogger(RgContactTypesResource.class);

    private static final String ENTITY_NAME = "rgContactTypes";

    private final RgContactTypesRepository rgContactTypesRepository;

    private final RgContactTypesMapper rgContactTypesMapper;

    public RgContactTypesResource(RgContactTypesRepository rgContactTypesRepository, RgContactTypesMapper rgContactTypesMapper) {
        this.rgContactTypesRepository = rgContactTypesRepository;
        this.rgContactTypesMapper = rgContactTypesMapper;
    }

    /**
     * POST  /rg-contact-types : Create a new rgContactTypes.
     *
     * @param rgContactTypesDTO the rgContactTypesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rgContactTypesDTO, or with status 400 (Bad Request) if the rgContactTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rg-contact-types")
    @Timed
    public ResponseEntity<RgContactTypesDTO> createRgContactTypes(@Valid @RequestBody RgContactTypesDTO rgContactTypesDTO) throws URISyntaxException {
        log.debug("REST request to save RgContactTypes : {}", rgContactTypesDTO);
        if (rgContactTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new rgContactTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RgContactTypes rgContactTypes = rgContactTypesMapper.toEntity(rgContactTypesDTO);
        rgContactTypes = rgContactTypesRepository.save(rgContactTypes);
        RgContactTypesDTO result = rgContactTypesMapper.toDto(rgContactTypes);
        return ResponseEntity.created(new URI("/api/rg-contact-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rg-contact-types : Updates an existing rgContactTypes.
     *
     * @param rgContactTypesDTO the rgContactTypesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rgContactTypesDTO,
     * or with status 400 (Bad Request) if the rgContactTypesDTO is not valid,
     * or with status 500 (Internal Server Error) if the rgContactTypesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rg-contact-types")
    @Timed
    public ResponseEntity<RgContactTypesDTO> updateRgContactTypes(@Valid @RequestBody RgContactTypesDTO rgContactTypesDTO) throws URISyntaxException {
        log.debug("REST request to update RgContactTypes : {}", rgContactTypesDTO);
        if (rgContactTypesDTO.getId() == null) {
            return createRgContactTypes(rgContactTypesDTO);
        }
        RgContactTypes rgContactTypes = rgContactTypesMapper.toEntity(rgContactTypesDTO);
        rgContactTypes = rgContactTypesRepository.save(rgContactTypes);
        RgContactTypesDTO result = rgContactTypesMapper.toDto(rgContactTypes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rgContactTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rg-contact-types : get all the rgContactTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rgContactTypes in body
     */
    @GetMapping("/rg-contact-types")
    @Timed
    public ResponseEntity<List<RgContactTypesDTO>> getAllRgContactTypes(Pageable pageable) {
        log.debug("REST request to get a page of RgContactTypes");
        Page<RgContactTypes> page = rgContactTypesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rg-contact-types");
        return new ResponseEntity<>(rgContactTypesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /rg-contact-types/:id : get the "id" rgContactTypes.
     *
     * @param id the id of the rgContactTypesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rgContactTypesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rg-contact-types/{id}")
    @Timed
    public ResponseEntity<RgContactTypesDTO> getRgContactTypes(@PathVariable Long id) {
        log.debug("REST request to get RgContactTypes : {}", id);
        RgContactTypes rgContactTypes = rgContactTypesRepository.findOne(id);
        RgContactTypesDTO rgContactTypesDTO = rgContactTypesMapper.toDto(rgContactTypes);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rgContactTypesDTO));
    }

    /**
     * DELETE  /rg-contact-types/:id : delete the "id" rgContactTypes.
     *
     * @param id the id of the rgContactTypesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rg-contact-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteRgContactTypes(@PathVariable Long id) {
        log.debug("REST request to delete RgContactTypes : {}", id);
        rgContactTypesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
