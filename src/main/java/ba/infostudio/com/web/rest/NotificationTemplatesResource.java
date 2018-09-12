package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.service.NotificationTemplatesService;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.NotificationTemplatesDTO;
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
 * REST controller for managing NotificationTemplates.
 */
@RestController
@RequestMapping("/api")
public class NotificationTemplatesResource {

    private final Logger log = LoggerFactory.getLogger(NotificationTemplatesResource.class);

    private static final String ENTITY_NAME = "notificationTemplates";

    private final NotificationTemplatesService notificationTemplatesService;

    public NotificationTemplatesResource(NotificationTemplatesService notificationTemplatesService) {
        this.notificationTemplatesService = notificationTemplatesService;
    }

    /**
     * POST  /notification-templates : Create a new notificationTemplates.
     *
     * @param notificationTemplatesDTO the notificationTemplatesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new notificationTemplatesDTO, or with status 400 (Bad Request) if the notificationTemplates has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/notification-templates")
    @Timed
    public ResponseEntity<NotificationTemplatesDTO> createNotificationTemplates(@Valid @RequestBody NotificationTemplatesDTO notificationTemplatesDTO) throws URISyntaxException {
        log.debug("REST request to save NotificationTemplates : {}", notificationTemplatesDTO);
        if (notificationTemplatesDTO.getId() != null) {
            throw new BadRequestAlertException("A new notificationTemplates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotificationTemplatesDTO result = notificationTemplatesService.save(notificationTemplatesDTO);
        return ResponseEntity.created(new URI("/api/notification-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notification-templates : Updates an existing notificationTemplates.
     *
     * @param notificationTemplatesDTO the notificationTemplatesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated notificationTemplatesDTO,
     * or with status 400 (Bad Request) if the notificationTemplatesDTO is not valid,
     * or with status 500 (Internal Server Error) if the notificationTemplatesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/notification-templates")
    @Timed
    public ResponseEntity<NotificationTemplatesDTO> updateNotificationTemplates(@Valid @RequestBody NotificationTemplatesDTO notificationTemplatesDTO) throws URISyntaxException {
        log.debug("REST request to update NotificationTemplates : {}", notificationTemplatesDTO);
        if (notificationTemplatesDTO.getId() == null) {
            return createNotificationTemplates(notificationTemplatesDTO);
        }
        NotificationTemplatesDTO result = notificationTemplatesService.save(notificationTemplatesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, notificationTemplatesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /notification-templates : get all the notificationTemplates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of notificationTemplates in body
     */
    @GetMapping("/notification-templates")
    @Timed
    public ResponseEntity<List<NotificationTemplatesDTO>> getAllNotificationTemplates(Pageable pageable) {
        log.debug("REST request to get a page of NotificationTemplates");
        Page<NotificationTemplatesDTO> page = notificationTemplatesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notification-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /notification-templates/:id : get the "id" notificationTemplates.
     *
     * @param id the id of the notificationTemplatesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the notificationTemplatesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/notification-templates/{id}")
    @Timed
    public ResponseEntity<NotificationTemplatesDTO> getNotificationTemplates(@PathVariable Long id) {
        log.debug("REST request to get NotificationTemplates : {}", id);
        NotificationTemplatesDTO notificationTemplatesDTO = notificationTemplatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notificationTemplatesDTO));
    }

    /**
     * DELETE  /notification-templates/:id : delete the "id" notificationTemplates.
     *
     * @param id the id of the notificationTemplatesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/notification-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteNotificationTemplates(@PathVariable Long id) {
        log.debug("REST request to delete NotificationTemplates : {}", id);
        notificationTemplatesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
