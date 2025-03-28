package site.danjam.mate.common.response;

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

    private Integer code;
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
