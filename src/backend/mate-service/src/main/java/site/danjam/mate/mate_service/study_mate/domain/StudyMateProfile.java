package site.danjam.mate.mate_service.study_mate.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import site.danjam.mate.mate_service.mate.domain.MateProfile;
import site.danjam.mate.mate_service.study_mate.enums.AverageGrade;
import site.danjam.mate.mate_service.study_mate.enums.StudyTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StudyMateProfile extends MateProfile {

    @Column(nullable = false)
    private String preferredStudyTypes; //ex) "[CERTIFICATION,LANGUAGE]"

    @Column(nullable = false)
    private String userSubjects; //ex) "[데이터베이스,자율주행]"

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StudyTime preferredStudyTime;

    @Column(nullable = false)
    @Enumerated
    private AverageGrade averageGrade;

}
