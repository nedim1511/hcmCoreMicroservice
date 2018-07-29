package ba.infostudio.com.web.rest;

import ba.infostudio.com.domain.CoAnnouncements;
import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.service.CoAnnouncementsService;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.CoAnnouncementsDTO;
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

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CoAnnouncements.
 */
@RestController
@RequestMapping("/api")
public class CoAnnouncementsResource {

    private final Logger log = LoggerFactory.getLogger(CoAnnouncementsResource.class);

    private static final String ENTITY_NAME = "coAnnouncements";

    private final CoAnnouncementsService coAnnouncementsService;


    public CoAnnouncementsResource(CoAnnouncementsService coAnnouncementsService) {
        this.coAnnouncementsService = coAnnouncementsService;
    }

    /**
     * POST  /co-announcements : Create a new coAnnouncements.
     *
     * @param coAnnouncementsDTO the coAnnouncementsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coAnnouncementsDTO, or with status 400 (Bad Request) if the coAnnouncements has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/co-announcements")
    @Timed
    public ResponseEntity<CoAnnouncementsDTO> createCoAnnouncements(@Valid @RequestBody CoAnnouncementsDTO coAnnouncementsDTO) throws URISyntaxException {
        log.debug("REST request to save CoAnnouncements : {}", coAnnouncementsDTO);
        if (coAnnouncementsDTO.getId() != null) {
            throw new BadRequestAlertException("A new coAnnouncements cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoAnnouncementsDTO result = coAnnouncementsService.save(coAnnouncementsDTO);
        return ResponseEntity.created(new URI("/api/co-announcements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /co-announcements : Updates an existing coAnnouncements.
     *
     * @param coAnnouncementsDTO the coAnnouncementsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coAnnouncementsDTO,
     * or with status 400 (Bad Request) if the coAnnouncementsDTO is not valid,
     * or with status 500 (Internal Server Error) if the coAnnouncementsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/co-announcements")
    @Timed
    public ResponseEntity<CoAnnouncementsDTO> updateCoAnnouncements(@Valid @RequestBody CoAnnouncementsDTO coAnnouncementsDTO) throws URISyntaxException {
        log.debug("REST request to update CoAnnouncements : {}", coAnnouncementsDTO);
        if (coAnnouncementsDTO.getId() == null) {
            return createCoAnnouncements(coAnnouncementsDTO);
        }
        CoAnnouncementsDTO result = coAnnouncementsService.save(coAnnouncementsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coAnnouncementsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /co-announcements : get all the coAnnouncements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of coAnnouncements in body
     */
    @GetMapping("/co-announcements")
    @Timed
    public ResponseEntity<List<CoAnnouncementsDTO>> getAllCoAnnouncements(Pageable pageable) {
        log.debug("REST request to get a page of CoAnnouncements");
        Page<CoAnnouncementsDTO> page = coAnnouncementsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/co-announcements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /co-announcements/valid : get valid the coAnnouncements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of coAnnouncements in body
     */
    @GetMapping("/co-announcements/valid")
    @Timed
    public ResponseEntity<List<CoAnnouncementsDTO>> getAllValidCoAnnouncements() {
        log.debug("REST request to get a page of CoAnnouncements");
        List<CoAnnouncementsDTO> announcements = coAnnouncementsService.valid(LocalDate.now());
        return new ResponseEntity<>(announcements, null, HttpStatus.OK);
    }

    /**
     * GET  /co-announcements/:id : get the "id" coAnnouncements.
     *
     * @param id the id of the coAnnouncementsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coAnnouncementsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/co-announcements/{id}")
    @Timed
    public ResponseEntity<CoAnnouncementsDTO> getCoAnnouncements(@PathVariable Long id) {
        log.debug("REST request to get CoAnnouncements : {}", id);
        CoAnnouncementsDTO coAnnouncementsDTO = coAnnouncementsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coAnnouncementsDTO));
    }

    /**
     * DELETE  /co-announcements/:id : delete the "id" coAnnouncements.
     *
     * @param id the id of the coAnnouncementsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/co-announcements/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoAnnouncements(@PathVariable Long id) {
        log.debug("REST request to delete CoAnnouncements : {}", id);
        coAnnouncementsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
