package site.danjam.mate.schedule_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * MySchedule을 생성 요청을 위한 DTO
 * @version 1.0.0
 * @Param :
 * title : 스케쥴 제목, null 불가
 * memo : 스케쥴 메모, null 가능
 * startDate : 스케쥴 시작일자(년/월/일/시작시간), null 가능
 * endDate : 스케쥴 종료일자(년/월/일/종료시간), null 가능
 * alarm : 알람시간(년/월/일/시간), null 가능
 */
@Data
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

