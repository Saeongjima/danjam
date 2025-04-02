package site.danjam.mate.common.exception.global;

import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;

public class InvalidInputException extends BaseException {

    public InvalidInputException() {
        super(Code.INVALID_INPUT);
    }

    public InvalidInputException(String message) {
        super(Code.INVALID_INPUT, message);
    }
}

