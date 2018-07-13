package ba.infostudio.com.repository;

import ba.infostudio.com.domain.RgAccomplishmentTypes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RgAccomplishmentTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RgAccomplishmentTypesRepository extends JpaRepository<RgAccomplishmentTypes, Long> {

}
