package site.danjam.mate.mate_service.romm_mate.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SleepHabit {

    SENSITIVE_TO_SOUND("소리에예민해요"),
    GRIND_TEETH("이를갈아요"),
    SNORE("코를골아요"),
    SENSITIVE_TO_LIGHT("빛에민감해요"),
    NO_MATTER("없어요");

    private final String sleepHabit;

    public static String enumToString(SleepHabit sleepHabit) {
        return sleepHabit.getSleepHabit();
    }
//    소리에예민해요, 이를갈아요, 코를골아요, 빛에민감해요, 없어요
}
