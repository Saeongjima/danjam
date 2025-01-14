package site.danjam.mate.user_service.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.danjam.mate.user_service.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}