package site.danjam.mate.common.exception.global;

import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;

public class ExpiredTokenException extends BaseException {
    public ExpiredTokenException(Code code) {
        super(code);
    }
}