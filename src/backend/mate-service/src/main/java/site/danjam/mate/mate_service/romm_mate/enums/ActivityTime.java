package site.danjam.mate.mate_service.romm_mate.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityTime {

    MORNING("아침형"),
    NIGHT("새벽형"),
    NO_MATTER("상관없어요");

    private final String activityTime;

    public static String enumToString(ActivityTime activityTime) {
        return activityTime.getActivityTime();
    }
//    아침형, 새벽형, 상관없어요
}
