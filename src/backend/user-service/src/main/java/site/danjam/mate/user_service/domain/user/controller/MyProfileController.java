package site.danjam.mate.user_service.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.danjam.mate.common.response.ApiResponseData;
import site.danjam.mate.common.response.ApiResponseError;
import site.danjam.mate.common.response.ApiResponseMessage;
import site.danjam.mate.common.security.GlobalCustomUserDetails;
import site.danjam.mate.user_service.domain.user.dto.GreetingDTO;
import site.danjam.mate.user_service.domain.user.dto.MyProfileDTO;
import site.danjam.mate.user_service.domain.user.dto.UpdateLoginDTO;
import site.danjam.mate.user_service.domain.user.dto.UpdateSchoolDTO;
import site.danjam.mate.user_service.domain.user.service.MyProfileInfoService;

@RestController
@RequestMapping("user-service/api/my-profile")
@RequiredArgsConstructor
@Tag(name="마이프로필", description = "마이프로필 관련 API")
public class MyProfileController {

    private final MyProfileInfoService myProfileInfoService;

    @Operation(summary = "마이프로필 조회", description = "마이프로필을 조회합니다.\n\n응답 코드 예시:\n- 0: 성공적으로 처리되었습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "0: 성공적으로 처리되었습니다.", content = @Content(schema = @Schema(implementation = ApiResponseData.class))),
            @ApiResponse(responseCode = "400", description = "100: 잘못된 입력값이 존재합니다.", content = @Content(schema = @Schema(implementation = ApiResponseError.class))),
            @ApiResponse(responseCode = "500", description = "1: 예기치 못한 서버 오류가 발생했습니다.", content = @Content(schema = @Schema(implementation = ApiResponseError.class)))
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('STRANGER','AUTH_USER')")
    public ResponseEntity<ApiResponseData<MyProfileDTO>> readMyProfile(@AuthenticationPrincipal GlobalCustomUserDetails globalCustomUserDetails) {
        return ResponseEntity.ok(ApiResponseData.of(myProfileInfoService.readMyProfileInfo(globalCustomUserDetails.getUserId()), "마이프로필 조회 성공"));
    }

    @Operation(summary = "마이프로필 기본정보 입력(mbti, 인삿말)", description = "마이프로필 기본정보를 입력합니다.\n\n응답 코드 예시:\n- 0: 성공적으로 처리되었습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "0: 성공적으로 처리되었습니다.", content = @Content(schema = @Schema(implementation = ApiResponseMessage.class))),
            @ApiResponse(responseCode = "400", description = "100: 잘못된 입력값이 존재합니다.", content = @Content(schema = @Schema(implementation = ApiResponseError.class))),
            @ApiResponse(responseCode = "500", description = "1: 예기치 못한 서버 오류가 발생했습니다.", content = @Content(schema = @Schema(implementation = ApiResponseError.class)))
    })
    @PutMapping("/basic-info")
    public ResponseEntity<ApiResponseMessage> updateBasicInfo(@AuthenticationPrincipal GlobalCustomUserDetails globalCustomUserDetails,
                                                                 @RequestBody @Valid GreetingDTO dto) {
        myProfileInfoService.updateBasicInfo(globalCustomUserDetails.getUserId(), dto);
        return ResponseEntity.ok(ApiResponseMessage.of("회원정보가 정상적으로 업데이트 되었습니다."));
    }

    @Operation(summary = "학교 정보 수정(학교, 학과, 인증 이미지)", description = "학교 정보를 수정합니다. 유저의 학교, 학과, 인증 이미지 수정.\n\n응답 코드 예시:\n- 0: 성공적으로 처리되었습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "0: 성공적으로 처리되었습니다.", content = @Content(schema = @Schema(implementation = ApiResponseMessage.class))),
            @ApiResponse(responseCode = "400", description = "100: 잘못된 입력값이 존재합니다.", content = @Content(schema = @Schema(implementation = ApiResponseError.class))),
            @ApiResponse(responseCode = "500", description = "1: 예기치 못한 서버 오류가 발생했습니다.", content = @Content(schema = @Schema(implementation = ApiResponseError.class)))
    })
    @PutMapping(value = "/school", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponseMessage> updateSchoolInfo(@AuthenticationPrincipal GlobalCustomUserDetails globalCustomUserDetails,
                                                               @RequestPart @Valid UpdateSchoolDTO dto,
                                                               @RequestPart(required = false) MultipartFile file) {
        myProfileInfoService.updateSchoolInfo(globalCustomUserDetails.getUserId(), dto, file);
        return ResponseEntity.ok(ApiResponseMessage.of("회원정보가 정상적으로 업데이트 되었습니다."));
    }


    @PutMapping(value = "/login-info", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponseMessage> updateLoginInfo(@AuthenticationPrincipal GlobalCustomUserDetails globalCustomUserDetails,
                                                              @RequestPart UpdateLoginDTO dto,
                                                              @RequestPart(required = false) MultipartFile file) {
        myProfileInfoService.updateLoginInfo(globalCustomUserDetails.getUserId(), dto, file);
        return ResponseEntity.ok(ApiResponseMessage.of("회원정보가 정상적으로 업데이트 되었습니다."));
    }

    //todo - 프로필 이미지 수정 api 추가



    @DeleteMapping
    public ResponseEntity<ApiResponseMessage> deleteUser(@RequestHeader("username") String username,
                                                         @RequestBody UpdateLoginDTO dto) {
        myProfileInfoService.deleteUser(username, dto);
        return ResponseEntity.ok(ApiResponseMessage.of("정상적으로 탈퇴 되었습니다."));
    }
}
