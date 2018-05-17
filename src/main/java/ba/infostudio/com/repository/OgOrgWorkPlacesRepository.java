package ba.infostudio.com.repository;

import ba.infostudio.com.domain.OgOrgWorkPlaces;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OgOrgWorkPlaces entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OgOrgWorkPlacesRepository extends JpaRepository<OgOrgWorkPlaces, Long> {

}
