package site.danjam.mate.schedule_service.dto;


import lombok.Data;
import lombok.Getter;
import site.danjam.mate.schedule_service.global.enums.DayOfWeek;

import java.time.LocalTime;

@Getter
public class MyFixScheduleOutputDTO {
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
