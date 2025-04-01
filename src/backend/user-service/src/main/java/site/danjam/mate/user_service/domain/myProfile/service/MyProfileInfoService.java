package site.danjam.mate.user_service.domain.myProfile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.danjam.mate.common.annotation.MethodDescription;
import site.danjam.mate.common.exception.CanNotFindResourceException;
import site.danjam.mate.common.exception.Code;
import site.danjam.mate.user_service.domain.mate.dto.MateProfileDTO;
import site.danjam.mate.user_service.domain.myProfile.domain.User;
import site.danjam.mate.user_service.domain.myProfile.dto.GreetingDTO;
import site.danjam.mate.user_service.domain.myProfile.dto.MyProfileDTO;
import site.danjam.mate.user_service.domain.myProfile.dto.UpdateLoginDTO;
import site.danjam.mate.user_service.domain.myProfile.dto.UpdateSchoolDTO;
import site.danjam.mate.user_service.domain.myProfile.repository.MyProfileRepository;
import site.danjam.mate.user_service.domain.school.domain.Major;
import site.danjam.mate.user_service.domain.school.domain.School;
import site.danjam.mate.user_service.domain.school.repository.MajorRepository;
import site.danjam.mate.user_service.domain.school.repository.SchoolRepository;
import site.danjam.mate.user_service.domain.user.domain.Certification;
import site.danjam.mate.user_service.domain.user.exception.DuplicateUsernameException;
import site.danjam.mate.user_service.domain.user.repository.UserRepository;
import site.danjam.mate.common.exception.InvalidInputException;
import site.danjam.mate.user_service.global.util.MultipartUtil;

@Service
@Transactional
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
    private final MajorRepository majorRepository;

    @MethodDescription(description = "유저 마이 프로필 정보를 조회합니다.")
    @Transactional(readOnly = true)
    public MyProfileDTO readMyProfileInfo(String username) {
        Certification certification = userRepository.findByUsername(username);
        User user = myProfileRepository.findByMyProfile(certification);
        MateProfileDTO mate = mateStatusClient.getMateProfile(username);
        Major major = majorRepository.findById(user.getMajorId()).orElse(null);
        return MyProfileDTO.from(user, certification, mate, major.getMajorName());
    }

    @MethodDescription(description = "마이프로필 로그인정보(username, password, profileImg) 를 수정합니다.")
    public void updateLoginInfo(String username, UpdateLoginDTO dto, MultipartFile file) {
        Certification certification = userRepository.findByUsername(username);
        User user = myProfileRepository.findByMyProfile(certification);

        if (file != null && !file.isEmpty()) {
            user.updateProfileImg(multipartUtil.determineFileName(file, username, SUFFIX, PROFILE_BUCKET_NAME));
            myProfileRepository.save(user);
        }

        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            if (userRepository.existsByUsername(dto.getUsername())) {
                throw new DuplicateUsernameException();
            }
            certification.updateUsername(username);
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            certification.updatePassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        }

        userRepository.save(certification);
    }

    @MethodDescription(description = "사용자의 학교 정보를 수정합니다.")
    public void updateSchoolInfo(String username, UpdateSchoolDTO dto, MultipartFile file) {
        Certification certification = userRepository.findByUsername(username);
        User user = myProfileRepository.findByMyProfile(certification);
        School school = schoolRepository.findBySchool(dto.getSchoolId());

        if (file == null || file.isEmpty()) {
            throw new InvalidInputException("학교 인증 사진이 존재하지 않습니다.");
        }

        certification.updateAuthImgUrl(multipartUtil.determineFileName(file, username, SUFFIX, AUTH_BUCKET_NAME));
        user.updateSchool(school);
        Major major = majorRepository.findBySchoolAndMajorName(dto.getMajor(), school.getId()).orElseThrow(()-> new CanNotFindResourceException(
                Code.USER_CAN_NOT_FIND_MAJOR));
        user.updateSchoolInfo(dto.getEntryYear(), school.getId(), major.getId());

        userRepository.save(certification);
        myProfileRepository.save(user);
    }

    @MethodDescription(description = "사용자의 mbti와 소개글을 수정합니다.")
    public void updateGreeting(String username, GreetingDTO dto) {
        Certification certification = userRepository.findByUsername(username);
        User user = myProfileRepository.findByMyProfile(certification);

        user.updateGreeting(dto.getGreeting());
        user.updateMbti(dto.getMbti());

        myProfileRepository.save(user);
    }

    @MethodDescription(description = "회원을 탈퇴합니다.")
    public void deleteUser(String username, UpdateLoginDTO dto) {
        Certification certification = userRepository.findByUsername(username);
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), certification.getPassword())) {
            throw new InvalidInputException("비밀번호가 일치하지 않습니다.");
        }
        certification.softDelete(username);
        userRepository.save(certification);
    }
}
