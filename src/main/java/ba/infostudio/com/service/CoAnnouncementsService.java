package ba.infostudio.com.service;

import ba.infostudio.com.domain.CoAnnouncements;
import ba.infostudio.com.repository.CoAnnouncementsRepository;
import ba.infostudio.com.service.dto.CoAnnouncementsDTO;
import ba.infostudio.com.service.mapper.CoAnnouncementsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CoAnnouncements.
 */
@Service
@Transactional
public class CoAnnouncementsService {

    private final Logger log = LoggerFactory.getLogger(CoAnnouncementsService.class);

    private final CoAnnouncementsRepository coAnnouncementsRepository;

    private final CoAnnouncementsMapper coAnnouncementsMapper;

    public CoAnnouncementsService(CoAnnouncementsRepository coAnnouncementsRepository, CoAnnouncementsMapper coAnnouncementsMapper) {
        this.coAnnouncementsRepository = coAnnouncementsRepository;
        this.coAnnouncementsMapper = coAnnouncementsMapper;
    }

    /**
     * Save a coAnnouncements.
     *
     * @param coAnnouncementsDTO the entity to save
     * @return the persisted entity
     */
    public CoAnnouncementsDTO save(CoAnnouncementsDTO coAnnouncementsDTO) {
        log.debug("Request to save CoAnnouncements : {}", coAnnouncementsDTO);
        CoAnnouncements coAnnouncements = coAnnouncementsMapper.toEntity(coAnnouncementsDTO);
        coAnnouncements = coAnnouncementsRepository.save(coAnnouncements);
        return coAnnouncementsMapper.toDto(coAnnouncements);
    }

    /**
     * Get all the coAnnouncements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CoAnnouncementsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CoAnnouncements");
        return coAnnouncementsRepository.findAll(pageable)
            .map(coAnnouncementsMapper::toDto);
    }

    /**
     * Get one coAnnouncements by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CoAnnouncementsDTO findOne(Long id) {
        log.debug("Request to get CoAnnouncements : {}", id);
        CoAnnouncements coAnnouncements = coAnnouncementsRepository.findOne(id);
        return coAnnouncementsMapper.toDto(coAnnouncements);
    }

    /**
     * Delete the coAnnouncements by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CoAnnouncements : {}", id);
        coAnnouncementsRepository.delete(id);
    }
}
