package site.danjam.mate.common.exception.global;

import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException(Code code) {
        super(code);
    }

    public InvalidTokenException(Code code, String message) {
        super(code, message);
    }
}
