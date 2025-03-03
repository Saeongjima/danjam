package site.danjam.mate.user_service.domain.user.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.user_service.domain.user.domain.User;
import site.danjam.mate.user_service.domain.user.exception.NotFoundUserException;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserJpaRepository userJpaRepository;

    public User save(User user) {
        return userJpaRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    public boolean existsByNickname(String nickname) {
        return userJpaRepository.existsByNickname(nickname);
    }

    public User findByUsername(String username) {
        return userJpaRepository.findByUsername(username).orElseThrow(NotFoundUserException::new);
    }

    public Optional<User> findById(Long userId){
        return userJpaRepository.findById(userId);
    }

}
