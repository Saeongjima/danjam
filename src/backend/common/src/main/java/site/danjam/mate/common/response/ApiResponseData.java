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
public class ApiResponseData<T> {

    @Schema(description = "응답 코드")
    private final Integer code = Code.SUCCESS.getCode();

    @Schema(description = "응답 메시지")
    private String message = Code.SUCCESS.getMessage(); // default message

    @Schema(description = "응답 데이터")
    private T data;

    public static <T> ApiResponseData<T> of(T data, String message) {
        return ApiResponseData.<T>builder()
                .message(message)
                .data(data)
                .build();
    }
}

