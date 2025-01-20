package site.danjam.mate.user_service.domain.myProfile.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.danjam.mate.user_service.domain.mate.dto.MateProfileDTO;
import site.danjam.mate.user_service.domain.myProfile.domain.MyProfile;
import site.danjam.mate.user_service.domain.myProfile.dto.MyProfileDTO;
import site.danjam.mate.user_service.domain.myProfile.dto.UpdateLoginDTO;
import site.danjam.mate.user_service.domain.myProfile.exception.NotFoundMyProfileException;
import site.danjam.mate.user_service.domain.myProfile.repository.MyProfileRepository;
import site.danjam.mate.user_service.domain.user.domain.User;
import site.danjam.mate.user_service.domain.user.exception.DuplicateUsernameException;
import site.danjam.mate.user_service.domain.user.exception.NotFoundUserException;
import site.danjam.mate.user_service.domain.user.repository.UserRepository;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;
import site.danjam.mate.user_service.global.util.MultipartUtil;

@Service
@RequiredArgsConstructor
public class MyProfileInfoService {
    private final MyProfileRepository myProfileRepository;
    private final UserRepository userRepository;
    private final MateStatusClient mateStatusClient;
    private final MultipartUtil multipartUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @MethodDescription(description = "유저 마이 프로필 정보를 조회합니다.")
    public MyProfileDTO readMyProfileInfo(String username) {
        User user = findUser(username);
        MyProfile myProfile = findMyProfile(user);
        MateProfileDTO mate = mateStatusClient.getMateProfile(username);

        MyProfileDTO response = MyProfileDTO.from(myProfile, user, mate);
        return response;
    }

    @Transactional
    public void updateLoginInfo(String username, UpdateLoginDTO dto, MultipartFile file) {
        User user = findUser(username);
        MyProfile myProfile = findMyProfile(user);

        if (file != null && file.isEmpty()) {
            myProfile.updateProfileImg(multipartUtil.determineFileName(file, username));
            myProfileRepository.save(myProfile);
        }

        if (!dto.getUsername().isBlank()) {
            if (userRepository.existsByUsername(username)) {
                throw new DuplicateUsernameException();
            }
            user.updateUsername(username);
        }

        if (!dto.getPassword().isBlank()) {
            user.updatePassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        }

        userRepository.save(user);
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(NotFoundUserException::new);
    }

    private MyProfile findMyProfile(User user) {
        return Optional.ofNullable(user.getMyProfile()).orElseThrow(NotFoundMyProfileException::new);
    }

}
