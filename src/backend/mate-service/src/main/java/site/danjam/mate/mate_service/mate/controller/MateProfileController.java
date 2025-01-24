package site.danjam.mate.mate_service.mate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.mate_service.mate.enums.MateType;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.global.common.dto.ApiResponseData;
import site.danjam.mate.mate_service.global.common.dto.ApiResponseMessage;
import site.danjam.mate.mate_service.global.exception.Code;
import site.danjam.mate.mate_service.mate.service.MateProfileService;
import site.danjam.mate.mate_service.utils.DataConvert;
import site.danjam.mate.mate_service.utils.MateProfileServiceRegistry;

@RestController
@RequiredArgsConstructor
@RequestMapping("mate-service/api")
public class MateProfileController {

    private final MateProfileServiceRegistry serviceRegistry;

    @MethodDescription(description = "type에 해당되는 메이트 프로필 생성")
    @PostMapping("/{type}/profile")
    public ResponseEntity<ApiResponseMessage> createProfile(
            @PathVariable String type,
            @RequestBody Object profileInputDTO,
            @RequestHeader("username") String username,
            @RequestHeader("role") String role
    ) {

        // 입력된 타입을 MateType으로 변환
        MateType mateType = DataConvert.stringToMateType(type);

        // MateType에 해당하는 구현체 찾기
        MateProfileService service = serviceRegistry.getService(mateType);
        service.createMateProfile(profileInputDTO, username, role);
        return ResponseEntity.ok(new ApiResponseMessage());
    }

    @MethodDescription(description = "type에 해당되는 메이트 프로필 조회")
    @GetMapping("/{type}/profile")
    public ResponseEntity<ApiResponseData<Object>> getProfile(
            @PathVariable String type,
            @RequestHeader("username") String username,
            @RequestHeader("role") String role
    ){
        // 입력된 타입을 MateType으로 변환
        MateType mateType = DataConvert.stringToMateType(type);
        // MateType에 해당되는 구현체 찾기
        MateProfileService service = serviceRegistry.getService(mateType);
        return ResponseEntity.ok(ApiResponseData.of(service.getMateProfile(username,role),Code.SUCCESS.getMessage()));
    }
}
