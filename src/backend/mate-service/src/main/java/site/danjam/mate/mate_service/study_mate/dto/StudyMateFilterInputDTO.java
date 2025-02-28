package site.danjam.mate.mate_service.study_mate.dto;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import site.danjam.mate.mate_service.study_mate.enums.AverageGrade;
import site.danjam.mate.mate_service.study_mate.enums.StudyTime;
import site.danjam.mate.mate_service.study_mate.enums.StudyType;

@Getter
@Builder
public class StudyMateFilterInputDTO {

    private String mbti;
    private String minBirthYear;
    private String maxBirthYear;
    private String minEntryYear;
    private String maxEntryYear;
    private Set<String> colleges;
    private Set<Integer> gender; // 0: 여자, 1: 남자

    private Set<StudyType> preferredStudyTypes; // 희망하는 스터디
    private Set<StudyTime> preferredStudyTimes; // 희망하는 스터디 소요 시간
    private Set<AverageGrade> preferredAverageGrades; // 평균 학점
}
