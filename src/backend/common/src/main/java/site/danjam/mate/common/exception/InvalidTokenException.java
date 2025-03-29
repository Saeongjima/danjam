package site.danjam.mate.common.exception;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException(Code code) {
        super(code);
    }

    public InvalidTokenException(Code code, String message) {
        super(code, message);
    }
}
