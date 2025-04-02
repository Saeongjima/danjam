package site.danjam.mate.user_service.domain.certification.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import site.danjam.mate.user_service.domain.certification.domain.Certification;

public interface CertificationJpaRepository extends JpaRepository<Certification, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Certification> findByUsername(String username);
}