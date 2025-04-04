package site.danjam.mate.user_service.domain.certification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.common.response.ApiResponseData;
import site.danjam.mate.common.response.ApiResponseMessage;
import site.danjam.mate.user_service.domain.certification.service.JoinCheckService;
@RestController
@RequestMapping("user-service/api/join-check")
@RequiredArgsConstructor
@Tag(name = "회원가입")
public class JoinCheckController {
    private final JoinCheckService joinCheckService;

    @GetMapping("/username")

    @Operation(
            summary = "아이디(username) 중복 확인",
            description = "입력한 username이 이미 사용 중인지 확인하고, 사용 가능 여부를 반환합니다.\n\n응답 코드 예시:\n- 0 : 사용 여부를 확인했습니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ApiResponseData.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResponseData.class))),
    })
    public ResponseEntity<ApiResponseData<String>> checkUsernameAvailability(@RequestParam("username") String username) {
        return ResponseEntity.ok(ApiResponseData.of(String.valueOf(joinCheckService.isUsernameDuplicate(username)), "사용 여부를 확인했습니다."));
    }

    @GetMapping("/email")
    @Operation(
            summary = "이메일 중복 확인",
            description = "입력한 email이 이미 사용 중인지 확인하고, 사용 가능 여부를 반환합니다.\n\n응답 코드 예시:\n- 0 : 사용 여부를 확인했습니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ApiResponseData.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResponseData.class))),
    })
    public ResponseEntity<ApiResponseData<String>> checkEmailAvailability(@RequestParam("email") String email) {
        return ResponseEntity.ok(ApiResponseData.of(String.valueOf(joinCheckService.isEmailDuplicate(email)), "사용 여부를 확인했습니다."));
    }

    @GetMapping("/nickname")
    @Operation(
            summary = "닉네임 중복 확인",
            description = "입력한 nickname이 이미 사용 중인지 확인하고, 사용 가능 여부를 반환합니다.\n\n응답 코드 예시:\n- 0 : 사용 여부를 확인했습니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ApiResponseData.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResponseData.class))),
    })
    public ResponseEntity<ApiResponseMessage> checkNicknameAvailability(@RequestParam("nickname") String nickname) {
        return ResponseEntity.ok(ApiResponseMessage.of(String.valueOf(joinCheckService.isNicknameDuplicate(nickname))));
    }
}
