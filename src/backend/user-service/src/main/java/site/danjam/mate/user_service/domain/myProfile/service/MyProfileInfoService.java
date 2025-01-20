package site.danjam.mate.user_service.domain.myProfile.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.danjam.mate.user_service.domain.mate.dto.MateProfileDTO;
import site.danjam.mate.user_service.domain.myProfile.domain.MyProfile;
import site.danjam.mate.user_service.domain.myProfile.dto.MyProfileDTO;
import site.danjam.mate.user_service.domain.myProfile.exception.NotFoundMyProfileException;
import site.danjam.mate.user_service.domain.myProfile.repository.MyProfileRepository;
import site.danjam.mate.user_service.domain.user.domain.User;
import site.danjam.mate.user_service.domain.user.exception.NotFoundUserException;
import site.danjam.mate.user_service.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MyProfileInfoService {
    private final MyProfileRepository myProfileRepository;
    private final UserRepository userRepository;
    private final MateStatusClient mateStatusClient;

    public MyProfileDTO readMyProfileInfo(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(NotFoundUserException::new);
        MyProfile myProfile = Optional.ofNullable(user.getMyProfile()).orElseThrow(NotFoundMyProfileException::new);
        MateProfileDTO mate = mateStatusClient.getMateProfile(username);

        MyProfileDTO response = MyProfileDTO.from(myProfile, user, mate);
        return response;
    }
}
