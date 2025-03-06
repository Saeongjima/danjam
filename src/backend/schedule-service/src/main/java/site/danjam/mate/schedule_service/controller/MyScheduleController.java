package site.danjam.mate.schedule_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.danjam.mate.schedule_service.dto.MyScheduleInputDTO;
import site.danjam.mate.schedule_service.dto.MyScheduleOutputDTO;
import site.danjam.mate.schedule_service.global.config.MethodDescription;
import site.danjam.mate.schedule_service.global.entity.ApiResponseData;
import site.danjam.mate.schedule_service.service.MyScheduleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule-service/api")
public class MyScheduleController {

    private final MyScheduleService myScheduleService;

    @MethodDescription(description = "시간표 생성하기")
    @PostMapping("/myschedule")
    public void createMySchedule(@RequestBody @Valid MyScheduleInputDTO myScheduleInputDTO,
                                 @RequestHeader("userId") Long userId,
                                 @RequestHeader("role") String role){

        myScheduleService.createMySchedule(myScheduleInputDTO, userId, role);
    }
    @MethodDescription(description = "시간표 조회하기")
    @GetMapping("/myschedule")
    public ResponseEntity<ApiResponseData<List<MyScheduleOutputDTO>>> readMySchedule(@RequestHeader("userId") Long userId,
                                                                                     @RequestHeader("role") String role){

        List<MyScheduleOutputDTO> myScheduleOutputDTOS = myScheduleService.readMyScedule(userId, role);
        return ResponseEntity.ok().body(ApiResponseData.success(myScheduleOutputDTOS, "스케줄 조회가 완료되었습니다"));
    }
    @MethodDescription(description = "시간표 수정하기")
    @PutMapping("/myschedule/{scheduleId}")
    public ResponseEntity<ApiResponseData<String>> updateMySchedule(@RequestBody @Valid MyScheduleInputDTO myScheduleInputDTO,
                                                                    @RequestHeader("userId") Long userId,
                                                                    @RequestHeader("role") String role, @PathVariable("scheduleId") Long scheduleId){

        myScheduleService.updateMySchedule(myScheduleInputDTO,userId, role, scheduleId);
        return ResponseEntity.ok().body(ApiResponseData.success(null, "스케줄 수정이 완료되었습니다."));

    }
    @MethodDescription(description = "시간표 삭제하기")
    @DeleteMapping("/myschedule/{scheduleId}")
    public ResponseEntity<ApiResponseData<String>> deleteMySchedule(@RequestHeader("userId") Long userId,
                                                                    @RequestHeader("role") String role, @PathVariable("scheduleId") Long scheduleId){
        myScheduleService.deleteMySchedule(userId, role, scheduleId);
        return ResponseEntity.ok().body(ApiResponseData.success("스케줄 삭제가 완료되었습니다."));
    }

}