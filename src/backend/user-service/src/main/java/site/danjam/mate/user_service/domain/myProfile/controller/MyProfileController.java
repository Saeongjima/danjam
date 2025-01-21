package site.danjam.mate.user_service.domain.myProfile.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.danjam.mate.user_service.domain.myProfile.dto.GreetingDTO;
import site.danjam.mate.user_service.domain.myProfile.dto.MyProfileDTO;
import site.danjam.mate.user_service.domain.myProfile.dto.UpdateLoginDTO;
import site.danjam.mate.user_service.domain.myProfile.dto.UpdateSchoolDTO;
import site.danjam.mate.user_service.domain.myProfile.service.MyProfileInfoService;
import site.danjam.mate.user_service.global.common.dto.ApiResponseData;
import site.danjam.mate.user_service.global.common.dto.ApiResponseMessage;

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

    @PatchMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponseMessage> updateLoginInfo(@RequestHeader("username") String username,
                                                              @RequestPart UpdateLoginDTO dto,
                                                              @RequestPart(required = false) MultipartFile file) {
        myProfileInfoService.updateLoginInfo(username, dto, file);
        return ResponseEntity.ok(ApiResponseMessage.of("회원정보가 정상적으로 업데이트 되었습니다."));
    }

    @PatchMapping(value = "/school", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponseMessage> updateSchoolInfo(@RequestHeader("username") String username,
                                                               @RequestPart @Valid UpdateSchoolDTO dto,
                                                               @RequestPart(required = false) MultipartFile file) {
        myProfileInfoService.updateSchoolInfo(username, dto, file);
        return ResponseEntity.ok(ApiResponseMessage.of("회원정보가 정상적으로 업데이트 되었습니다."));
    }

    @PatchMapping("/greeting")
    public ResponseEntity<ApiResponseMessage> updateGreetingInfo(@RequestHeader("username") String username,
                                                                 @RequestBody @Valid GreetingDTO dto) {
        myProfileInfoService.updateGreeting(username, dto);
        return ResponseEntity.ok(ApiResponseMessage.of("회원정보가 정상적으로 업데이트 되었습니다."));
    }
}
