package site.danjam.mate.user_service.domain.certification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.danjam.mate.common.annotation.MethodDescription;
import site.danjam.mate.common.enums.Role;
import site.danjam.mate.common.exception.CanNotFindResourceException;
import site.danjam.mate.common.exception.Code;
import site.danjam.mate.user_service.domain.user.domain.User;
import site.danjam.mate.user_service.domain.user.repository.UserRepository;
import site.danjam.mate.user_service.domain.school.domain.Major;
import site.danjam.mate.user_service.domain.school.domain.School;
import site.danjam.mate.user_service.domain.school.repository.MajorRepository;
import site.danjam.mate.user_service.domain.school.repository.SchoolRepository;
import site.danjam.mate.user_service.domain.certification.domain.Certification;
import site.danjam.mate.user_service.domain.certification.dto.JoinDTO;
import site.danjam.mate.user_service.domain.certification.repository.CertificationRepository;
import site.danjam.mate.user_service.global.util.MultipartUtil;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final String SUFFIX = "auth";
    private final String BUCKET_NAME = "user-auth-img";
    private final CertificationRepository certificationRepository;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final MultipartUtil multipartUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MajorRepository majorRepository;

    @MethodDescription(description = "유저를 생성합니다.")
    @Transactional
    public String signup(JoinDTO joinDto, MultipartFile authImgFile) {
        School school = schoolRepository.findById(joinDto.getSchoolId());
        Major major = majorRepository.findByMajorNameAndSchoolId(joinDto.getMajor(), school.getId());
        String fileName = multipartUtil.determineFileName(authImgFile, joinDto.getUsername(), SUFFIX, BUCKET_NAME);
        Certification certification = createBuildCertification(joinDto, fileName);
        User user = createBuildMyProfile(joinDto, major.getId());
        user.updateSchool(school);
        certification.updateUser(user);

        certificationRepository.save(certification);
        userRepository.save(user);

        return fileName;
    }


    @MethodDescription(description = "빌더 패턴을 통해 User 를 반환받습니다.")
    private Certification createBuildCertification(JoinDTO dto, String authImgUrl) {
        return Certification.builder()
                .name(dto.getName())
                .username(dto.getUsername())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .authImgUrl(authImgUrl)
                .role(Role.STRANGER)
                .build();
    }

    @MethodDescription(description = "빌더 패턴을 통해 MyProfile 를 반환받습니다.")
    private User createBuildMyProfile(JoinDTO dto, Long majorId) {
        return User.builder()
                .birth(dto.getBirth())
                .entryYear(dto.getEntryYear())
                .gender(dto.getGender())
                .nickname(dto.getNickname())
                .majorId(majorId)
                .build();
    }
}
