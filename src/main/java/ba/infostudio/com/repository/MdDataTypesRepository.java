package ba.infostudio.com.repository;

import ba.infostudio.com.domain.MdDataTypes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MdDataTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MdDataTypesRepository extends JpaRepository<MdDataTypes, Long> {

}
