package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.OgWorkPlaceTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OgWorkPlaceTypes and its DTO OgWorkPlaceTypesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OgWorkPlaceTypesMapper extends EntityMapper<OgWorkPlaceTypesDTO, OgWorkPlaceTypes> {



    default OgWorkPlaceTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        OgWorkPlaceTypes ogWorkPlaceTypes = new OgWorkPlaceTypes();
        ogWorkPlaceTypes.setId(id);
        return ogWorkPlaceTypes;
    }
}
