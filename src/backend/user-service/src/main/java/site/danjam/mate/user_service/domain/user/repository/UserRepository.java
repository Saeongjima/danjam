package site.danjam.mate.user_service.domain.user.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.common.exception.Code;
import site.danjam.mate.common.exception.user_service.CanNotFindUserException;
import site.danjam.mate.user_service.domain.user.domain.User;

import site.danjam.mate.common.exception.user_service.NotFoundMyProfileException;
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

    public User findById(Long userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(() -> new CanNotFindUserException(Code.USER_CAN_NOT_FIND_USER, "해당 유저를 찾을 수 없습니다."));
    }
}
