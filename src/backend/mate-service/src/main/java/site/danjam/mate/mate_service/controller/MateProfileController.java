package site.danjam.mate.mate_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.mate_service.enums.MateType;
import site.danjam.mate.mate_service.global.common.dto.ApiResponseMessage;
import site.danjam.mate.mate_service.service.MateProfileService;
import site.danjam.mate.mate_service.utils.MateProfileServiceRegistry;

@RestController
@RequiredArgsConstructor
@RequestMapping("mate-service/api")
public class MateProfileController {

    private final MateProfileServiceRegistry serviceRegistry;

    @PostMapping("/{type}/profile")
    public ResponseEntity<ApiResponseMessage> createProfile(
            @PathVariable MateType type,
            @RequestBody Object profileInputDTO,
            @RequestHeader("username") String username) {

        // MateType에 해당하는 구현체 찾기
        MateProfileService service = serviceRegistry.getService(type);
        service.createMateProfile(profileInputDTO, username);
        return ResponseEntity.ok(new ApiResponseMessage());
    }
}
