package ba.infostudio.com.repository;

import ba.infostudio.com.domain.LeLegalEntityTypes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LeLegalEntityTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeLegalEntityTypesRepository extends JpaRepository<LeLegalEntityTypes, Long> {

}
