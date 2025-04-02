package site.danjam.mate.user_service.global.util;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
@RequiredArgsConstructor
@Order(2) // SchoolInfoDataInitializer 이후에 실행되도록 순서 지정
public class UserInfoDataInitializer implements ApplicationRunner {

    private final MajorRepository majorRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CertificationRepository certificationRepository;

    private static final int TOTAL_USERS = 100000;
    private static final int THREAD_COUNT = 10;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (certificationRepository.existsByUsername("user1")) {
            return;
        }

        // 시작 시간 기록
        long startTime = System.currentTimeMillis();
        log.info("데이터 삽입 시작 시간: {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime));
        log.info("총 생성할 사용자 수: {}", TOTAL_USERS);
        log.info("사용할 스레드 수: {}", THREAD_COUNT);

        List<Major> majors = majorRepository.findAll();
        if (majors.isEmpty()) {
            log.warn("학과 정보가 없습니다. 데이터 삽입을 중단합니다.");
            return;
        }

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        // 스레드별 작업 분배
        int baseUsersPerThread = TOTAL_USERS / THREAD_COUNT;
        int remainingUsers = TOTAL_USERS % THREAD_COUNT;

        int currentStartIndex = 1;
        for (int threadIndex = 0; threadIndex < THREAD_COUNT; threadIndex++) {
            // 남은 사용자를 첫 번째 스레드부터 하나씩 할당
            int usersForThisThread = baseUsersPerThread + (threadIndex < remainingUsers ? 1 : 0);
            final int startIndex = currentStartIndex;
            final int endIndex = currentStartIndex + usersForThisThread - 1;
            currentStartIndex = endIndex + 1;

            log.info("스레드 {}: {} ~ {} 사용자 처리", threadIndex + 1, startIndex, endIndex);

            executorService.submit(() -> {
                try {
                    createUsers(startIndex, endIndex, majors);
                } catch (Exception e) {
                    log.error("사용자 생성 중 오류 발생: {}", e.getMessage(), e);
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.MINUTES);

        // 종료 시간 기록 및 소요 시간 계산
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info("데이터 삽입 종료 시간: {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime));
        log.info("총 소요 시간: {}초 ({}분 {}초)",
                duration / 1000,
                duration / 1000 / 60,
                duration / 1000 % 60);
    }

    @Transactional
    protected void createUsers(int startIndex, int endIndex, List<Major> majors) {
        Random random = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String[] mbtiTypes = { "INTJ", "INTP", "ENTJ", "ENTP",
                "INFJ", "INFP", "ENFJ", "ENFP",
                "ISTJ", "ISFJ", "ESTJ", "ESFJ",
                "ISTP", "ISFP", "ESTP", "ESFP" };

        String password = passwordEncoder.encode("password");
        for (int i = startIndex; i <= endIndex; i++) {
            Major randomMajor = majors.get(random.nextInt(majors.size()));

            User user = User.builder()
                    .nickname("nickname" + i)
                    .gender(random.nextInt(2))
                    .birth(sdf.format(
                            new java.util.Calendar.Builder()
                                    .setDate(1990 + random.nextInt(20), random.nextInt(12), random.nextInt(28) + 1)
                                    .build()
                                    .getTime()))
                    .entryYear(2010 + random.nextInt(16))
                    .majorId(randomMajor.getId())
                    .build();

            user.updateProfileImg("http://example.com/profile" + i + ".jpg");
            user.updateMbti(mbtiTypes[random.nextInt(mbtiTypes.length)]);
            user.updateGreeting("안녕하세요! " + i + "번째 사용자입니다.");
            user.updateSchool(randomMajor.getCollege().getSchool());

            Certification certification = Certification.builder()
                    .name("User" + i)
                    .username("user" + i)
                    .password(password)
                    .email("user" + i + "@example.com")
                    .authImgUrl("http://example.com/auth" + i + ".jpg")
                    .role(Role.AUTH_USER)
                    .build();

            certification.updateUser(user);
            certificationRepository.save(certification);
        }
    }
}
