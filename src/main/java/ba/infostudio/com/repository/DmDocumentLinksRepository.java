package ba.infostudio.com.repository;

import ba.infostudio.com.domain.DmDocumentLinks;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DmDocumentLinks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DmDocumentLinksRepository extends JpaRepository<DmDocumentLinks, Long> {

}
