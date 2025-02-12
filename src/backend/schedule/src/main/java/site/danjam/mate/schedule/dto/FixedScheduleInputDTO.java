package site.danjam.mate.schedule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import site.danjam.mate.schedule.global.enums.DayOfWeek;

import java.time.LocalTime;

@Data
public class FixedScheduleInputDTO {
    @NotNull(message = "요일을 입력해주세요.")
    private DayOfWeek dayOfWeek;
    @NotNull(message = "시작 시간을 입력해주세요.")
    private LocalTime startTime;
    @NotNull(message = "종료 시간을 입력해주세요.")
    private LocalTime endTime;
}
