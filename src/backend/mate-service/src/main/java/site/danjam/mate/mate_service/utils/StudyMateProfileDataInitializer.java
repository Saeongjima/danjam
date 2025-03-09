package site.danjam.mate.mate_service.utils;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import site.danjam.mate.mate_service.mate.enums.MateType;
import site.danjam.mate.mate_service.study_mate.domain.PreferredStudyType;
import site.danjam.mate.mate_service.study_mate.domain.StudyMateProfile;
import site.danjam.mate.mate_service.study_mate.enums.AverageGrade;
import site.danjam.mate.mate_service.study_mate.enums.StudyTime;
import site.danjam.mate.mate_service.study_mate.enums.StudyType;
import site.danjam.mate.mate_service.study_mate.repository.StudyMateProfileRepository;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StudyMateProfileDataInitializer implements ApplicationRunner {

    private final StudyMateProfileRepository studyMateProfileRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 이미 데이터가 존재하면 초기화를 건너뜁니다.
        if (studyMateProfileRepository.existsStudyMateProfileByUserId(1L)) {
            return;
        }

        Faker faker = new Faker(new Locale("ko"));
        StudyTime[] studyTimes = StudyTime.values();
        AverageGrade[] averageGrades = AverageGrade.values();
        StudyType[] studyTypes = StudyType.values();  // StudyType enum이 정의되어 있다고 가정합니다.

        // 1000건의 StudyMateProfile 데이터 생성 (userId는 1부터 1000)
        for (int i = 1; i <= 1000; i++) {
            // StudyMateProfile 생성 (SuperBuilder 사용)
            StudyMateProfile profile = StudyMateProfile.builder()
                    .userSubjects("[자료구조,컴퓨터공학,알고리즘]")
                    .userId((long) i)
                    .studyTime(studyTimes[faker.random().nextInt(studyTimes.length)])
                    .averageGrade(averageGrades[faker.random().nextInt(averageGrades.length)])
                    .build();

            // PreferredStudyType 생성: 각 프로필마다 1~3개의 선호 스터디 타입을 랜덤 생성
            int preferredCount = faker.number().numberBetween(1, 4);
            Set<PreferredStudyType> preferredStudyTypes = new HashSet<>();
            for (int j = 0; j < preferredCount; j++) {
                PreferredStudyType pst = PreferredStudyType.builder()
                        .studyType(studyTypes[faker.random().nextInt(studyTypes.length)])
                        .studyMateProfile(profile)
                        .build();
                preferredStudyTypes.add(pst);
            }
            // StudyMateProfile에 PreferredStudyType 집합 설정
            profile.setPreferredStudyTypes(preferredStudyTypes);

            // profile 저장 시 Cascade.ALL 옵션에 의해 PreferredStudyType들도 함께 저장됩니다.
            studyMateProfileRepository.save(profile);
        }
    }
}
