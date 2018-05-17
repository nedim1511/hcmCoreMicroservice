package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.OgWorkPlacesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OgWorkPlaces and its DTO OgWorkPlacesDTO.
 */
@Mapper(componentModel = "spring", uses = {OgWorkPlaceTypesMapper.class})
public interface OgWorkPlacesMapper extends EntityMapper<OgWorkPlacesDTO, OgWorkPlaces> {

    @Mapping(source = "idParent.id", target = "idParentId")
    @Mapping(source = "idParent.name", target = "idParentName")
    @Mapping(source = "idWorkPlaceTypes.id", target = "idWorkPlaceTypesId")
    @Mapping(source = "idWorkPlaceTypes.name", target = "idWorkPlaceTypesName")
    OgWorkPlacesDTO toDto(OgWorkPlaces ogWorkPlaces);

    @Mapping(source = "idParentId", target = "idParent")
    @Mapping(source = "idWorkPlaceTypesId", target = "idWorkPlaceTypes")
    OgWorkPlaces toEntity(OgWorkPlacesDTO ogWorkPlacesDTO);

    default OgWorkPlaces fromId(Long id) {
        if (id == null) {
            return null;
        }
        OgWorkPlaces ogWorkPlaces = new OgWorkPlaces();
        ogWorkPlaces.setId(id);
        return ogWorkPlaces;
    }
}
