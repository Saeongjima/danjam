package site.danjam.mate.mate_service.study_mate.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AverageGrade {

    UNDER_TWO("~2.0"),
    TWO_THREE("2.0 ~ 3.0"),
    THREE_FOUR("3.0 ~ 4.0"),
    FOUR_OVER("4.0 ~ 4.5"),
    NOT_PUBLIC("공개하지 않을래요");

    private final String grade;

}