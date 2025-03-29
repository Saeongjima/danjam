package site.danjam.mate.common.exception;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException(Code code) {
        super(code);
    }
}
