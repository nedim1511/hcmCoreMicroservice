package ba.infostudio.com.service;

import ba.infostudio.com.domain.UserNotifications;
import ba.infostudio.com.repository.UserNotificationsRepository;
import ba.infostudio.com.service.dto.UserNotificationsDTO;
import ba.infostudio.com.service.mapper.UserNotificationsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing UserNotifications.
 */
@Service
@Transactional
public class UserNotificationsService {

    private final Logger log = LoggerFactory.getLogger(UserNotificationsService.class);

    private final UserNotificationsRepository userNotificationsRepository;

    private final UserNotificationsMapper userNotificationsMapper;

    public UserNotificationsService(UserNotificationsRepository userNotificationsRepository, UserNotificationsMapper userNotificationsMapper) {
        this.userNotificationsRepository = userNotificationsRepository;
        this.userNotificationsMapper = userNotificationsMapper;
    }

    /**
     * Save a userNotifications.
     *
     * @param userNotificationsDTO the entity to save
     * @return the persisted entity
     */
    public UserNotificationsDTO save(UserNotificationsDTO userNotificationsDTO) {
        log.debug("Request to save UserNotifications : {}", userNotificationsDTO);
        UserNotifications userNotifications = userNotificationsMapper.toEntity(userNotificationsDTO);
        userNotifications = userNotificationsRepository.save(userNotifications);
        return userNotificationsMapper.toDto(userNotifications);
    }

    /**
     * Get all the userNotifications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserNotificationsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserNotifications");
        return userNotificationsRepository.findAll(pageable)
            .map(userNotificationsMapper::toDto);
    }

    /**
     * Get one userNotifications by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserNotificationsDTO findOne(Long id) {
        log.debug("Request to get UserNotifications : {}", id);
        UserNotifications userNotifications = userNotificationsRepository.findOne(id);
        return userNotificationsMapper.toDto(userNotifications);
    }

    /**
     * Delete the userNotifications by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserNotifications : {}", id);
        userNotificationsRepository.delete(id);
    }
}
