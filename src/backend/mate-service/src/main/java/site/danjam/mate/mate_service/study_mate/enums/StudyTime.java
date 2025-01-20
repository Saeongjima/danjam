package site.danjam.mate.mate_service.study_mate.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StudyTime {

    WITHIN_ONE("1시간 이내"),
    ONE_TWO("1시간 ~ 2시간"),
    TWO_THREE("2시간 ~ 3시간"),
    THREE_OVER("3시간 ~");

    private final String studyTime;
}