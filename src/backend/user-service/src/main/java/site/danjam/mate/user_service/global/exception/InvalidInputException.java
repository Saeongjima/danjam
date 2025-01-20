package site.danjam.mate.user_service.global.exception;

import static site.danjam.mate.user_service.global.exception.Code.INVALID_INPUT;

public class InvalidInputException extends BaseException {

    public InvalidInputException() {
        super(INVALID_INPUT);
    }

    public InvalidInputException(String message) {
        super(INVALID_INPUT, message);
    }
}

