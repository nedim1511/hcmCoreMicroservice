package ba.infostudio.com.service;

import ba.infostudio.com.domain.RgCurrency;
import ba.infostudio.com.repository.RgCurrencyRepository;
import ba.infostudio.com.service.dto.RgCurrencyDTO;
import ba.infostudio.com.service.mapper.RgCurrencyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing RgCurrency.
 */
@Service
@Transactional
public class RgCurrencyService {

    private final Logger log = LoggerFactory.getLogger(RgCurrencyService.class);

    private final RgCurrencyRepository rgCurrencyRepository;

    private final RgCurrencyMapper rgCurrencyMapper;

    public RgCurrencyService(RgCurrencyRepository rgCurrencyRepository, RgCurrencyMapper rgCurrencyMapper) {
        this.rgCurrencyRepository = rgCurrencyRepository;
        this.rgCurrencyMapper = rgCurrencyMapper;
    }

    /**
     * Save a rgCurrency.
     *
     * @param rgCurrencyDTO the entity to save
     * @return the persisted entity
     */
    public RgCurrencyDTO save(RgCurrencyDTO rgCurrencyDTO) {
        log.debug("Request to save RgCurrency : {}", rgCurrencyDTO);
        RgCurrency rgCurrency = rgCurrencyMapper.toEntity(rgCurrencyDTO);
        rgCurrency = rgCurrencyRepository.save(rgCurrency);
        return rgCurrencyMapper.toDto(rgCurrency);
    }

    /**
     * Get all the rgCurrencies.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RgCurrencyDTO> findAll() {
        log.debug("Request to get all RgCurrencies");
        return rgCurrencyRepository.findAll().stream()
            .map(rgCurrencyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one rgCurrency by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RgCurrencyDTO findOne(Long id) {
        log.debug("Request to get RgCurrency : {}", id);
        RgCurrency rgCurrency = rgCurrencyRepository.findOne(id);
        return rgCurrencyMapper.toDto(rgCurrency);
    }

    /**
     * Delete the rgCurrency by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RgCurrency : {}", id);
        rgCurrencyRepository.delete(id);
    }
}
