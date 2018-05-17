package ba.infostudio.com.repository;

import ba.infostudio.com.domain.RgIdentificationTypes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RgIdentificationTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RgIdentificationTypesRepository extends JpaRepository<RgIdentificationTypes, Long> {

}
