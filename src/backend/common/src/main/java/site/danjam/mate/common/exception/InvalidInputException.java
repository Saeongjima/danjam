package site.danjam.mate.common.exception;

public class InvalidInputException extends BaseException {

    public InvalidInputException() {
        super(Code.INVALID_INPUT);
    }

    public InvalidInputException(String message) {
        super(Code.INVALID_INPUT, message);
    }
}

