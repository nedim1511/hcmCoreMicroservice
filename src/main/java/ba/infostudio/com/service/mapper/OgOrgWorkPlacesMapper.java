package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.OgOrgWorkPlacesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OgOrgWorkPlaces and its DTO OgOrgWorkPlacesDTO.
 */
@Mapper(componentModel = "spring", uses = {OgOrganizationsMapper.class, OgWorkPlacesMapper.class})
public interface OgOrgWorkPlacesMapper extends EntityMapper<OgOrgWorkPlacesDTO, OgOrgWorkPlaces> {

    @Mapping(source = "idOrganization.id", target = "idOrganizationId")
    @Mapping(source = "idOrganization.name", target = "idOrganizationName")
    @Mapping(source = "idWorkPlace.id", target = "idWorkPlaceId")
    @Mapping(source = "idWorkPlace.name", target = "idWorkPlaceName")
    OgOrgWorkPlacesDTO toDto(OgOrgWorkPlaces ogOrgWorkPlaces);

    @Mapping(source = "idOrganizationId", target = "idOrganization")
    @Mapping(source = "idWorkPlaceId", target = "idWorkPlace")
    OgOrgWorkPlaces toEntity(OgOrgWorkPlacesDTO ogOrgWorkPlacesDTO);

    default OgOrgWorkPlaces fromId(Long id) {
        if (id == null) {
            return null;
        }
        OgOrgWorkPlaces ogOrgWorkPlaces = new OgOrgWorkPlaces();
        ogOrgWorkPlaces.setId(id);
        return ogOrgWorkPlaces;
    }
}
