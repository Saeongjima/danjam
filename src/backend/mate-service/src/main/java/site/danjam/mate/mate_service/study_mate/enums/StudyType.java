package site.danjam.mate.mate_service.study_mate.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StudyType {
    MAJOR("전공과목 공부"),
    CERTIFICATION("자격증 공부"),
    LANGUAGE("어학 공부"),
    EXAM("고시 공부"),
    JOB_PREPARATION("취업 준비"),
    PROGRAMING("프로그래밍"),
    HOBBY_DEVELOPMENT("취미/자기계발"),
    FREE("자율");

    private final String studyType;
}