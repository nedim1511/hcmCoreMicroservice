package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.MdHeaderTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MdHeaderTypes and its DTO MdHeaderTypesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MdHeaderTypesMapper extends EntityMapper<MdHeaderTypesDTO, MdHeaderTypes> {



    default MdHeaderTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        MdHeaderTypes mdHeaderTypes = new MdHeaderTypes();
        mdHeaderTypes.setId(id);
        return mdHeaderTypes;
    }
}
