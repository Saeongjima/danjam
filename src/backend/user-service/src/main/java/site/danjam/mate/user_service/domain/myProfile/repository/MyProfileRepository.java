package site.danjam.mate.user_service.domain.myProfile.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.user_service.domain.myProfile.domain.User;
import site.danjam.mate.common.exception.NotFoundMyProfileException;
import site.danjam.mate.user_service.domain.user.domain.Certification;

@Repository
@RequiredArgsConstructor
public class MyProfileRepository {
    private final MyProfileJpaRepository myProfileJpaRepository;

    public User save(User user) {
        return myProfileJpaRepository.save(user);
    }

    public User findByMyProfile(Certification certification) {
        return Optional.ofNullable(certification.getUser()).orElseThrow(NotFoundMyProfileException::new);
    }
}
