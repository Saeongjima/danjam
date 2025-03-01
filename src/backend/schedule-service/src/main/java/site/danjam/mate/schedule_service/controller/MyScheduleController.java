package site.danjam.mate.schedule_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.danjam.mate.schedule_service.dto.MyScheduleInputDTO;
import site.danjam.mate.schedule_service.dto.MyScheduleOutputDTO;
import site.danjam.mate.schedule_service.global.entity.ApiResponseData;
import site.danjam.mate.schedule_service.service.MyScheduleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule-service/api")
public class MyScheduleController {

    private final MyScheduleService myScheduleService;

    /**
     * 홈에서 내 개인 스케쥴을 생성할 때 사용하는 API
     * @RequestHeader username, role : 유저 정보
     * @param myScheduleInputDTO : MySchedule 생성 DTO
     * @return : ResponseEntity<Map<String, String>>
     **/

    //username은 id값으로 넘어갈 예정
    @PostMapping("/myschedule")
    public void createMySchedule(@RequestBody @Valid MyScheduleInputDTO myScheduleInputDTO,
                                 @RequestHeader("userId") Long userId,
                                 @RequestHeader("role") String role){

        myScheduleService.createMySchedule(myScheduleInputDTO, userId, role);
    }

    @GetMapping("/myschedule")
    public ResponseEntity<ApiResponseData<List<MyScheduleOutputDTO>>> readMySchedule(@RequestHeader("userId") Long userId,
                                                                                     @RequestHeader("role") String role){

        List<MyScheduleOutputDTO> myScheduleOutputDTOS = myScheduleService.readMyScedule(userId, role);
        return ResponseEntity.ok().body(ApiResponseData.success(myScheduleOutputDTOS, "스케줄 조회가 완료되었습니다"));
    }

    @PutMapping("/myschedule/{scheduleId}")
    public ResponseEntity<ApiResponseData<String>> updateMySchedule(@RequestBody @Valid MyScheduleInputDTO myScheduleInputDTO,
                                                                    @RequestHeader("userId") Long userId,
                                                                    @RequestHeader("role") String role, @PathVariable("scheduleId") Long scheduleId){

        myScheduleService.updateMySchedule(myScheduleInputDTO,userId, role, scheduleId);
        return ResponseEntity.ok().body(ApiResponseData.success(null, "스케줄 수정이 완료되었습니다."));

    }

    @DeleteMapping("/myschedule/{scheduleId}")
    public ResponseEntity<ApiResponseData<String>> deleteMySchedule(@RequestHeader("userId") Long userId,
                                                                    @RequestHeader("role") String role, @PathVariable("scheduleId") Long scheduleId){
        myScheduleService.deleteMySchedule(userId, role, scheduleId);
        return ResponseEntity.ok().body(ApiResponseData.success("스케줄 삭제가 완료되었습니다."));
    }

}