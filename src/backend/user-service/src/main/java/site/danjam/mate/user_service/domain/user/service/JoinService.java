package site.danjam.mate.user_service.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.danjam.mate.user_service.domain.myProfile.domain.MyProfile;
import site.danjam.mate.user_service.domain.myProfile.repository.MyProfileRepository;
import site.danjam.mate.user_service.domain.school.domain.School;
import site.danjam.mate.user_service.domain.school.exception.NotFoundSchoolException;
import site.danjam.mate.user_service.domain.school.repository.SchoolRepository;
import site.danjam.mate.user_service.domain.user.domain.User;
import site.danjam.mate.user_service.domain.user.dto.JoinDTO;
import site.danjam.mate.user_service.domain.user.repository.UserRepository;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;
import site.danjam.mate.user_service.global.util.MultipartUtil;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final MyProfileRepository myProfileRepository;
    private final SchoolRepository schoolRepository;

    private final MultipartUtil multipartUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @MethodDescription(description = "유저를 생성합니다.")
    @Transactional
    public String signup(JoinDTO joinDto, MultipartFile authImgFile) {
        School school = schoolRepository.findById(joinDto.getSchoolId()).orElseThrow(NotFoundSchoolException::new);

        String fileName = multipartUtil.determineFileName(authImgFile, joinDto.getUsername(), "auth");
        User user = createBuildUser(joinDto, fileName);
        user.createDefaultSchool(school);
        MyProfile myProfile = createBuildMyProfile(joinDto, user);
        user.createDefaultMyProfile(myProfile);

        userRepository.save(user);
        myProfileRepository.save(myProfile);

        return fileName;
    }


    @MethodDescription(description = "빌더 패턴을 통해 User 를 반환받습니다.")
    private User createBuildUser(JoinDTO dto, String authImgUrl) {
        return User.builder()
                .name(dto.getName())
                .username(dto.getUsername())
                .gender(dto.getGender())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .authImgUrl(authImgUrl)
                .build();
    }

    @MethodDescription(description = "빌더 패턴을 통해 MyProfile 를 반환받습니다.")
    private MyProfile createBuildMyProfile(JoinDTO dto, User user) {
        return MyProfile.builder()
                .birth(dto.getBirth())
                .entryYear(dto.getEntryYear())
                .major(dto.getMajor())
                .user(user)
                .build();
    }
}
