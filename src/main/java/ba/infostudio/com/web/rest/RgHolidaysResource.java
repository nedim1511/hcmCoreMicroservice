package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.RgHolidays;

import ba.infostudio.com.repository.RgHolidaysRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.RgHolidaysDTO;
import ba.infostudio.com.service.mapper.RgHolidaysMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RgHolidays.
 */
@RestController
@RequestMapping("/api")
public class RgHolidaysResource {

    private final Logger log = LoggerFactory.getLogger(RgHolidaysResource.class);

    private static final String ENTITY_NAME = "rgHolidays";

    private final RgHolidaysRepository rgHolidaysRepository;

    private final RgHolidaysMapper rgHolidaysMapper;

    public RgHolidaysResource(RgHolidaysRepository rgHolidaysRepository, RgHolidaysMapper rgHolidaysMapper) {
        this.rgHolidaysRepository = rgHolidaysRepository;
        this.rgHolidaysMapper = rgHolidaysMapper;
    }

    /**
     * POST  /rg-holidays : Create a new rgHolidays.
     *
     * @param rgHolidaysDTO the rgHolidaysDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rgHolidaysDTO, or with status 400 (Bad Request) if the rgHolidays has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rg-holidays")
    @Timed
    public ResponseEntity<RgHolidaysDTO> createRgHolidays(@RequestBody RgHolidaysDTO rgHolidaysDTO) throws URISyntaxException {
        log.debug("REST request to save RgHolidays : {}", rgHolidaysDTO);
        if (rgHolidaysDTO.getId() != null) {
            throw new BadRequestAlertException("A new rgHolidays cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RgHolidays rgHolidays = rgHolidaysMapper.toEntity(rgHolidaysDTO);
        rgHolidays = rgHolidaysRepository.save(rgHolidays);
        RgHolidaysDTO result = rgHolidaysMapper.toDto(rgHolidays);
        return ResponseEntity.created(new URI("/api/rg-holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rg-holidays : Updates an existing rgHolidays.
     *
     * @param rgHolidaysDTO the rgHolidaysDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rgHolidaysDTO,
     * or with status 400 (Bad Request) if the rgHolidaysDTO is not valid,
     * or with status 500 (Internal Server Error) if the rgHolidaysDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rg-holidays")
    @Timed
    public ResponseEntity<RgHolidaysDTO> updateRgHolidays(@RequestBody RgHolidaysDTO rgHolidaysDTO) throws URISyntaxException {
        log.debug("REST request to update RgHolidays : {}", rgHolidaysDTO);
        if (rgHolidaysDTO.getId() == null) {
            return createRgHolidays(rgHolidaysDTO);
        }
        RgHolidays rgHolidays = rgHolidaysMapper.toEntity(rgHolidaysDTO);
        rgHolidays = rgHolidaysRepository.save(rgHolidays);
        RgHolidaysDTO result = rgHolidaysMapper.toDto(rgHolidays);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rgHolidaysDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rg-holidays : get all the rgHolidays.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rgHolidays in body
     */
    @GetMapping("/rg-holidays")
    @Timed
    public ResponseEntity<List<RgHolidaysDTO>> getAllRgHolidays(Pageable pageable) {
        log.debug("REST request to get a page of RgHolidays");
        Page<RgHolidays> page = rgHolidaysRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rg-holidays");
        return new ResponseEntity<>(rgHolidaysMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /rg-holidays/:id : get the "id" rgHolidays.
     *
     * @param id the id of the rgHolidaysDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rgHolidaysDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rg-holidays/{id}")
    @Timed
    public ResponseEntity<RgHolidaysDTO> getRgHolidays(@PathVariable Long id) {
        log.debug("REST request to get RgHolidays : {}", id);
        RgHolidays rgHolidays = rgHolidaysRepository.findOne(id);
        RgHolidaysDTO rgHolidaysDTO = rgHolidaysMapper.toDto(rgHolidays);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rgHolidaysDTO));
    }

    /**
     * DELETE  /rg-holidays/:id : delete the "id" rgHolidays.
     *
     * @param id the id of the rgHolidaysDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rg-holidays/{id}")
    @Timed
    public ResponseEntity<Void> deleteRgHolidays(@PathVariable Long id) {
        log.debug("REST request to delete RgHolidays : {}", id);
        rgHolidaysRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
