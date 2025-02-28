package site.danjam.mate.user_service.auth.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import site.danjam.mate.user_service.auth.domain.RefreshToken;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByRefresh(String refresh);

    Boolean existsByRefresh(String refresh);

    Optional<RefreshToken> getRefreshTokenByUserId(Long userId);

    Optional<RefreshToken> getRefreshTokenByRefresh(String refresh);
}
