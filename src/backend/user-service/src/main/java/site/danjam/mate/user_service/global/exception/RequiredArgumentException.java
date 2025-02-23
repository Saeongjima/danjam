package site.danjam.mate.user_service.global.exception;

public class RequiredArgumentException extends BaseException {
    public RequiredArgumentException(String message) {
        super(Code.REQUIRED_ARGUMENT_EXCEPTION, Code.REQUIRED_ARGUMENT_EXCEPTION.getMessage()+" : " + message);
    }
}
