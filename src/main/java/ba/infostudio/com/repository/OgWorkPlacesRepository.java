package ba.infostudio.com.repository;

import ba.infostudio.com.domain.OgWorkPlaces;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OgWorkPlaces entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OgWorkPlacesRepository extends JpaRepository<OgWorkPlaces, Long> {

}
