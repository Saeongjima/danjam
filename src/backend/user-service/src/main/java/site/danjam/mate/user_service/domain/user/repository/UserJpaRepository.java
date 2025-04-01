package site.danjam.mate.user_service.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import site.danjam.mate.user_service.domain.user.domain.Certification;

public interface UserJpaRepository extends JpaRepository<Certification, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<Certification> findByUsername(String username);
}