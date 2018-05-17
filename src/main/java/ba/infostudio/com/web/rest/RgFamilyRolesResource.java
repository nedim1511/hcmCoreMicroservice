package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.RgFamilyRoles;

import ba.infostudio.com.repository.RgFamilyRolesRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.RgFamilyRolesDTO;
import ba.infostudio.com.service.mapper.RgFamilyRolesMapper;
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
 * REST controller for managing RgFamilyRoles.
 */
@RestController
@RequestMapping("/api")
public class RgFamilyRolesResource {

    private final Logger log = LoggerFactory.getLogger(RgFamilyRolesResource.class);

    private static final String ENTITY_NAME = "rgFamilyRoles";

    private final RgFamilyRolesRepository rgFamilyRolesRepository;

    private final RgFamilyRolesMapper rgFamilyRolesMapper;

    public RgFamilyRolesResource(RgFamilyRolesRepository rgFamilyRolesRepository, RgFamilyRolesMapper rgFamilyRolesMapper) {
        this.rgFamilyRolesRepository = rgFamilyRolesRepository;
        this.rgFamilyRolesMapper = rgFamilyRolesMapper;
    }

    /**
     * POST  /rg-family-roles : Create a new rgFamilyRoles.
     *
     * @param rgFamilyRolesDTO the rgFamilyRolesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rgFamilyRolesDTO, or with status 400 (Bad Request) if the rgFamilyRoles has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rg-family-roles")
    @Timed
    public ResponseEntity<RgFamilyRolesDTO> createRgFamilyRoles(@Valid @RequestBody RgFamilyRolesDTO rgFamilyRolesDTO) throws URISyntaxException {
        log.debug("REST request to save RgFamilyRoles : {}", rgFamilyRolesDTO);
        if (rgFamilyRolesDTO.getId() != null) {
            throw new BadRequestAlertException("A new rgFamilyRoles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RgFamilyRoles rgFamilyRoles = rgFamilyRolesMapper.toEntity(rgFamilyRolesDTO);
        rgFamilyRoles = rgFamilyRolesRepository.save(rgFamilyRoles);
        RgFamilyRolesDTO result = rgFamilyRolesMapper.toDto(rgFamilyRoles);
        return ResponseEntity.created(new URI("/api/rg-family-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rg-family-roles : Updates an existing rgFamilyRoles.
     *
     * @param rgFamilyRolesDTO the rgFamilyRolesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rgFamilyRolesDTO,
     * or with status 400 (Bad Request) if the rgFamilyRolesDTO is not valid,
     * or with status 500 (Internal Server Error) if the rgFamilyRolesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rg-family-roles")
    @Timed
    public ResponseEntity<RgFamilyRolesDTO> updateRgFamilyRoles(@Valid @RequestBody RgFamilyRolesDTO rgFamilyRolesDTO) throws URISyntaxException {
        log.debug("REST request to update RgFamilyRoles : {}", rgFamilyRolesDTO);
        if (rgFamilyRolesDTO.getId() == null) {
            return createRgFamilyRoles(rgFamilyRolesDTO);
        }
        RgFamilyRoles rgFamilyRoles = rgFamilyRolesMapper.toEntity(rgFamilyRolesDTO);
        rgFamilyRoles = rgFamilyRolesRepository.save(rgFamilyRoles);
        RgFamilyRolesDTO result = rgFamilyRolesMapper.toDto(rgFamilyRoles);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rgFamilyRolesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rg-family-roles : get all the rgFamilyRoles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rgFamilyRoles in body
     */
    @GetMapping("/rg-family-roles")
    @Timed
    public ResponseEntity<List<RgFamilyRolesDTO>> getAllRgFamilyRoles(Pageable pageable) {
        log.debug("REST request to get a page of RgFamilyRoles");
        Page<RgFamilyRoles> page = rgFamilyRolesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rg-family-roles");
        return new ResponseEntity<>(rgFamilyRolesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /rg-family-roles/:id : get the "id" rgFamilyRoles.
     *
     * @param id the id of the rgFamilyRolesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rgFamilyRolesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rg-family-roles/{id}")
    @Timed
    public ResponseEntity<RgFamilyRolesDTO> getRgFamilyRoles(@PathVariable Long id) {
        log.debug("REST request to get RgFamilyRoles : {}", id);
        RgFamilyRoles rgFamilyRoles = rgFamilyRolesRepository.findOne(id);
        RgFamilyRolesDTO rgFamilyRolesDTO = rgFamilyRolesMapper.toDto(rgFamilyRoles);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rgFamilyRolesDTO));
    }

    /**
     * DELETE  /rg-family-roles/:id : delete the "id" rgFamilyRoles.
     *
     * @param id the id of the rgFamilyRolesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rg-family-roles/{id}")
    @Timed
    public ResponseEntity<Void> deleteRgFamilyRoles(@PathVariable Long id) {
        log.debug("REST request to delete RgFamilyRoles : {}", id);
        rgFamilyRolesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
