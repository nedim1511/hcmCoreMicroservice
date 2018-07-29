package ba.infostudio.com.repository;
import ba.infostudio.com.domain.CoFiles;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data JPA repository for the CoFiles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoFilesRepository extends JpaRepository<CoFiles, Long> {
    List<CoFiles> findAllByValidToGreaterThanEqualAndValidFromLessThanEqual(LocalDate currentDate, LocalDate currentDate2);


}
