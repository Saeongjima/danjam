package site.danjam.mate.common.exception.global;

import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;

public class ValidationExcepiton extends BaseException {
    public ValidationExcepiton() {
        super(Code.VALIDATION_ERROR);
    }

    public ValidationExcepiton(String validationMessage) {
        super(Code.VALIDATION_ERROR, validationMessage);
    }
}
