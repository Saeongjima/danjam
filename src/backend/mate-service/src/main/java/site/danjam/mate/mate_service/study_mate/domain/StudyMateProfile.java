package site.danjam.mate.mate_service.study_mate.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.global.common.entity.BaseTimeEntity;
import site.danjam.mate.mate_service.study_mate.dto.StudyMateProfileInputDTO;
import site.danjam.mate.mate_service.study_mate.enums.AverageGrade;
import site.danjam.mate.mate_service.study_mate.enums.StudyTime;
import site.danjam.mate.mate_service.global.util.DataConvert;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StudyMateProfile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    private String userSubjects; //ex) "[데이터베이스,자율주행]"

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StudyTime studyTime;

    @Column(nullable = false)
    @Enumerated
    private AverageGrade averageGrade;

    @Column(nullable = false, unique = true)
    protected Long userId;

    // 선호하는 스터디 종류
    @OneToMany(mappedBy = "studyMateProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PreferredStudyType> preferredStudyTypes;

    @MethodDescription(description = "필드들을 업데이트할 때 사용합니다.")
    public void updateMateProfile(StudyMateProfileInputDTO studyMateProfileInputDTO){
        this.userSubjects = DataConvert.setToString(studyMateProfileInputDTO.getUserSubjects());
        this.studyTime = studyMateProfileInputDTO.getStudyTime();
        this.averageGrade = studyMateProfileInputDTO.getAverageGrade();
    }

    @MethodDescription(description = "희망 스터디 타입만 추출하는 메서드. 프로필을 조회할 때 사용합니다.")
    public Set<String> getPreferredStudyTypesNames() {
        if (preferredStudyTypes == null) {
            return Set.of(); // null인 경우 빈 Set 반환
        }

        return preferredStudyTypes.stream()
                .map(preferredStudyType -> preferredStudyType.getStudyType().toString())
                .collect(Collectors.toSet()); // 중복 제거 후 Set으로 변환
    }

    @MethodDescription(description = "선호하는 스터디 타입을 주입하기 위해 사용하는 메서드.")
    public void setPreferredStudyTypes(Set<PreferredStudyType> preferredStudyTypes) {
        this.preferredStudyTypes = preferredStudyTypes;
    }
}
