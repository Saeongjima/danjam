package site.danjam.mate.common.exception;

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

    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, 100, "잘못된 입력값이 존재합니다."),
    MISSING_HEADER(HttpStatus.BAD_REQUEST, 101, "Header에 값이 존재하지 않습니다."),

    /**
     * api_gateway 관련 200번대
     */
    GATEWAY_REQUIRED_ARGUMENT_EXCEPTION(HttpStatus.BAD_REQUEST, 201, "입력값이 없는 항목이 있습니다."),
    GATEWAY_REQUIRED_LOGIN(HttpStatus.UNAUTHORIZED, 202, "로그인이 필요합니다."),
    GATEWAY_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 203, "유효하지 않은 토큰입니다."),
    GATEWAY_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 204, "토큰이 만료되었습니다."),
    /**
     * user_service 관련 300번대
     */
    USER_INTERNAL_SEVER_MINIO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 301, "Minio 서버 오류가 발생했습니다."),
    USER_INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST, 302, "지원되지 않는 파일 확장자 입니다."),
    USER_REQUIRED_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, 303, "리프레시 토큰이 필요합니다."),
    USER_REQUIRED_ARGUMENT(HttpStatus.BAD_REQUEST, 304, "필수 입력값이 없습니다."),
    USER_REFRESH_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, 305, "리프레시 토큰이 만료되었습니다."),
    USER_INVALID_TOKEN(HttpStatus.BAD_REQUEST, 306, "유효하지 않은 토큰입니다."),
    USER_CAN_NOT_FIND_USER(HttpStatus.BAD_REQUEST, 307, "해당 유저를 찾을 수 없습니다."),
    USER_CAN_NOT_FIND_MYPROFILE(HttpStatus.BAD_REQUEST, 40103, "프로필을 찾을 수 없습니다."),
    USER_NO_MATCHING_SCHOOL_FOUND(HttpStatus.NOT_FOUND, 40101, "일치하는 학교를 찾을 수 없습니다."),
    USER_DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, 40104, "이미 사용중인 유저 이름입니다."),
    USER_MISMATCH_ID_PW(HttpStatus.BAD_REQUEST, 40105, "아이디와 비밀번호가 일치하지 않습니다."),
    USER_CAN_NOT_FIND_MAJOR(HttpStatus.BAD_REQUEST, 40106, "해당 학과를 찾을 수 없습니다."),
    USER_CAN_NOT_FIND_SCHOOL(HttpStatus.BAD_REQUEST, 40107, "해당 학교를 찾을 수 없습니다."),
    USER_CAN_NOT_FIND_COLLEGE(HttpStatus.BAD_REQUEST, 40108, "단과대학을 찾을 수 없습니다."),
    /**
     * chat_service 관련 400번대
     */

    /**
     * discovery_service 관련 500번대
     */

    /**
     * schedule_service 관련 600번대
     */

    /**
     * mate_service 관련 700번대
     */

    // 유효하지 않은(잘못된) 입력값(40000 ~ 40099번대)
    INVALID_INPUT(HttpStatus.BAD_REQUEST, 40000, "잘못된 값이 존재합니다."),
    INVALID_QUERY_PARAM(HttpStatus.BAD_REQUEST, 40001, "쿼리 파라미터 타입이 일치하지 않습니다."),
    NULL_INPUT_VALUE(HttpStatus.BAD_REQUEST, 40002, "입력값이 없는 항목이 있습니다."),
    ALREADY_PROFILE_EXIST(HttpStatus.BAD_REQUEST, 40003, "프로필이 이미 존재합니다."),
    INVALID_MATE_TYPE(HttpStatus.BAD_REQUEST, 40004, "존재하지 않는 메이트 타입입니다."),
    CAN_NOT_FIND_RESOURCE(HttpStatus.BAD_REQUEST, 4005, "해당 리소스를 찾을 수 없습니다."),

    // 보안 관련(40300 ~ 40399)
    REQUIRED_LOGIN(HttpStatus.UNAUTHORIZED, 40300, "로그인이 필요합니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 40301, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 40302, "토큰이 만료되었습니다."),
    CAN_NOT_ACCESS_RESOURCE(HttpStatus.FORBIDDEN, 40303, "해당 리소스에 대한 접근 권한이 없습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, 40304, "접근 권한이 없습니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1, "예기치 못한 서버 오류가 발생했습니다.");

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
                .orElse(this.message);
    }

    public String getDetailMessage(String message) {
        return this.getMessage() + " : " + message;
    }
}