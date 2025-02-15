package site.danjam.mate.user_service.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.danjam.mate.user_service.auth.domain.RefreshToken;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByRefresh(String refresh);

    Boolean existsByRefresh(String refresh);

}
