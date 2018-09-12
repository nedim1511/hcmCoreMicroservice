package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.service.UserNotificationsService;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.web.rest.util.PaginationUtil;
import ba.infostudio.com.service.dto.UserNotificationsDTO;
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
 * REST controller for managing UserNotifications.
 */
@RestController
@RequestMapping("/api")
public class UserNotificationsResource {

    private final Logger log = LoggerFactory.getLogger(UserNotificationsResource.class);

    private static final String ENTITY_NAME = "UserNotifications";

    private final UserNotificationsService userNofitificationsService;

    public UserNotificationsResource(UserNotificationsService userNofitificationsService) {
        this.userNofitificationsService = userNofitificationsService;
    }

    /**
     * POST  /user-nofitifications : Create a new userNofitifications.
     *
     * @param userNofitificationsDTO the userNofitificationsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userNofitificationsDTO, or with status 400 (Bad Request) if the userNofitifications has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-nofitifications")
    @Timed
    public ResponseEntity<UserNotificationsDTO> createUserNofitifications(@Valid @RequestBody UserNotificationsDTO userNofitificationsDTO) throws URISyntaxException {
        log.debug("REST request to save UserNofitifications : {}", userNofitificationsDTO);
        if (userNofitificationsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userNofitifications cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserNotificationsDTO result = userNofitificationsService.save(userNofitificationsDTO);
        return ResponseEntity.created(new URI("/api/user-nofitifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-nofitifications : Updates an existing userNofitifications.
     *
     * @param userNofitificationsDTO the userNofitificationsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userNofitificationsDTO,
     * or with status 400 (Bad Request) if the userNofitificationsDTO is not valid,
     * or with status 500 (Internal Server Error) if the userNofitificationsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-nofitifications")
    @Timed
    public ResponseEntity<UserNotificationsDTO> updateUserNofitifications(@Valid @RequestBody UserNotificationsDTO userNofitificationsDTO) throws URISyntaxException {
        log.debug("REST request to update UserNofitifications : {}", userNofitificationsDTO);
        if (userNofitificationsDTO.getId() == null) {
            return createUserNofitifications(userNofitificationsDTO);
        }
        UserNotificationsDTO result = userNofitificationsService.save(userNofitificationsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userNofitificationsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-nofitifications : get all the userNofitifications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userNofitifications in body
     */
    @GetMapping("/user-nofitifications")
    @Timed
    public ResponseEntity<List<UserNotificationsDTO>> getAllUserNofitifications(Pageable pageable) {
        log.debug("REST request to get a page of UserNofitifications");
        Page<UserNotificationsDTO> page = userNofitificationsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-nofitifications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-nofitifications/:id : get the "id" userNofitifications.
     *
     * @param id the id of the userNofitificationsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userNofitificationsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-nofitifications/{id}")
    @Timed
    public ResponseEntity<UserNotificationsDTO> getUserNofitifications(@PathVariable Long id) {
        log.debug("REST request to get UserNofitifications : {}", id);
        UserNotificationsDTO userNofitificationsDTO = userNofitificationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userNofitificationsDTO));
    }

    /**
     * DELETE  /user-nofitifications/:id : delete the "id" userNofitifications.
     *
     * @param id the id of the userNofitificationsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-nofitifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserNofitifications(@PathVariable Long id) {
        log.debug("REST request to delete UserNotifications : {}", id);
        userNofitificationsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
