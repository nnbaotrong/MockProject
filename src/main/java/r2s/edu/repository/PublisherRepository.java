package r2s.edu.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import r2s.edu.domain.Publisher;

/**
 * Spring Data SQL repository for the Publisher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {}
