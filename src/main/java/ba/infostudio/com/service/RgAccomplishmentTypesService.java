package ba.infostudio.com.service;

import ba.infostudio.com.service.dto.RgAccomplishmentTypesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing RgAccomplishmentTypes.
 */
public interface RgAccomplishmentTypesService {

    /**
     * Save a rgAccomplishmentTypes.
     *
     * @param rgAccomplishmentTypesDTO the entity to save
     * @return the persisted entity
     */
    RgAccomplishmentTypesDTO save(RgAccomplishmentTypesDTO rgAccomplishmentTypesDTO);

    /**
     * Get all the rgAccomplishmentTypes.
     *
     * @return the list of entities
     */
    List<RgAccomplishmentTypesDTO> findAll();


    /**
     * Get the "id" rgAccomplishmentTypes.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RgAccomplishmentTypesDTO> findOne(Long id);

    /**
     * Delete the "id" rgAccomplishmentTypes.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
