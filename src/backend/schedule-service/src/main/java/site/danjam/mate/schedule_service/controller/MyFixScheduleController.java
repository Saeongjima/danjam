package site.danjam.mate.schedule_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.danjam.mate.schedule_service.dto.MyFixScheduleInputDTO;
import site.danjam.mate.schedule_service.dto.MyFixScheduleOutputDTO;
import site.danjam.mate.schedule_service.global.entity.ApiResponseData;
import site.danjam.mate.schedule_service.global.entity.Code;
import site.danjam.mate.schedule_service.service.MyFixScheduleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule-service/api/fix")
public class MyFixScheduleController {

    private final MyFixScheduleService myFixScheduleService;

    //고정 시간표 생성하기
    @PostMapping("/myschedule")
    public ResponseEntity<ApiResponseData<String>> setFixedMySchedule(@RequestHeader(name = "userId") Long userId,
                                                                      @RequestHeader(name = "role") String role, @RequestBody @Valid MyFixScheduleInputDTO myFixScheduleInputDTO){

        myFixScheduleService.setMyFixSchedule(userId,role,myFixScheduleInputDTO);
        return ResponseEntity.ok(ApiResponseData.of(Code.SUCCESS.getCode(),"고정 스케쥴 등록 성공",null));
    }

    //고정 시간표 조회하기
    @GetMapping("/myschedule")
    public ResponseEntity<ApiResponseData<List<MyFixScheduleOutputDTO>>> getFixedMySchedule(@RequestHeader(name = "userId") Long userId){
        //서비스 호출
        List<MyFixScheduleOutputDTO> myFixScheduleOutputDTOS = myFixScheduleService.getMyFixSchedule(userId);
        return ResponseEntity.ok(ApiResponseData.of(Code.SUCCESS.getCode(), "고정 스케쥴 조회 성공",myFixScheduleOutputDTOS));
    }

    //고정 시간표 수정하기
    @PutMapping("/myschedule/{schedule_id}")
    public ResponseEntity<ApiResponseData<String>> updateFixedSchedule(@RequestHeader(name = "userId") Long userId,
                                                                       @PathVariable("schedule_id") Long schedule_Id, @RequestBody @Valid MyFixScheduleInputDTO myFixScheduleInputDTO){
        myFixScheduleService.updateMyFixSchedule(userId, schedule_Id, myFixScheduleInputDTO);
        return ResponseEntity.ok(ApiResponseData.success(null,"고정 시간표 수정 완료"));
    }


    //고정 시간표 삭제하기
    @DeleteMapping("/myschedule/{schedule_id}")
    public ResponseEntity<ApiResponseData<String>> deleteFixedSchedule(@RequestHeader(name = "userId") Long userId,
                                                                       @PathVariable("schedule_id") Long schedule_Id){
        myFixScheduleService.deleteMyFixSchedule(userId,schedule_Id);
        return ResponseEntity.ok(ApiResponseData.success(null,"고정 시간표 삭제 완료"));
    }

}