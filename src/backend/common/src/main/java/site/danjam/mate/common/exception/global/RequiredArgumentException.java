package site.danjam.mate.common.exception.global;

import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;

public class RequiredArgumentException extends BaseException {
    public RequiredArgumentException(Code code) {
        super(code);
    }

    public RequiredArgumentException(Code code, String message) {
        super(code, message);
    }
}

