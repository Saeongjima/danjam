package site.danjam.mate.schedule_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import site.danjam.mate.schedule_service.global.enums.DayOfWeek;

import java.time.LocalTime;

@Getter
public class MyFixScheduleInputDTO {

    @NotNull(message = "요일을 입력해주세요.")
    private DayOfWeek dayOfWeek;
    @NotNull(message = "시작 시간을 입력해주세요.")
    private LocalTime startTime;
    @NotNull(message = "종료 시간을 입력해주세요.")
    private LocalTime endTime;

}
