package site.danjam.mate.schedule_service.global.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.danjam.mate.schedule_service.global.config.MethodDescription;
import site.danjam.mate.schedule_service.global.entity.ApiResponseData;
import site.danjam.mate.schedule_service.global.entity.Code;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @MethodDescription(description = "MethodArgumentNotValidException 발생 시 처리. @Valid 어노테이션을 사용한 DTO의 유효성 검사 실패 시 발생한다.")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseData<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        Code code = Code.VALIDATION_ERROR;
        String detailedMessage = code.getDetailMessage(String.join(", ", errorMessages));
        ApiResponseData<?> response = ApiResponseData.of(code.getCode(), detailedMessage,detailedMessage);
        return ResponseEntity.status(code.getStatus()).body(response);
    }

    @MethodDescription(description = "BaseException 발생 시 처리.")
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponseData<?>> handleBaseException(BaseException e) {
        Code code = e.getErrorCode();
        ApiResponseData<?> response = ApiResponseData.of(code.getCode(), e.getMessage(),null);
        return ResponseEntity.status(code.getStatus()).body(response);
    }

    @MethodDescription(description = "Header 누락시 예외처리")
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiResponseData<?>>
    handleMissingRequestHeaderException(MissingRequestHeaderException e){
        Code code = Code.MISSING_HEADER;
        String missingHeaderName = e.getHeaderName();
        ApiResponseData<?> response = ApiResponseData.of(code.getCode(), e.getMessage()+":"+missingHeaderName,null);
        return ResponseEntity.status(code.getStatus()).body(response);
    }

    @MethodDescription(description = "스케줄 로직 오류 발생시, 예외처리")
    @ExceptionHandler(UserScheduleException.class)
    public ResponseEntity<ApiResponseData<?>>
    handleNotFoundUserExceptionException(UserScheduleException e){
        Code code = Code.SCHEDULE_ERROR;
        ApiResponseData<?> response = ApiResponseData.of(code.getCode(), e.getMessage(),null);
        return ResponseEntity.status(code.getStatus()).body(response);
    }

}

