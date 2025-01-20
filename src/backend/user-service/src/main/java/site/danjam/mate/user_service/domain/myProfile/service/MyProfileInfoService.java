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
import site.danjam.mate.user_service.domain.myProfile.dto.UpdateSchoolDTO;
import site.danjam.mate.user_service.domain.myProfile.exception.NotFoundMyProfileException;
import site.danjam.mate.user_service.domain.myProfile.repository.MyProfileRepository;
import site.danjam.mate.user_service.domain.school.domain.School;
import site.danjam.mate.user_service.domain.school.exception.NotFoundSchoolException;
import site.danjam.mate.user_service.domain.school.repository.SchoolRepository;
import site.danjam.mate.user_service.domain.user.domain.User;
import site.danjam.mate.user_service.domain.user.exception.DuplicateUsernameException;
import site.danjam.mate.user_service.domain.user.exception.NotFoundUserException;
import site.danjam.mate.user_service.domain.user.repository.UserRepository;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;
import site.danjam.mate.user_service.global.exception.InvalidInputException;
import site.danjam.mate.user_service.global.util.MultipartUtil;

@Service
@RequiredArgsConstructor
public class MyProfileInfoService {

    private final String SUFFIX = "auth";
    private final String PROFILE_BUCKET_NAME = "user-profile-img";
    private final String AUTH_BUCKET_NAME = "user-auth-img";
    private final MyProfileRepository myProfileRepository;
    private final UserRepository userRepository;
    private final MateStatusClient mateStatusClient;
    private final MultipartUtil multipartUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SchoolRepository schoolRepository;

    @MethodDescription(description = "유저 마이 프로필 정보를 조회합니다.")
    public MyProfileDTO readMyProfileInfo(String username) {
        User user = findByUser(username);
        MyProfile myProfile = findByMyProfile(user);
        MateProfileDTO mate = mateStatusClient.getMateProfile(username);

        MyProfileDTO response = MyProfileDTO.from(myProfile, user, mate);
        return response;
    }

    @MethodDescription(description = "마이프로필 로그인정보(username, password, profileImg) 를 수정합니다.")
    @Transactional
    public void updateLoginInfo(String username, UpdateLoginDTO dto, MultipartFile file) {
        User user = findByUser(username);
        MyProfile myProfile = findByMyProfile(user);

        if (file != null && file.isEmpty()) {
            myProfile.updateProfileImg(multipartUtil.determineFileName(file, username, SUFFIX, PROFILE_BUCKET_NAME));
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

    @MethodDescription(description = "사용자의 학교 정보를 수정합니다.")
    public void updateSchoolInfo(String username, UpdateSchoolDTO dto, MultipartFile file) {
        User user = findByUser(username);
        MyProfile myProfile = findByMyProfile(user);
        School school = findBySchool(dto.getSchoolId());

        if (file != null && file.isEmpty()) {
            throw new InvalidInputException("학교 인증 사진이 존재하지 않습니다.");
        }

        user.updateAuthImgUrl(multipartUtil.determineFileName(file, username, SUFFIX, AUTH_BUCKET_NAME));
        user.updateSchool(school);
        myProfile.updateSchoolInfo(dto.getEntryYear(), dto.getMajor());

        userRepository.save(user);
        myProfileRepository.save(myProfile);
    }

    @MethodDescription(description = "유저 정보를 찾습니다.")
    private User findByUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(NotFoundUserException::new);
    }

    @MethodDescription(description = "마이프로필 정보를 찾습니다.")
    private MyProfile findByMyProfile(User user) {
        return Optional.ofNullable(user.getMyProfile()).orElseThrow(NotFoundMyProfileException::new);
    }

    @MethodDescription(description = "학교 정보를 찾습니다.")
    private School findBySchool(Long id) {
        return schoolRepository.findById(id).orElseThrow(NotFoundSchoolException::new);
    }
}
