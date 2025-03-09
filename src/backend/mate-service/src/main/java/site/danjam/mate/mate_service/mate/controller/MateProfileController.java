package site.danjam.mate.mate_service.mate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.mate_service.mate.enums.MateType;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.global.common.dto.ApiResponseMessage;
import site.danjam.mate.mate_service.mate.service.MateProfileService;
import site.danjam.mate.mate_service.global.util.DataConvert;
import site.danjam.mate.mate_service.utils.MateProfileServiceRegistry;

@RestController
@RequiredArgsConstructor
@RequestMapping("mate-service/api")
public class MateProfileController {

    private final MateProfileServiceRegistry serviceRegistry;

    @MethodDescription(description = "type에 해당되는 메이트 프로필 생성")
    @PostMapping("/{type}/profiles")
    public ResponseEntity<ApiResponseMessage> createProfile(
            @PathVariable String type,
            @RequestBody Object profileInputDTO,
            @RequestHeader("userId") Long userId,
            @RequestHeader("role") String role
    ) {
        // 입력된 타입을 MateType으로 변환
        MateType mateType = DataConvert.stringToMateType(type);

        // MateType에 해당하는 구현체 찾기
        MateProfileService service = serviceRegistry.getService(mateType);
        service.createMateProfile(profileInputDTO, userId, role);
        return ResponseEntity.ok(ApiResponseMessage.of("성공적으로 프로필을 생성하였습니다."));
    }

//    @MethodDescription(description = "type에 해당되는 메이트 프로필 조회")
//    @GetMapping("/{type}/profiles/{nickname}")
//    public ResponseEntity<ApiResponseData<Object>> getProfile(
//            @PathVariable String type,
//            @RequestHeader("username") String username,
//            @RequestHeader("role") String role,
//            @PathVariable String nickname
//    ){
//        // 입력된 타입을 MateType으로 변환
//        MateType mateType = DataConvert.stringToMateType(type);
//        // MateType에 해당되는 구현체 찾기
//        MateProfileService service = serviceRegistry.getService(mateType);
//        return ResponseEntity.ok(ApiResponseData.of(service.getMateProfile(username,role),Code.SUCCESS.getMessage()));
//    }

    @MethodDescription(description = "type에 해당되는 메이트 프로필 수정")
    @PutMapping("/{type}/profiles/{profileId}")
    public ResponseEntity<ApiResponseMessage> updateProfile(
            @PathVariable String type,
            @PathVariable Long profileId,
            @RequestBody Object profileInputDTO,
            @RequestHeader("userId") Long userId,
            @RequestHeader("role") String role
    ){
        // 입력된 타입을 MateType으로 변환
        MateType mateType = DataConvert.stringToMateType(type);
        // MateType에 해당되는 구현체 찾기
        MateProfileService service = serviceRegistry.getService(mateType);

        service.updateMateProfile(profileInputDTO,userId,role,profileId);
        return ResponseEntity.ok(ApiResponseMessage.of("성공적으로 수정하였습니다."));
    }
}
