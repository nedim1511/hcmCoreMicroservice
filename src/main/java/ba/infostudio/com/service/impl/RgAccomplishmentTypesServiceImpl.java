package ba.infostudio.com.service.impl;

import ba.infostudio.com.service.RgAccomplishmentTypesService;
import ba.infostudio.com.domain.RgAccomplishmentTypes;
import ba.infostudio.com.repository.RgAccomplishmentTypesRepository;
import ba.infostudio.com.service.dto.RgAccomplishmentTypesDTO;
import ba.infostudio.com.service.mapper.RgAccomplishmentTypesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing RgAccomplishmentTypes.
 */
@Service
@Transactional
public class RgAccomplishmentTypesServiceImpl implements RgAccomplishmentTypesService {

    private final Logger log = LoggerFactory.getLogger(RgAccomplishmentTypesServiceImpl.class);

    private final RgAccomplishmentTypesRepository rgAccomplishmentTypesRepository;

    private final RgAccomplishmentTypesMapper rgAccomplishmentTypesMapper;

    public RgAccomplishmentTypesServiceImpl(RgAccomplishmentTypesRepository rgAccomplishmentTypesRepository, RgAccomplishmentTypesMapper rgAccomplishmentTypesMapper) {
        this.rgAccomplishmentTypesRepository = rgAccomplishmentTypesRepository;
        this.rgAccomplishmentTypesMapper = rgAccomplishmentTypesMapper;
    }

    /**
     * Save a rgAccomplishmentTypes.
     *
     * @param rgAccomplishmentTypesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RgAccomplishmentTypesDTO save(RgAccomplishmentTypesDTO rgAccomplishmentTypesDTO) {
        log.debug("Request to save RgAccomplishmentTypes : {}", rgAccomplishmentTypesDTO);
        RgAccomplishmentTypes rgAccomplishmentTypes = rgAccomplishmentTypesMapper.toEntity(rgAccomplishmentTypesDTO);
        rgAccomplishmentTypes = rgAccomplishmentTypesRepository.save(rgAccomplishmentTypes);
        return rgAccomplishmentTypesMapper.toDto(rgAccomplishmentTypes);
    }

    /**
     * Get all the rgAccomplishmentTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RgAccomplishmentTypesDTO> findAll() {
        log.debug("Request to get all RgAccomplishmentTypes");
        return rgAccomplishmentTypesRepository.findAll().stream()
            .map(rgAccomplishmentTypesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one rgAccomplishmentTypes by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RgAccomplishmentTypesDTO> findOne(Long id) {
        log.debug("Request to get RgAccomplishmentTypes : {}", id);
        return rgAccomplishmentTypesRepository.findById(id)
            .map(rgAccomplishmentTypesMapper::toDto);
    }

    /**
     * Delete the rgAccomplishmentTypes by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RgAccomplishmentTypes : {}", id);
        rgAccomplishmentTypesRepository.deleteById(id);
    }
}
