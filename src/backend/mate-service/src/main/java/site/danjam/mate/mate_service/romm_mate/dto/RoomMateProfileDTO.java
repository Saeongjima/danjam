package site.danjam.mate.mate_service.romm_mate.dto;

import java.util.List;
import java.util.Set;

public class RoomMateProfileDTO {
        private Long profileId; //roomMateProfile의 id
        private Boolean isSmoking;
        private String hotLevel;
        private String coldLevel;
        private String activityTime;
        private String cleanPeriod;
        private String showerTime;
        private Set<String> hopeDormitories;
        private Set<Integer> hopeRoomPersons;
        private Set<String> ownSleepHabits;
}
