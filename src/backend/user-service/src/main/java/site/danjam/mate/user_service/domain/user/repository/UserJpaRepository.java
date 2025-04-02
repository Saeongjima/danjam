package site.danjam.mate.user_service.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.danjam.mate.user_service.domain.user.domain.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    boolean existsByNickname(String nickName);
}

