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
public class ApiResponseError {

    @Schema(description = "응답 코드")
    private Integer code;

    @Schema(description = "응답 메시지")
    private String message;

    public static ApiResponseError of(Code code) {
        return ApiResponseError.builder()
                .code(code.getCode())
                .message(code.getMessage())
                .build();
    }

    public static ApiResponseError of(Code code, String detailMessage) {
        return ApiResponseError.builder()
                .code(code.getCode())
                .message(detailMessage)
                .build();
    }
}
