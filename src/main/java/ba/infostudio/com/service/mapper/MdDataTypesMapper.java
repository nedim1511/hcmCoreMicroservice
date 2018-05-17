package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.MdDataTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MdDataTypes and its DTO MdDataTypesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MdDataTypesMapper extends EntityMapper<MdDataTypesDTO, MdDataTypes> {



    default MdDataTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        MdDataTypes mdDataTypes = new MdDataTypes();
        mdDataTypes.setId(id);
        return mdDataTypes;
    }
}
