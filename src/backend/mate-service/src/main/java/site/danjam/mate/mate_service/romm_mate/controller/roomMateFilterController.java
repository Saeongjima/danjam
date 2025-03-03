package site.danjam.mate.mate_service.romm_mate.controller;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.global.common.dto.ApiResponseData;
import site.danjam.mate.mate_service.romm_mate.dto.RoomMateFilterInputDTO;
import site.danjam.mate.mate_service.romm_mate.dto.RoomMateFilterOutputDTO;
import site.danjam.mate.mate_service.romm_mate.enums.ActivityTime;
import site.danjam.mate.mate_service.romm_mate.enums.CleanPeriod;
import site.danjam.mate.mate_service.romm_mate.enums.Level;
import site.danjam.mate.mate_service.romm_mate.enums.ShowerTime;
import site.danjam.mate.mate_service.romm_mate.service.RoomMateFilterService;
import site.danjam.mate.mate_service.study_mate.dto.StudyMateFilterOutputDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("mate-service/api/roommate")
public class roomMateFilterController {

    private final RoomMateFilterService roomMateFilterService;

//    //todo - pagination 적용
//    @MethodDescription(description = "스터디 메이트 프로필들을 조회한다.")
//    @GetMapping("/profiles")
//    public ResponseEntity<ApiResponseData<List<RoomMateFilterOutputDTO>>> getRoomMateFilterOutputDTOList(
//            @RequestHeader("userId") Long userId,
//            @RequestHeader("role") String role,
//            @RequestParam(value = "mbti", required = false) String mbti,
//            @RequestParam(value = "gender", required = false) List<Integer> gender,
//            @RequestParam(value = "minBirthYear", required = false) String minBirthYear,
//            @RequestParam(value = "maxBirthYear", required = false) String maxBirthYear,
//            @RequestParam(value = "minEntryYear", required = false) String minEntryYear,
//            @RequestParam(value = "maxEntryYear", required = false) String maxEntryYear,
//            @RequestParam(value = "colleges", required = false) Set<String> colleges,
//
//            @RequestParam(value = "hopeDormitories", required = false) Set<String> hopeDormitories, //todo- 기숙사 정보 user-service에 추가되어야함.
//            @RequestParam(value = "hopeRoomPersons", required = false) Set<Integer> hopeRoomPersons,
//            @RequestParam(value = "isSmoking", required = false) Set<Boolean> isSmoking,
//            @RequestParam(value = "hotLevel", required = false) Level hotLevel,
//            @RequestParam(value = "coldLevel", required = false) Level coldLevel,
//            @RequestParam(value = "activityTime", required = false) ActivityTime activityTime,
//            @RequestParam(value = "cleanPeriod", required = false) CleanPeriod cleanPeriod,
//            @RequestParam(value = "showerTime", required = false) ShowerTime showerTime,
//            @RequestParam(value = "sleepHabits", required = false) Integer sleepHabits
//
//    ){
//        RoomMateFilterInputDTO roomMateFilterInputDTO = RoomMateFilterInputDTO.builder()
//                .mbti(mbti)
//                .gender(gender)
//                .minBirthYear(minBirthYear)
//                .maxBirthYear(maxBirthYear)
//                .minEntryYear(minEntryYear)
//                .maxEntryYear(maxEntryYear)
//                .colleges(colleges)
//                .hopeDormitories(hopeDormitories)
//                .hopeRoomPersons(hopeRoomPersons)
//                .isSmoking(isSmoking)
//                .preferredHotLevel(hotLevel)
//                .preferredColdLevel(coldLevel)
//                .preferredActivityTime(activityTime)
//                .preferredCleanPeriod(cleanPeriod)
//                .preferredShowerTime(showerTime)
//                .preferredSleepHabits(sleepHabits)
//                .build();
//        return ResponseEntity.ok(ApiResponseData.of(roomMateFilterService.getRoomMateFilterOutputDTOList(roomMateFilterInputDTO,userId, role),"성공적으로 룸메이트 프로필이 필터링 되었습니다."));
//
//    }
}
