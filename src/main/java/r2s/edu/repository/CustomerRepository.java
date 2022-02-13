package r2s.edu.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import r2s.edu.domain.Customer;

/**
 * Spring Data SQL repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {}
