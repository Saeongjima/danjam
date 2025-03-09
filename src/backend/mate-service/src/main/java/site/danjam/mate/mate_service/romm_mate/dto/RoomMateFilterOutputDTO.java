package site.danjam.mate.mate_service.romm_mate.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import site.danjam.mate.mate_service.romm_mate.enums.ActivityTime;
import site.danjam.mate.mate_service.romm_mate.enums.CleanPeriod;
import site.danjam.mate.mate_service.romm_mate.enums.Level;
import site.danjam.mate.mate_service.romm_mate.enums.ShowerTime;
import site.danjam.mate.mate_service.romm_mate.enums.SleepHabit;

@Getter
public class RoomMateFilterOutputDTO {

    private Long userId;
    private Long profileId;
    private String nickname;
    private String mbti;
    private String major;
    private String greeting;
    private String entryYear;
    private String birth;
    private String profileImgUrl;
    private Integer gender;

    //RoomMateProfile정보
    private List<String> hopeDormitories; // 희망 기숙사
    private List<Integer> hopeRoomPersons; // 희망 방 인원
    private List<SleepHabit> sleepHabits; // 수면 습관
    private Boolean isSmoking; // True = 흡연자, False = 비흡연자
    private Level hotLevel; // 더위 정도
    private Level coldLevel; // 추위 정도
    private ActivityTime activityTime; // 활동 시간대
    private CleanPeriod cleanPeriod; // 청소 주기
    private ShowerTime showerTime; // 샤워 시간
}
