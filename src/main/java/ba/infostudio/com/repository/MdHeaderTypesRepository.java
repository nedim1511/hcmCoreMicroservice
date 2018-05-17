package ba.infostudio.com.repository;

import ba.infostudio.com.domain.MdHeaderTypes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MdHeaderTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MdHeaderTypesRepository extends JpaRepository<MdHeaderTypes, Long> {

}
