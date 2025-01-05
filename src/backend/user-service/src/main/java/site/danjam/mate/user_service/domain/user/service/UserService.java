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
import site.danjam.mate.user_service.domain.user.dto.JoinDto;
import site.danjam.mate.user_service.domain.user.repository.UserRepository;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;
import site.danjam.mate.user_service.global.common.minio.MinioService;
import site.danjam.mate.user_service.global.util.MultipartUtil;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MyProfileRepository myProfileRepository;
    private final SchoolRepository schoolRepository;

    private final MinioService minioService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @MethodDescription(description = "유저를 생성합니다.")
    @Transactional
    public String signup(JoinDto joinDto, MultipartFile authImgFile) throws Exception {
        School school = schoolRepository.findById(joinDto.getSchoolId()).orElseThrow(NotFoundSchoolException::new);

        String fileName = uploadFile(authImgFile, joinDto.getUsername());
        User user = createBuildUser(joinDto, fileName);
        user.createDefaultSchool(school);
        MyProfile myProfile = createBuildMyProfile(joinDto, user);
        user.createDefaultMyProfile(myProfile);

        userRepository.save(user);
        myProfileRepository.save(myProfile);

        return fileName;
    }

    @MethodDescription(description = "파일을 업로드 하고, 파일 이름을 반환받습니다.")
    private String uploadFile(MultipartFile file, String userName) throws Exception {
        MultipartUtil multipartUtil = new MultipartUtil();
        String extension = multipartUtil.getFileExtension(file);
        System.out.println("extension = " + extension + ", userName = " + userName);
        String fileName = multipartUtil.generateFileName(userName, "auth", extension);
//        minioService.uploadFileMinio("auth", fileName, file);

        return fileName;
    }

    @MethodDescription(description = "빌더 패턴을 통해 User 를 반환받습니다.")
    private User createBuildUser(JoinDto dto, String authImgUrl) {
        return User.builder()
                .username(dto.getUsername())
                .gender(dto.getGender())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .authImgUrl(authImgUrl)
                .build();
    }

    @MethodDescription(description = "빌더 패턴을 통해 MyProfile 를 반환받습니다.")
    private MyProfile createBuildMyProfile(JoinDto dto, User user) {
        return MyProfile.builder()
                .birth(dto.getBirth())
                .entryYear(dto.getEntryYear())
                .major(dto.getMajor())
                .user(user)
                .build();
    }
}
