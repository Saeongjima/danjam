package site.danjam.mate.user_service.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import site.danjam.mate.user_service.domain.school.domain.College;
import site.danjam.mate.user_service.domain.school.domain.Major;
import site.danjam.mate.user_service.domain.school.domain.School;
import site.danjam.mate.user_service.domain.school.repository.CollegeRepository;
import site.danjam.mate.user_service.domain.school.repository.MajorRepository;
import site.danjam.mate.user_service.domain.school.repository.SchoolRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final SchoolRepository schoolRepository;
    private final CollegeRepository collegeRepository;
    private final MajorRepository majorRepository;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        // 학교 정보가 있는지 확인
        if (schoolRepository.findAll().isEmpty()) {
            // 고려대학교 세종캠퍼스 정보 생성
            School koreaSejong = School.builder()
                    .name("korea_sejong")
                    .korName("고려대학교 세종캠퍼스")
                    .collegeList(new ArrayList<>())
                    .build();
            schoolRepository.save(koreaSejong);

            // 홍익대학교 세종캠퍼스 정보 생성
            School hongikSejong = School.builder()
                    .name("hongik_sejong")
                    .korName("홍익대학교 세종캠퍼스")
                    .collegeList(new ArrayList<>())
                    .build();
            schoolRepository.save(hongikSejong);

            // korea_sejong.json 파일 읽기
            try {
                Resource resource = new ClassPathResource("major_college/korea_sejong.json");
                Map<String, String> majorCollegeMap = objectMapper.readValue(resource.getInputStream(), Map.class);

                // 학과별로 단과대학 그룹화
                Map<String, List<String>> collegeMajors = new HashMap<>();
                for (Map.Entry<String, String> entry : majorCollegeMap.entrySet()) {
                    String major = entry.getKey();
                    String college = entry.getValue();
                    collegeMajors.computeIfAbsent(college, k -> new ArrayList<>()).add(major);
                }

                // 단과대학별로 학과 정보 저장
                for (Map.Entry<String, List<String>> entry : collegeMajors.entrySet()) {
                    String collegeName = entry.getKey();
                    List<String> majors = entry.getValue();

                    College college = College.builder()
                            .collegeName(collegeName)
                            .school(koreaSejong)
                            .majorList(new ArrayList<>())
                            .build();
                    collegeRepository.save(college);

                    for (String majorName : majors) {
                        Major major = Major.builder()
                                .majorName(majorName)
                                .college(college)
                                .build();
                        majorRepository.save(major);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to read korea_sejong.json", e);
            }

            // hongik_sejong.json 파일 읽기
            try {
                Resource resource = new ClassPathResource("major_college/hongik_sejong.json");
                Map<String, String> majorCollegeMap = objectMapper.readValue(resource.getInputStream(), Map.class);

                // 학과별로 단과대학 그룹화
                Map<String, List<String>> collegeMajors = new HashMap<>();
                for (Map.Entry<String, String> entry : majorCollegeMap.entrySet()) {
                    String major = entry.getKey();
                    String college = entry.getValue();
                    collegeMajors.computeIfAbsent(college, k -> new ArrayList<>()).add(major);
                }

                // 단과대학별로 학과 정보 저장
                for (Map.Entry<String, List<String>> entry : collegeMajors.entrySet()) {
                    String collegeName = entry.getKey();
                    List<String> majors = entry.getValue();

                    College college = College.builder()
                            .collegeName(collegeName)
                            .school(hongikSejong)
                            .majorList(new ArrayList<>())
                            .build();
                    collegeRepository.save(college);

                    for (String majorName : majors) {
                        Major major = Major.builder()
                                .majorName(majorName)
                                .college(college)
                                .build();
                        majorRepository.save(major);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to read hongik_sejong.json", e);
            }
        }
    }
}