package ba.infostudio.com.repository;

import ba.infostudio.com.domain.OgOrganizations;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OgOrganizations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OgOrganizationsRepository extends JpaRepository<OgOrganizations, Long> {

}
