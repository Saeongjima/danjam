package site.danjam.mate.user_service.domain.myProfile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.user_service.domain.myProfile.dto.MyProfileDTO;
import site.danjam.mate.user_service.domain.myProfile.service.MyProfileInfoService;
import site.danjam.mate.user_service.global.common.dto.ApiResponseData;

@RestController
@RequestMapping("/api/myProfile")
@RequiredArgsConstructor
public class MyProfileController {

    private final MyProfileInfoService myProfileInfoService;

    @GetMapping
    public ResponseEntity<ApiResponseData<MyProfileDTO>> readMyProfile(
            @RequestHeader("username") String username) {
        return ResponseEntity.ok(ApiResponseData.of(myProfileInfoService.readMyProfileInfo(username), "마이프로필 조회 성공"));
    }
}
