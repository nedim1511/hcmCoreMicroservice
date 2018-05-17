package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.RgRegionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RgRegions and its DTO RgRegionsDTO.
 */
@Mapper(componentModel = "spring", uses = {RgRegionTypesMapper.class})
public interface RgRegionsMapper extends EntityMapper<RgRegionsDTO, RgRegions> {

    @Mapping(source = "idType.id", target = "idTypeId")
    @Mapping(source = "idType.name", target = "idTypeName")
    @Mapping(source = "idParent.id", target = "idParentId")
    @Mapping(source = "idParent.name", target = "idParentName")
    RgRegionsDTO toDto(RgRegions rgRegions);

    @Mapping(source = "idTypeId", target = "idType")
    @Mapping(source = "idParentId", target = "idParent")
    RgRegions toEntity(RgRegionsDTO rgRegionsDTO);

    default RgRegions fromId(Long id) {
        if (id == null) {
            return null;
        }
        RgRegions rgRegions = new RgRegions();
        rgRegions.setId(id);
        return rgRegions;
    }
}
