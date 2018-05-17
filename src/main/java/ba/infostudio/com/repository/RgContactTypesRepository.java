package ba.infostudio.com.repository;

import ba.infostudio.com.domain.RgContactTypes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RgContactTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RgContactTypesRepository extends JpaRepository<RgContactTypes, Long> {

}
