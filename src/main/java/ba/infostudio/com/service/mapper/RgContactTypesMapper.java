package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.RgContactTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RgContactTypes and its DTO RgContactTypesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RgContactTypesMapper extends EntityMapper<RgContactTypesDTO, RgContactTypes> {



    default RgContactTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        RgContactTypes rgContactTypes = new RgContactTypes();
        rgContactTypes.setId(id);
        return rgContactTypes;
    }
}
