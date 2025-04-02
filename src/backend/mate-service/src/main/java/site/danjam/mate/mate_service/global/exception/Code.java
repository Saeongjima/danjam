package site.danjam.mate.mate_service.global.exception;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Code {

    /**
     * 성공 0번대
     */
    SUCCESS(HttpStatus.OK, 0, "성공적으로 처리되었습니다."),

    /**
     * VALIDATION 관련 100번대
     */
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, 100, "잘못된 입력값이 존재합니다."),
    MISSING_HEADER(HttpStatus.BAD_REQUEST, 101, "Header에 값이 존재하지 않습니다."),
    /**
     * 400번대
     */
    //유효하지 않은(잘못된) 입력값(40000 ~ 40099번대)
    INVALID_INPUT(HttpStatus.BAD_REQUEST, 40000, "잘못된 값이 존재합니다."),
    INVALID_QUERY_PARAM(HttpStatus.BAD_REQUEST, 40001, "쿼리 파라미터 타입이 일치하지 않습니다."),
    NULL_INPUT_VALUE(HttpStatus.BAD_REQUEST, 40002, "입력값이 없는 항목이 있습니다."),
    CAN_NOT_FIND_RESOURCE(HttpStatus.BAD_REQUEST, 40005, "해당 리소스를 찾을 수 없습니다."),

    //보안 관련(40300 ~ 40399)
    REQUIRED_LOGIN(HttpStatus.UNAUTHORIZED, 40300, "로그인이 필요합니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 40301, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 40302, "토큰이 만료되었습니다."),
    CAN_NOT_ACCESS_RESOURCE(HttpStatus.FORBIDDEN, 40303, "해당 리소스에 대한 접근 권한이 없습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, 40304, "접근 권한이 없습니다."),

    /**
     * 500번대
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "예기치 못한 서버 오류가 발생했습니다."),

    /**
     * Mate서비스 에러(60000)
     */
    INVALID_MATE_TYPE(HttpStatus.BAD_REQUEST, 60004, "존재하지 않는 메이트 타입입니다."),
    ALREADY_PROFILE_EXIST(HttpStatus.BAD_REQUEST, 60003, "프로필이 이미 존재합니다.");

    private final HttpStatus status;
    private final Integer code;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
        // 결과 예시 - "Validation error - Reason why it isn't valid"
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    public String getDetailMessage(String message) {
        return this.getMessage() + " : " + message;
    }
}