package site.danjam.mate.user_service.global.util;

import com.github.javafaker.Faker;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.danjam.mate.common.enums.Role;
import site.danjam.mate.user_service.domain.certification.domain.Certification;
import site.danjam.mate.user_service.domain.certification.repository.CertificationRepository;
import site.danjam.mate.user_service.domain.school.domain.Major;
import site.danjam.mate.user_service.domain.school.repository.MajorRepository;
import site.danjam.mate.user_service.domain.user.domain.User;
import site.danjam.mate.user_service.domain.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
@Order(2) // SchoolInfoDataInitializer 이후에 실행되도록 순서 지정
public class UserInfoDataInitializer implements ApplicationRunner {

    private final MajorRepository majorRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CertificationRepository certificationRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (certificationRepository.existsByUsername("user1")) {
            return;
        }

        // 1. Java Faker 초기화 (한국 로케일 사용)
        Faker faker = new Faker(new Locale("ko"));
        Random random = new Random();

        // 날짜 포맷터 (birth 필드에 사용)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // MBTI 랜덤 선택을 위한 배열
        String[] mbtiTypes = { "INTJ", "INTP", "ENTJ", "ENTP",
                "INFJ", "INFP", "ENFJ", "ENFP",
                "ISTJ", "ISFJ", "ESTJ", "ESFJ",
                "ISTP", "ISFP", "ESTP", "ESFP" };

        // 모든 학과 정보 가져오기
        List<Major> majors = majorRepository.findAll();
        if (majors.isEmpty()) {
            return;
        }

        // 2. 1000명의 User 및 Certification 데이터 생성
        for (int i = 1; i <= 1000; i++) {
            // 랜덤한 학과 선택
            Major randomMajor = majors.get(random.nextInt(majors.size()));

            // User 데이터 생성
            User user = User.builder()
                    .nickname("nickname" + i)
                    .gender(random.nextInt(2)) // 0 또는 1
                    .birth(sdf.format(faker.date().birthday(20, 30)))
                    .entryYear(2010 + random.nextInt(16)) // 2010 ~ 2025
                    .majorId(randomMajor.getId())
                    .build();

            // 프로필 이미지 URL
            user.updateProfileImg("http://example.com/profile" + i + ".jpg");

            // MBTI 랜덤 값
            user.updateMbti(mbtiTypes[random.nextInt(mbtiTypes.length)]);

            // 인삿말
            user.updateGreeting(faker.lorem().sentence());

            // 학교 정보 업데이트 (Major -> College -> School)
            user.updateSchool(randomMajor.getCollege().getSchool());

            // Certification 데이터 생성
            Certification certification = Certification.builder()
                    .name("User" + i)
                    .username("user" + i)
                    .password(passwordEncoder.encode("user" + i))
                    .email("user" + i + "@example.com")
                    .authImgUrl("http://example.com/auth" + i + ".jpg")
                    .role(Role.AUTH_USER)
                    .build();

            // User와 Certification 연결
            certification.updateUser(user);

            // 저장
            userRepository.save(user);
            certificationRepository.save(certification);
        }
    }
}
