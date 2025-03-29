package site.danjam.mate.common.exception;

public class ExpiredTokenException extends BaseException {
    public ExpiredTokenException(Code code) {
        super(code);
    }
}