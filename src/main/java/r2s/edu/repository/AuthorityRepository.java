package r2s.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import r2s.edu.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
