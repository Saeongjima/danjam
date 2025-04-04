package site.danjam.mate.user_service.domain.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.common.response.ApiResponseData;
import site.danjam.mate.user_service.domain.school.dto.MajorResponseDto;
import site.danjam.mate.user_service.domain.school.service.MajorService;

@Tag(name = "학교", description = "학교 관련 API")
@RestController
@RequestMapping("user-service/api/schools")
@RequiredArgsConstructor
public class MajorController {

    private final MajorService majorService;

    @Operation(summary = "학과 목록 조회", description = "요청된 학교의 모든 학과 목록을 조회합니다.\n\n응답 코드 예시:\n- 0: 성공적으로 처리되었습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "0: 성공적으로 처리되었습니다.", content = @Content(schema = @Schema(implementation = ApiResponseData.class))),
            @ApiResponse(responseCode = "500", description = "1: 예기치 못한 서버 오류가 발생했습니다.", content = @Content(schema = @Schema(implementation = ApiResponseData.class)))
    })
    @GetMapping("/{schoolId}/majors")
    public ResponseEntity<ApiResponseData<List<MajorResponseDto>>> getAllMajors(@PathVariable Long schoolId) {
        List<MajorResponseDto> majorList = majorService.getAllMajors(schoolId);
        return ResponseEntity.ok(ApiResponseData.of(majorList, "전공 목록 조회 성공"));
    }
}
