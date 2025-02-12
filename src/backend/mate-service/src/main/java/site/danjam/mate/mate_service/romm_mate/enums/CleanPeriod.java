package site.danjam.mate.mate_service.romm_mate.enums;

import lombok.Getter;

@Getter
public enum CleanPeriod {

    EVERYDAY("매일해요"),
    EVERYWEEK("주마다해요"),
    EVERYMONTH("달마다해요"),
    NO_MATTER("상관없어요");

    private final String cleanPeriod;

    CleanPeriod(String cleanPeriod) {
        this.cleanPeriod = cleanPeriod;
    }

    public static String enumToString(CleanPeriod cleanPeriod) {
        return cleanPeriod.getCleanPeriod();
    }
//    매일해요, 주마다해요, 달마다해요, 상관없어요
}
