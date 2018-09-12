package ba.infostudio.com.service;

import ba.infostudio.com.domain.NotificationTemplates;
import ba.infostudio.com.repository.NotificationTemplatesRepository;
import ba.infostudio.com.service.dto.NotificationTemplatesDTO;
import ba.infostudio.com.service.mapper.NotificationTemplatesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing NotificationTemplates.
 */
@Service
@Transactional
public class NotificationTemplatesService {

    private final Logger log = LoggerFactory.getLogger(NotificationTemplatesService.class);

    private final NotificationTemplatesRepository notificationTemplatesRepository;

    private final NotificationTemplatesMapper notificationTemplatesMapper;

    public NotificationTemplatesService(NotificationTemplatesRepository notificationTemplatesRepository, NotificationTemplatesMapper notificationTemplatesMapper) {
        this.notificationTemplatesRepository = notificationTemplatesRepository;
        this.notificationTemplatesMapper = notificationTemplatesMapper;
    }

    /**
     * Save a notificationTemplates.
     *
     * @param notificationTemplatesDTO the entity to save
     * @return the persisted entity
     */
    public NotificationTemplatesDTO save(NotificationTemplatesDTO notificationTemplatesDTO) {
        log.debug("Request to save NotificationTemplates : {}", notificationTemplatesDTO);
        NotificationTemplates notificationTemplates = notificationTemplatesMapper.toEntity(notificationTemplatesDTO);
        notificationTemplates = notificationTemplatesRepository.save(notificationTemplates);
        return notificationTemplatesMapper.toDto(notificationTemplates);
    }

    /**
     * Get all the notificationTemplates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NotificationTemplatesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NotificationTemplates");
        return notificationTemplatesRepository.findAll(pageable)
            .map(notificationTemplatesMapper::toDto);
    }

    /**
     * Get one notificationTemplates by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public NotificationTemplatesDTO findOne(Long id) {
        log.debug("Request to get NotificationTemplates : {}", id);
        NotificationTemplates notificationTemplates = notificationTemplatesRepository.findOne(id);
        return notificationTemplatesMapper.toDto(notificationTemplates);
    }

    /**
     * Delete the notificationTemplates by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NotificationTemplates : {}", id);
        notificationTemplatesRepository.delete(id);
    }
}
