package site.danjam.mate.mate_service.romm_mate.dto;

import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import site.danjam.mate.mate_service.romm_mate.enums.ActivityTime;
import site.danjam.mate.mate_service.romm_mate.enums.CleanPeriod;
import site.danjam.mate.mate_service.romm_mate.enums.Level;
import site.danjam.mate.mate_service.romm_mate.enums.ShowerTime;

@Getter
@Builder
public class RoomMateFilterInputDTO {

    private String mbti;
    private String minBirthYear;
    private String maxBirthYear;
    private String minEntryYear;
    private String maxEntryYear;
    private Set<String> colleges;
    private List<Integer> gender; // 0: 여자, 1: 남자

    private Set<String> hopeDormitories;
    private Set<Integer> hopeRoomPersons;
    private Set<Boolean> isSmoking;
    private Level preferredHotLevel;
    private Level preferredColdLevel;
    private ActivityTime preferredActivityTime;
    private CleanPeriod preferredCleanPeriod;
    private ShowerTime preferredShowerTime;
    private Integer preferredSleepHabits;

}
