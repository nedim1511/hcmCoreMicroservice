package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.DmDocumentLinksDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DmDocumentLinks and its DTO DmDocumentLinksDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DmDocumentLinksMapper extends EntityMapper<DmDocumentLinksDTO, DmDocumentLinks> {



    default DmDocumentLinks fromId(Long id) {
        if (id == null) {
            return null;
        }
        DmDocumentLinks dmDocumentLinks = new DmDocumentLinks();
        dmDocumentLinks.setId(id);
        return dmDocumentLinks;
    }
}
