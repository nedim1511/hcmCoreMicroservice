package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.DmDocumentTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DmDocumentTypes and its DTO DmDocumentTypesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DmDocumentTypesMapper extends EntityMapper<DmDocumentTypesDTO, DmDocumentTypes> {



    default DmDocumentTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        DmDocumentTypes dmDocumentTypes = new DmDocumentTypes();
        dmDocumentTypes.setId(id);
        return dmDocumentTypes;
    }
}
