package site.danjam.mate.user_service.domain.user.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.user_service.domain.user.domain.Certification;
import site.danjam.mate.common.exception.NotFoundUserException;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserJpaRepository userJpaRepository;

    public Certification save(Certification certification) {
        return userJpaRepository.save(certification);
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

    public Certification findByUsername(String username) {
        return userJpaRepository.findByUsername(username).orElseThrow(NotFoundUserException::new);
    }

    public Optional<Certification> findById(Long userId){
        return userJpaRepository.findById(userId);
    }
}
