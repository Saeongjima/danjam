package site.danjam.mate.common.exception.user_service;

import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;

public class RefreshTokenOwnerMismatchException extends BaseException {
    public RefreshTokenOwnerMismatchException(String message) {
        super(Code.CAN_NOT_ACCESS_RESOURCE, message);
    }
}
