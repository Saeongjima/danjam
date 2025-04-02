//package site.danjam.mate.user_service.global.util;
//
//import com.github.javafaker.Faker;
//import java.text.SimpleDateFormat;
//import java.util.Locale;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import site.danjam.mate.user_service.domain.myProfile.domain.MyProfile;
//import site.danjam.mate.user_service.domain.school.domain.School;
//import site.danjam.mate.user_service.domain.school.repository.SchoolRepository;
//import site.danjam.mate.user_service.domain.user.domain.Role;
//import site.danjam.mate.user_service.domain.user.domain.User;
//import site.danjam.mate.user_service.domain.user.repository.UserRepository;
//
//@Component
//@RequiredArgsConstructor
//public class DataInitializer implements ApplicationRunner {
//
//    private final SchoolRepository schoolRepository;
//    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    @Override
//    @Transactional
//    public void run(ApplicationArguments args) throws Exception {
//        if(userRepository.existsByUsername("user1")){
//            return;
//        }
//        // 1. School 데이터 삽입
//        School koreaSejong = schoolRepository.save(new School("korea_sejong", "고려대학교 세종캠퍼스"));
//        School hongikSejong = schoolRepository.save(new School("hongik_sejong", "홍익대학교 세종캠퍼스"));
//
//        // 2. Java Faker 초기화 (한국 로케일 사용)
//        Faker faker = new Faker(new Locale("ko"));
//
//        // 날짜 포맷터 (birth 필드에 사용)
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        // MBTI 랜덤 선택을 위한 배열
//        String[] mbtiTypes = { "INTJ", "INTP", "ENTJ", "ENTP",
//                "INFJ", "INFP", "ENFJ", "ENFP",
//                "ISTJ", "ISFJ", "ESTJ", "ESFJ",
//                "ISTP", "ISFP", "ESTP", "ESFP" };
//
//        // 3. 1000명의 MyProfile 및 User 데이터 생성
//        for (int i = 1; i <= 1000; i++) {
//            // MyProfile 데이터 생성
//            MyProfile profile = new MyProfile(
//                    sdf.format(faker.date().birthday(18, 30)), // 18세 ~ 30세 사이의 생일을 랜덤하게 생성
//                    faker.number().numberBetween(2010, 2021), // 입학년도는 2010~2020 사이 랜덤 값
//                    faker.educator().course(), // 전공은 Faker의 educator()를 통해 생성
//                    null
//            );
//
//            // 프로필 이미지 URL은 단순 문자열 조합
//            profile.updateProfileImg("http://example.com/profile" + i + ".jpg");
//
//            // MBTI 랜덤 값
//            profile.updateMbti(mbtiTypes[faker.random().nextInt(mbtiTypes.length)]);
//
//            // 인삿말은 랜덤 문장 생성
//            profile.updateGreeting(faker.lorem().sentence());
//
//            // User 데이터 생성
//            User user = User.builder()
//                    .name("User" + i)
//                    .username("user" + i)
//                    .gender(faker.number().numberBetween(0, 2))
//                    .password(passwordEncoder.encode("user" + i))
//                    .nickname("nickname" + i)
//                    .email("user" + i + "@example.com")
//                    .authImgUrl("http://example.com/auth" + i + ".jpg")
//                    .role(Role.AUTH_USER)
//                    .build();
//
//            // 관계 설정: Builder로 생성 후, update 메서드로 연관관계 연결
//            user.updateMyProfile(profile);
//            user.updateSchool(faker.bool().bool() ? koreaSejong : hongikSejong);
//
//            userRepository.save(user);
//        }
//    }
//}
