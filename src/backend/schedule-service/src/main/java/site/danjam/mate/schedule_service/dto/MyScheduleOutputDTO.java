package site.danjam.mate.schedule_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MyScheduleOutputDTO {
    private String title;
    private String memo;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime alarm;
}
