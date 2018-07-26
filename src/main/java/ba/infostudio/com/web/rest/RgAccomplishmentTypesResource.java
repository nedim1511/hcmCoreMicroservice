package ba.infostudio.com.web.rest;

import ba.infostudio.com.domain.RgAccomplishmentTypes;
import ba.infostudio.com.repository.RgAccomplishmentTypesRepository;
import ba.infostudio.com.service.mapper.RgAccomplishmentTypesMapper;
import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.service.dto.RgAccomplishmentTypesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RgAccomplishmentTypes.
 */
@RestController
@RequestMapping("/api")
public class RgAccomplishmentTypesResource {

    private final Logger log = LoggerFactory.getLogger(RgAccomplishmentTypesResource.class);

    private static final String ENTITY_NAME = "rgAccomplishmentTypes";

    private final RgAccomplishmentTypesRepository rgAccomplishmentTypesRepository;

    private final RgAccomplishmentTypesMapper rgAccomplishmentTypesMapper;

    public RgAccomplishmentTypesResource(RgAccomplishmentTypesRepository rgAccomplishmentTypesRepository,
                                         RgAccomplishmentTypesMapper rgAccomplishmentTypesMapper) {
        this.rgAccomplishmentTypesRepository = rgAccomplishmentTypesRepository;
        this.rgAccomplishmentTypesMapper = rgAccomplishmentTypesMapper;
    }

    /**
     * POST  /rg-accomplishment-types : Create a new rgAccomplishmentTypes.
     *
     * @param rgAccomplishmentTypesDTO the rgAccomplishmentTypesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rgAccomplishmentTypesDTO, or with status 400 (Bad Request) if the rgAccomplishmentTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rg-accomplishment-types")
    @Timed
    public ResponseEntity<RgAccomplishmentTypesDTO> createRgAccomplishmentTypes(@Valid @RequestBody RgAccomplishmentTypesDTO rgAccomplishmentTypesDTO) throws URISyntaxException {
        log.debug("REST request to save RgAccomplishmentTypes : {}", rgAccomplishmentTypesDTO);
        if (rgAccomplishmentTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new rgAccomplishmentTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RgAccomplishmentTypes res = rgAccomplishmentTypesMapper.toEntity(rgAccomplishmentTypesDTO);
        RgAccomplishmentTypes resultNonDTO = rgAccomplishmentTypesRepository.save(res);
        RgAccomplishmentTypesDTO result = rgAccomplishmentTypesMapper.toDto(resultNonDTO);
        return ResponseEntity.created(new URI("/api/rg-accomplishment-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rg-accomplishment-types : Updates an existing rgAccomplishmentTypes.
     *
     * @param rgAccomplishmentTypesDTO the rgAccomplishmentTypesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rgAccomplishmentTypesDTO,
     * or with status 400 (Bad Request) if the rgAccomplishmentTypesDTO is not valid,
     * or with status 500 (Internal Server Error) if the rgAccomplishmentTypesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rg-accomplishment-types")
    @Timed
    public ResponseEntity<RgAccomplishmentTypesDTO> updateRgAccomplishmentTypes(@Valid @RequestBody RgAccomplishmentTypesDTO rgAccomplishmentTypesDTO) throws URISyntaxException {
        log.debug("REST request to update RgAccomplishmentTypes : {}", rgAccomplishmentTypesDTO);
        if (rgAccomplishmentTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RgAccomplishmentTypes res = rgAccomplishmentTypesMapper.toEntity(rgAccomplishmentTypesDTO);
        RgAccomplishmentTypes resultNonDTO = rgAccomplishmentTypesRepository.save(res);
        RgAccomplishmentTypesDTO result = rgAccomplishmentTypesMapper.toDto(res);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rgAccomplishmentTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rg-accomplishment-types : get all the rgAccomplishmentTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rgAccomplishmentTypes in body
     */
    @GetMapping("/rg-accomplishment-types")
    @Timed
    public List<RgAccomplishmentTypesDTO> getAllRgAccomplishmentTypes() {
        log.debug("REST request to get all RgAccomplishmentTypes");
        List<RgAccomplishmentTypes> allAccomplishmentTypes = rgAccomplishmentTypesRepository.findAll();
        return rgAccomplishmentTypesMapper.toDto(allAccomplishmentTypes);
    }

    /**
     * GET  /rg-accomplishment-types/:id : get the "id" rgAccomplishmentTypes.
     *
     * @param id the id of the rgAccomplishmentTypesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rgAccomplishmentTypesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rg-accomplishment-types/{id}")
    @Timed
    public ResponseEntity<RgAccomplishmentTypesDTO> getRgAccomplishmentTypes(@PathVariable Long id) {
        log.debug("REST request to get RgAccomplishmentTypes : {}", id);
        RgAccomplishmentTypes res = rgAccomplishmentTypesRepository.findOne(id);
        RgAccomplishmentTypesDTO rgAccomplishmentTypesDTO = rgAccomplishmentTypesMapper.toDto(res);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rgAccomplishmentTypesDTO));
    }

    /**
     * DELETE  /rg-accomplishment-types/:id : delete the "id" rgAccomplishmentTypes.
     *
     * @param id the id of the rgAccomplishmentTypesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rg-accomplishment-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteRgAccomplishmentTypes(@PathVariable Long id) {
        log.debug("REST request to delete RgAccomplishmentTypes : {}", id);
        rgAccomplishmentTypesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
