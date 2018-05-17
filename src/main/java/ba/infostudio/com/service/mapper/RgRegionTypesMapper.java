package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.RgRegionTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RgRegionTypes and its DTO RgRegionTypesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RgRegionTypesMapper extends EntityMapper<RgRegionTypesDTO, RgRegionTypes> {



    default RgRegionTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        RgRegionTypes rgRegionTypes = new RgRegionTypes();
        rgRegionTypes.setId(id);
        return rgRegionTypes;
    }
}
