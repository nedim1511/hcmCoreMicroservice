package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.RgIdentificationTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RgIdentificationTypes and its DTO RgIdentificationTypesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RgIdentificationTypesMapper extends EntityMapper<RgIdentificationTypesDTO, RgIdentificationTypes> {



    default RgIdentificationTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        RgIdentificationTypes rgIdentificationTypes = new RgIdentificationTypes();
        rgIdentificationTypes.setId(id);
        return rgIdentificationTypes;
    }
}
