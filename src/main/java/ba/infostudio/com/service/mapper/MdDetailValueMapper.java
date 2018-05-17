package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.MdDetailValueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MdDetailValue and its DTO MdDetailValueDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MdDetailValueMapper extends EntityMapper<MdDetailValueDTO, MdDetailValue> {

    @Mapping(source = "idDetail.id", target = "idDetailId")
    MdDetailValueDTO toDto(MdDetailValue mdDetailValue);

    @Mapping(source = "idDetailId", target = "idDetail")
    MdDetailValue toEntity(MdDetailValueDTO mdDetailValueDTO);

    default MdDetailValue fromId(Long id) {
        if (id == null) {
            return null;
        }
        MdDetailValue mdDetailValue = new MdDetailValue();
        mdDetailValue.setId(id);
        return mdDetailValue;
    }
}
