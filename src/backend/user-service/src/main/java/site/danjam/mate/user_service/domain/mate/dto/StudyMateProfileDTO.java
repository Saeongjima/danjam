package site.danjam.mate.user_service.domain.mate.dto;

import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudyMateProfileDTO {

    private Long profileId;
    private Set<String> preferredStudyTypes; //선호하는 스터디 종류
    private String preferredStudyTime; //원하는 스터디 시간
    private Set<String> userSubjects; //수강중인 과목
    private String averageGrade; //평균 학점
}