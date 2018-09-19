package ba.infostudio.com.web.rest;

import ba.infostudio.com.domain.UserNotifications;
import ba.infostudio.com.repository.UserNotificationsRepository;
import ba.infostudio.com.service.mapper.UserNotificationsMapper;
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

    private static final String ENTITY_NAME = "userNotifications";

    private final UserNotificationsService userNotificationsService;

    private final UserNotificationsRepository userNotificationsRepository;

    private final UserNotificationsMapper userNotificationsMapper;

    public UserNotificationsResource(UserNotificationsService userNotificationsService,
                                     UserNotificationsRepository userNotificationsRepository,
                                     UserNotificationsMapper userNotificationsMapper) {
        this.userNotificationsService = userNotificationsService;
        this.userNotificationsRepository = userNotificationsRepository;
        this.userNotificationsMapper = userNotificationsMapper;
    }

    /**
     * POST  /user-notifications : Create a new userNotifications.
     *
     * @param userNotificationsDTO the userNotificationsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userNotificationsDTO, or with status 400 (Bad Request) if the userNotifications has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-notifications")
    @Timed
    public ResponseEntity<UserNotificationsDTO> createUserNotifications(@Valid @RequestBody UserNotificationsDTO userNotificationsDTO) throws URISyntaxException {
        log.debug("REST request to save UserNotifications : {}", userNotificationsDTO);
        if (userNotificationsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userNotifications cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserNotificationsDTO result = userNotificationsService.save(userNotificationsDTO);
        return ResponseEntity.created(new URI("/api/user-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-notifications : Updates an existing userNotifications.
     *
     * @param userNotificationsDTO the userNotificationsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userNotificationsDTO,
     * or with status 400 (Bad Request) if the userNotificationsDTO is not valid,
     * or with status 500 (Internal Server Error) if the userNotificationsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-notifications")
    @Timed
    public ResponseEntity<UserNotificationsDTO> updateUserNotifications(@Valid @RequestBody UserNotificationsDTO userNotificationsDTO) throws URISyntaxException {
        log.debug("REST request to update UserNotifications : {}", userNotificationsDTO);
        if (userNotificationsDTO.getId() == null) {
            return createUserNotifications(userNotificationsDTO);
        }
        UserNotificationsDTO result = userNotificationsService.save(userNotificationsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userNotificationsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-notifications : get all the userNotifications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userNotifications in body
     */
    @GetMapping("/user-notifications")
    @Timed
    public ResponseEntity<List<UserNotificationsDTO>> getAllUserNotifications(Pageable pageable) {
        log.debug("REST request to get a page of UserNotifications");
        Page<UserNotificationsDTO> page = userNotificationsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-notifications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-notifications/:id : get the "id" userNotifications.
     *
     * @param id the id of the userNotificationsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userNotificationsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-notifications/{id}")
    @Timed
    public ResponseEntity<UserNotificationsDTO> getUserNotifications(@PathVariable Long id) {
        log.debug("REST request to get UserNotifications : {}", id);
        UserNotificationsDTO userNotificationsDTO = userNotificationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userNotificationsDTO));
    }

    @GetMapping("/user-notifications/user/{userId}")
    @Timed
    public ResponseEntity<List<UserNotificationsDTO>> getUserNotificationsByUserId(@PathVariable Long userId) {
        log.debug("REST request to get UserNotifications : {}", userId);
        List<UserNotifications> userNotifications = this.userNotificationsRepository.findByIdUser(userId);
        List<UserNotificationsDTO> userNotificationsDTO = this.userNotificationsMapper.toDto(userNotifications);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userNotificationsDTO));
    }


    /**
     * DELETE  /user-notifications/:id : delete the "id" userNotifications.
     *
     * @param id the id of the userNotificationsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-notifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserNotifications(@PathVariable Long id) {
        log.debug("REST request to delete UserNotifications : {}", id);
        userNotificationsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
