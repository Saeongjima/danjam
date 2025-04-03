package site.danjam.mate.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.danjam.mate.common.exception.Code;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "API 응답 메시지 포맷")
public class ApiResponseMessage {

    @Schema(description = "응답 코드")
    private final Integer code = Code.SUCCESS.getCode();

    @Schema(description = "응답 메시지")
    private String message = Code.SUCCESS.getMessage(); // default message

    public static ApiResponseMessage of(String message) {
        return ApiResponseMessage.builder()
                .message(message)
                .build();
    }
}