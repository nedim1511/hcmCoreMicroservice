package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.OgOrgTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OgOrgTypes and its DTO OgOrgTypesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OgOrgTypesMapper extends EntityMapper<OgOrgTypesDTO, OgOrgTypes> {



    default OgOrgTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        OgOrgTypes ogOrgTypes = new OgOrgTypes();
        ogOrgTypes.setId(id);
        return ogOrgTypes;
    }
}
