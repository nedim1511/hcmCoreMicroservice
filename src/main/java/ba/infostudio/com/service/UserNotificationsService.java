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
 * Service Implementation for managing UserNofitifications.
 */
@Service
@Transactional
public class UserNotificationsService {

    private final Logger log = LoggerFactory.getLogger(UserNotificationsService.class);

    private final UserNotificationsRepository userNofitificationsRepository;

    private final UserNotificationsMapper userNofitificationsMapper;

    public UserNotificationsService(UserNotificationsRepository userNofitificationsRepository, UserNotificationsMapper userNofitificationsMapper) {
        this.userNofitificationsRepository = userNofitificationsRepository;
        this.userNofitificationsMapper = userNofitificationsMapper;
    }

    /**
     * Save a userNofitifications.
     *
     * @param userNofitificationsDTO the entity to save
     * @return the persisted entity
     */
    public UserNotificationsDTO save(UserNotificationsDTO userNofitificationsDTO) {
        log.debug("Request to save UserNotifications : {}", userNofitificationsDTO);
        UserNotifications userNofitifications = userNofitificationsMapper.toEntity(userNofitificationsDTO);
        userNofitifications = userNofitificationsRepository.save(userNofitifications);
        return userNofitificationsMapper.toDto(userNofitifications);
    }

    /**
     * Get all the userNofitifications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserNotificationsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserNofitifications");
        return userNofitificationsRepository.findAll(pageable)
            .map(userNofitificationsMapper::toDto);
    }

    /**
     * Get one userNofitifications by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserNotificationsDTO findOne(Long id) {
        log.debug("Request to get UserNotifications : {}", id);
        UserNotifications userNofitifications = userNofitificationsRepository.findOne(id);
        return userNofitificationsMapper.toDto(userNofitifications);
    }

    /**
     * Delete the userNofitifications by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserNotifications : {}", id);
        userNofitificationsRepository.delete(id);
    }
}
