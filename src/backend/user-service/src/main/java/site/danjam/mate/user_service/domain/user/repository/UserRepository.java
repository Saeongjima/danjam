package site.danjam.mate.user_service.domain.user.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.user_service.domain.user.domain.User;

import site.danjam.mate.common.exception.NotFoundMyProfileException;
import site.danjam.mate.user_service.domain.certification.domain.Certification;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserJpaRepository userJpaRepository;

    public User save(User user) {
        return userJpaRepository.save(user);
    }

    public User findByMyProfile(Certification certification) {
        return Optional.ofNullable(certification.getUser()).orElseThrow(NotFoundMyProfileException::new);
    }

    public boolean existsByNickname(String nickName) {
        return userJpaRepository.existsByNickname(nickName);
    }

}
