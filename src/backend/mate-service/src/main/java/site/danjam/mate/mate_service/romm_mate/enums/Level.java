package site.danjam.mate.mate_service.romm_mate.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Level {
    HIGH("많이타요"),
    LOW("적게타요"),
    NO_MATTER("상관없어요");

    private final String level;
    public static String enumToString(Level level) {
        return level.getLevel();
    }

//    많이타요, 적게타요, 상관없어요
}

