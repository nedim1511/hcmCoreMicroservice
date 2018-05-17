package ba.infostudio.com.repository;

import ba.infostudio.com.domain.OgOrg;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OgOrg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OgOrgRepository extends JpaRepository<OgOrg, Long> {

}
