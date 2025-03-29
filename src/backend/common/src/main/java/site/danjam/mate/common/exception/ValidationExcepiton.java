package site.danjam.mate.common.exception;

public class ValidationExcepiton extends BaseException {
    public ValidationExcepiton() {
        super(Code.VALIDATION_ERROR);
    }

    public ValidationExcepiton(String validationMessage) {
        super(Code.VALIDATION_ERROR, validationMessage);
    }
}
