package site.danjam.mate.schedule.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.danjam.mate.schedule.global.entity.ApiResponseData;
import site.danjam.mate.schedule.dto.MyScheduleInputDTO;
import site.danjam.mate.schedule.dto.MyScheduleOutputDTO;
import site.danjam.mate.schedule.service.MyScheduleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
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
                                 @RequestHeader("username") String username,
                                 @RequestHeader("role") String role){

        myScheduleService.createMySchedule(myScheduleInputDTO, username, role);
    }

    @GetMapping("/myschedule")
    public ResponseEntity<ApiResponseData<List<MyScheduleOutputDTO>>> readMySchedule(@RequestHeader("username") String username,
                                                                                     @RequestHeader("role") String role){


    }

    @PutMapping("/myschedule/{username}")
    public ResponseEntity<ApiResponseData<String>> updateMySchedule(@RequestBody @Valid MyScheduleInputDTO myScheduleInputDTO,
                                 @RequestHeader("username") @PathVariable String username,
                               @RequestHeader("role") String role){


    }

    @DeleteMapping("/myschedule/{username}")
    public ResponseEntity<ApiResponseData<String>> deleteMySchedule(@RequestHeader("username") @PathVariable String username,
                               @RequestHeader("role") String role){


    }

}
