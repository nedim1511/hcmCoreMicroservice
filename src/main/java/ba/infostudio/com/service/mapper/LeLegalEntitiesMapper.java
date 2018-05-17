package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.LeLegalEntitiesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LeLegalEntities and its DTO LeLegalEntitiesDTO.
 */
@Mapper(componentModel = "spring", uses = {LeLegalEntityTypesMapper.class, RgRegionsMapper.class})
public interface LeLegalEntitiesMapper extends EntityMapper<LeLegalEntitiesDTO, LeLegalEntities> {

    @Mapping(source = "idEntityType.id", target = "idEntityTypeId")
    @Mapping(source = "idEntityType.name", target = "idEntityTypeName")
    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.name", target = "regionName")
    LeLegalEntitiesDTO toDto(LeLegalEntities leLegalEntities);

    @Mapping(source = "idEntityTypeId", target = "idEntityType")
    @Mapping(source = "regionId", target = "region")
    LeLegalEntities toEntity(LeLegalEntitiesDTO leLegalEntitiesDTO);

    default LeLegalEntities fromId(Long id) {
        if (id == null) {
            return null;
        }
        LeLegalEntities leLegalEntities = new LeLegalEntities();
        leLegalEntities.setId(id);
        return leLegalEntities;
    }
}
