package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.MdDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MdDetails and its DTO MdDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {MdHeadersMapper.class, MdDataTypesMapper.class})
public interface MdDetailsMapper extends EntityMapper<MdDetailsDTO, MdDetails> {

    @Mapping(source = "idHeader.id", target = "idHeaderId")
    @Mapping(source = "idHeader.name", target = "idHeaderName")
    @Mapping(source = "idDataType.id", target = "idDataTypeId")
    @Mapping(source = "idDataType.name", target = "idDataTypeName")
    MdDetailsDTO toDto(MdDetails mdDetails);

    @Mapping(source = "idHeaderId", target = "idHeader")
    @Mapping(source = "idDataTypeId", target = "idDataType")
    MdDetails toEntity(MdDetailsDTO mdDetailsDTO);

    default MdDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        MdDetails mdDetails = new MdDetails();
        mdDetails.setId(id);
        return mdDetails;
    }
}
