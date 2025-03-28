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
public class ApiResponseData<T> {

    private final Integer code = Code.SUCCESS.getCode();
    private String message = Code.SUCCESS.getMessage(); // default message
    private T data;

    public static <T> ApiResponseData<T> of(T data, String message) {
        return ApiResponseData.<T>builder()
                .message(message)
                .data(data)
                .build();
    }
}

