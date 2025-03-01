package site.danjam.mate.schedule_service.dto;


import lombok.Data;
import site.danjam.mate.schedule_service.global.enums.DayOfWeek;

import java.time.LocalTime;

@Data
public class MyFixScheduleOutputDTO {
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
