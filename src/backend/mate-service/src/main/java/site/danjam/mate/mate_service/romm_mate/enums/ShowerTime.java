package site.danjam.mate.mate_service.romm_mate.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShowerTime {
    TEN_TO_TWENTY("10분-20분"),
    TWENTY_TO_THIRTY("20분-30분"),
    THIRTY_TO_FORTY("30분-40분"),
    FORTY_OVER("40분이상");

    private final String showerTime;

    public static String enumToString(ShowerTime showerTime) {
        return showerTime.getShowerTime();
    }
}

