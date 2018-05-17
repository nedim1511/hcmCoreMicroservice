package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.RgSchoolsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RgSchools and its DTO RgSchoolsDTO.
 */
@Mapper(componentModel = "spring", uses = {RgRegionsMapper.class})
public interface RgSchoolsMapper extends EntityMapper<RgSchoolsDTO, RgSchools> {

    @Mapping(source = "idCity.id", target = "idCityId")
    @Mapping(source = "idCity.name", target = "idCityName")
    RgSchoolsDTO toDto(RgSchools rgSchools);

    @Mapping(source = "idCityId", target = "idCity")
    RgSchools toEntity(RgSchoolsDTO rgSchoolsDTO);

    default RgSchools fromId(Long id) {
        if (id == null) {
            return null;
        }
        RgSchools rgSchools = new RgSchools();
        rgSchools.setId(id);
        return rgSchools;
    }
}
