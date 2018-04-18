package ba.infostudio.com.repository;

import ba.infostudio.com.domain.ApConstants;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ApConstants entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApConstantsRepository extends JpaRepository<ApConstants, Long> {

}
