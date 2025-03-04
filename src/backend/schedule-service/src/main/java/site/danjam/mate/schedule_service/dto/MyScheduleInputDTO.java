package site.danjam.mate.schedule_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyScheduleInputDTO {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    private String memo;

    @NotNull(message = "스케쥴 시작일을 입력해주세요.")
    private LocalDateTime startDate;

    @NotNull(message = "스케쥴 종료일을 입력해주세요.")
    private LocalDateTime endDate;

    private LocalDateTime alarm;
}

