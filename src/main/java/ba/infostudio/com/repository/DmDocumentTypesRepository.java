package ba.infostudio.com.repository;

import ba.infostudio.com.domain.DmDocumentTypes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DmDocumentTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DmDocumentTypesRepository extends JpaRepository<DmDocumentTypes, Long> {

}
