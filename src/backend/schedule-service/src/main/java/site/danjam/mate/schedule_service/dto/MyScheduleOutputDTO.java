package site.danjam.mate.schedule_service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyScheduleOutputDTO {
    private String title;
    private String memo;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime alarm;
}
