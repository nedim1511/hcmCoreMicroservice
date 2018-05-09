package ba.infostudio.com.repository;

import ba.infostudio.com.domain.RgCurrency;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RgCurrency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RgCurrencyRepository extends JpaRepository<RgCurrency, Long> {

}
