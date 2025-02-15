package site.danjam.mate.user_service.global.util;

import static site.danjam.mate.user_service.global.exception.Code.VALIDATION_ERROR;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;
import site.danjam.mate.user_service.global.common.dto.ApiResponseError;
import site.danjam.mate.user_service.global.exception.BaseException;
import site.danjam.mate.user_service.global.exception.Code;
import site.danjam.mate.user_service.global.exception.InvalidInputException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @MethodDescription(description = "@Valid 예외 처리 메서드")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseError> handleMethodArgumentNotValid(MethodArgumentNotValidException e
    ) {
        log.warn("[MethodArgumentNotValidException] {}: {}", e.getClass().getName(), e.getMessage());

        BindingResult bindingResult = e.getBindingResult();
        List<String> errorMessages = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ApiResponseError response = ApiResponseError.of(VALIDATION_ERROR, String.join(", ", errorMessages));
        return ResponseEntity.status(VALIDATION_ERROR.getStatus()).body(response);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponseError> handlerBaseExceptionException(BaseException e) {
        return processCustomErrorResponse(e.getErrorCode(),e.getMessage());
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiResponseError> handlerInvalidInputException(InvalidInputException e) {
        return processCustomErrorResponse(e.getErrorCode(), e.getMessage());
    }


    @MethodDescription(description = "공통 예외 처리 메서드(코드)")
    private ResponseEntity<ApiResponseError> processCustomErrorResponse(Code code) {
        ApiResponseError response = ApiResponseError.of(code);
        return ResponseEntity.status(code.getStatus()).body(response);
    }

    @MethodDescription(description = "공통 예외 처리 메서드(코드, 메시지)")
    private ResponseEntity<ApiResponseError> processCustomErrorResponse(Code code, String message) {
        ApiResponseError response = ApiResponseError.of(code, message);
        return ResponseEntity.status(code.getStatus()).body(response);
    }
}
