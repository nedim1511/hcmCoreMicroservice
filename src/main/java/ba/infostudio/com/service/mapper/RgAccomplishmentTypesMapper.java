package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.RgAccomplishmentTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RgAccomplishmentTypes and its DTO RgAccomplishmentTypesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RgAccomplishmentTypesMapper extends EntityMapper<RgAccomplishmentTypesDTO, RgAccomplishmentTypes> {



    default RgAccomplishmentTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        RgAccomplishmentTypes rgAccomplishmentTypes = new RgAccomplishmentTypes();
        rgAccomplishmentTypes.setId(id);
        return rgAccomplishmentTypes;
    }
}
