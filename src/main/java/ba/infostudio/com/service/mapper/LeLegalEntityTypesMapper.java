package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.LeLegalEntityTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LeLegalEntityTypes and its DTO LeLegalEntityTypesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LeLegalEntityTypesMapper extends EntityMapper<LeLegalEntityTypesDTO, LeLegalEntityTypes> {



    default LeLegalEntityTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        LeLegalEntityTypes leLegalEntityTypes = new LeLegalEntityTypes();
        leLegalEntityTypes.setId(id);
        return leLegalEntityTypes;
    }
}
