package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.MdHeadersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MdHeaders and its DTO MdHeadersDTO.
 */
@Mapper(componentModel = "spring", uses = {MdHeaderTypesMapper.class})
public interface MdHeadersMapper extends EntityMapper<MdHeadersDTO, MdHeaders> {

    @Mapping(source = "idHeaderType.id", target = "idHeaderTypeId")
    @Mapping(source = "idHeaderType.name", target = "idHeaderTypeName")
    MdHeadersDTO toDto(MdHeaders mdHeaders);

    @Mapping(source = "idHeaderTypeId", target = "idHeaderType")
    MdHeaders toEntity(MdHeadersDTO mdHeadersDTO);

    default MdHeaders fromId(Long id) {
        if (id == null) {
            return null;
        }
        MdHeaders mdHeaders = new MdHeaders();
        mdHeaders.setId(id);
        return mdHeaders;
    }
}
