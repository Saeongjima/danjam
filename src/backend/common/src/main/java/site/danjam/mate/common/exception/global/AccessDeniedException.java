package site.danjam.mate.common.exception.global;

import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;

public class AccessDeniedException extends BaseException {
    public AccessDeniedException() {
        super(Code.ACCESS_DENIED);
    }
}
