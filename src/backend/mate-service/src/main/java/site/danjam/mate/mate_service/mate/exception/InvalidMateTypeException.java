package site.danjam.mate.mate_service.mate.exception;

import site.danjam.mate.mate_service.global.exception.BaseException;
import site.danjam.mate.mate_service.global.exception.Code;

public class InvalidMateTypeException extends BaseException {
    public InvalidMateTypeException(Code code) {
        super(code);
    }
}
