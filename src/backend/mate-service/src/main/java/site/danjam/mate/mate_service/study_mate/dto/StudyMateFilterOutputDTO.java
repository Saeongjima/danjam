package site.danjam.mate.mate_service.study_mate.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import site.danjam.mate.mate_service.global.feign.dto.UserFilterOutputDTO;
import site.danjam.mate.mate_service.study_mate.domain.StudyMateProfile;
import site.danjam.mate.mate_service.study_mate.enums.AverageGrade;
import site.danjam.mate.mate_service.study_mate.enums.StudyTime;
import site.danjam.mate.mate_service.study_mate.enums.StudyType;

@Getter
public class StudyMateFilterOutputDTO {

    private Long userId;
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
    private List<StudyType> preferredStudyTypes;
    private String subjects;

    public StudyMateFilterOutputDTO(UserFilterOutputDTO userDto, StudyMateProfile studyMateProfile) {
        // User 관련 정보 (UserFilterOutputDTO)
        this.userId = userDto.getUserId();
        this.nickname = userDto.getNickname();
        this.mbti = userDto.getMbti();
        this.major = userDto.getMajor();
        this.greeting = userDto.getGreeting();
        this.entryYear = userDto.getEntryYear();
        this.birth = userDto.getBirth();
        this.profileImgUrl = userDto.getProfileImgUrl();
        this.gender = userDto.getGender();

        // StudyMateProfile 관련 정보
        this.studyTime = studyMateProfile.getStudyTime();
        this.averageGrade = studyMateProfile.getAverageGrade();
        // preferredStudyTypes: StudyMateProfile에서 PreferredStudyType을 가지고 있고, 그 안의 StudyType을 추출한다고 가정합니다.
        this.preferredStudyTypes = studyMateProfile.getPreferredStudyTypes().stream()
                .map(preferred -> preferred.getStudyType())
                .collect(Collectors.toList());
        this.subjects = studyMateProfile.getUserSubjects();
    }
}
