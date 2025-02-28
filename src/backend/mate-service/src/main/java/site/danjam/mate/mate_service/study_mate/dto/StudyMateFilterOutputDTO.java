package site.danjam.mate.mate_service.study_mate.dto;

import java.util.Set;
import lombok.Getter;
import site.danjam.mate.mate_service.study_mate.enums.AverageGrade;
import site.danjam.mate.mate_service.study_mate.enums.StudyTime;
import site.danjam.mate.mate_service.study_mate.enums.StudyType;

@Getter
public class StudyMateFilterOutputDTO {

    private String nickname;
    private String mbti;
    private String major;
    private String greeting;
    private String entryYear;
    private String birth;
    private String profileImgUrl;
    private Integer gender;

    private StudyTime studyTime;
    private AverageGrade averageGrade;
    private Set<StudyType> preferredStudyTypes;
    private String subjects;
}
