package site.danjam.mate.user_service.domain.certification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.danjam.mate.common.response.ApiResponseMessage;
import site.danjam.mate.user_service.domain.certification.dto.JoinDTO;
import site.danjam.mate.user_service.domain.certification.service.JoinService;

@Tag(name = "회원가입", description = "회원가입 관련 API")
@RestController
@RequestMapping("user-service/api")
@RequiredArgsConstructor()
public class JoinController {
    private final JoinService joinService;

    @PostMapping(value = "/signup", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다. 인증 이미지는 선택적으로 업로드할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = ApiResponseMessage.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResponseMessage.class))),
    })
    public ResponseEntity<ApiResponseMessage> signup(
            @Parameter(description = "회원가입 정보", required = true) @RequestPart @Valid JoinDTO joinDto,

            @Parameter(description = "인증 이미지 파일 (선택사항)") @RequestPart(required = false) MultipartFile authImgFile)
            throws Exception {
        return ResponseEntity.ok(ApiResponseMessage.of(joinService.signup(joinDto, authImgFile)));
    }
}
