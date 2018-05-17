package ba.infostudio.com.repository;

import ba.infostudio.com.domain.RgFamilyRoles;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RgFamilyRoles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RgFamilyRolesRepository extends JpaRepository<RgFamilyRoles, Long> {

}
