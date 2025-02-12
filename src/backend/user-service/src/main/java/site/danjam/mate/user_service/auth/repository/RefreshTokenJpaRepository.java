package site.danjam.mate.user_service.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.danjam.mate.user_service.auth.domain.RefreshToken;

@Repository
public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> {
}
