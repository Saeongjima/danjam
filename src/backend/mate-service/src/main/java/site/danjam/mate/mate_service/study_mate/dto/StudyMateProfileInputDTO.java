package site.danjam.mate.mate_service.study_mate.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;
import site.danjam.mate.mate_service.study_mate.enums.AverageGrade;
import site.danjam.mate.mate_service.study_mate.enums.StudyTime;
import site.danjam.mate.mate_service.study_mate.enums.StudyType;

@Getter
public class StudyMateProfileInputDTO {

    @NotEmpty(message = "희망하는 스터디를 선택해주세요.")
    @Size(min = 1, max = 10, message = "희망하는 스터디를 1개이상 10개 이하로 선택해주세요.")
    private Set<StudyType> preferredStudyTypes;

    @NotNull(message = "희망하는 스터디 소요 시간을 선택해주세요.")
    private StudyTime preferredStudyTime;

    private Set<String> userSubjects;

    @NotNull(message = "평균 학점을 선택해주세요.")
    private AverageGrade averageGrade;
}