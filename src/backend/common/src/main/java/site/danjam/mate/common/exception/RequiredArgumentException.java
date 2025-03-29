package site.danjam.mate.common.exception;

public class RequiredArgumentException extends BaseException {
    public RequiredArgumentException(Code code) {
        super(code);
    }

    public RequiredArgumentException(Code code, String message) {
        super(code, message);
    }
}

