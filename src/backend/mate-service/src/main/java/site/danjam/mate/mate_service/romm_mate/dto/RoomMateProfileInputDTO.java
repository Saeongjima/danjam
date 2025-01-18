package site.danjam.mate.mate_service.romm_mate.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import site.danjam.mate.mate_service.romm_mate.enums.ActivityTime;
import site.danjam.mate.mate_service.romm_mate.enums.CleanPeriod;
import site.danjam.mate.mate_service.romm_mate.enums.Level;
import site.danjam.mate.mate_service.romm_mate.enums.ShowerTime;
import site.danjam.mate.mate_service.romm_mate.enums.SleepHabit;

@Getter @Setter
public class RoomMateProfileInputDTO {
    @NotEmpty(message = "기숙사 옵션을 선택해주세요.")
    @Size(min = 1, max = 20, message = "기숙사 옵션은 1개 이상 20개 이하로 선택해주세요.")
    private Set<String> hopeDormitories; // 희망 기숙사

    @NotEmpty(message = "방 옵션을 선택해주세요.")
    @Size(min = 1, max = 20, message = "방 인실은 1개 이상 20개 이하로 선택해주세요.")
    private Set<Integer> hopeRoomPersons; // 희망 방 인원

    @NotEmpty(message = "수면 습관을 선택해주세요.")
    @Size(min = 1, max = 5, message = "수면 습관은 1개 이상 5개 이하로 선택해주세요.")
    @Enumerated(EnumType.STRING)
    private Set<SleepHabit> sleepHabits; // 수면 습관

    @NotNull(message = "흡연 여부를 선택해주세요.")
    private Character isSmoking; // Y: 흡연, N: 비흡연

    @NotNull(message = "더위 정도를 선택해주세요.")
    @Enumerated(EnumType.STRING)
    private Level hotLevel; //

    @NotNull(message = "추위 정도를 선택해주세요.")
    @Enumerated(EnumType.STRING)
    private Level coldLevel; //

    @NotNull(message = "활동 시간대를 선택해주세요.")
    @Enumerated(EnumType.STRING)
    private ActivityTime activityTime;

    @NotNull(message = "청소 주기를 선택해주세요.")
    @Enumerated(EnumType.STRING)
    private CleanPeriod cleanPeriod;

    @NotNull(message = "샤워 시간을 선택해주세요.")
    @Enumerated(EnumType.STRING)
    private ShowerTime showerTime;
}
