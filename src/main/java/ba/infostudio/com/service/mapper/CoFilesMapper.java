package ba.infostudio.com.service.mapper;

import ba.infostudio.com.domain.*;
import ba.infostudio.com.service.dto.CoFilesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CoFiles and its DTO CoFilesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CoFilesMapper extends EntityMapper<CoFilesDTO, CoFiles> {



    default CoFiles fromId(Long id) {
        if (id == null) {
            return null;
        }
        CoFiles coFiles = new CoFiles();
        coFiles.setId(id);
        return coFiles;
    }
}
