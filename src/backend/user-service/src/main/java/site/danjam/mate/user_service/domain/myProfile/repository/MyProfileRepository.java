package site.danjam.mate.user_service.domain.myProfile.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.user_service.domain.myProfile.domain.MyProfile;
import site.danjam.mate.user_service.domain.myProfile.exception.NotFoundMyProfileException;
import site.danjam.mate.user_service.domain.user.domain.User;

@Repository
@RequiredArgsConstructor
public class MyProfileRepository {
    private final MyProfileJpaRepository myProfileJpaRepository;

    public MyProfile save(MyProfile myProfile) {
        return myProfileJpaRepository.save(myProfile);
    }

    public MyProfile findByMyProfile(User user) {
        return Optional.ofNullable(user.getMyProfile()).orElseThrow(NotFoundMyProfileException::new);
    }
}
