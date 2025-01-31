package site.danjam.mate.chat_service.domain.mate.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MateType {
    FOODMATE("식사메이트"), ROOMMATE("룸메이트"), WORKOUTMATE("운동메이트"),
    WALKMATE("산책메이트"), STUDYMATE("스터디메이트");
    private final String mateType;
}

