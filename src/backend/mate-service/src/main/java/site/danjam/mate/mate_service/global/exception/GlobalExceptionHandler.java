package site.danjam.mate.mate_service.global.exception;

import java.util.List;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.global.common.dto.ApiResponseError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @MethodDescription(description = "MethodArgumentNotValidException 발생 시 처리. @Valid 어노테이션을 사용한 DTO의 유효성 검사 실패 시 발생한다.")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        Code code = Code.VALIDATION_ERROR;
        String detailedMessage = code.getDetailMessage(String.join(", ", errorMessages));
        ApiResponseError response = ApiResponseError.of(code, detailedMessage);
        return ResponseEntity.status(code.getStatus()).body(response);
    }

    @MethodDescription(description = "BaseException 발생 시 처리.")
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponseError> handleBaseException(BaseException e) {
        Code code = e.getErrorCode();
        ApiResponseError response = ApiResponseError.of(code, e.getMessage());
        return ResponseEntity.status(code.getStatus()).body(response);
    }
}
