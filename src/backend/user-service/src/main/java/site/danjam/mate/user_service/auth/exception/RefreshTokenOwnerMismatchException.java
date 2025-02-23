package site.danjam.mate.user_service.auth.exception;

import site.danjam.mate.user_service.global.exception.BaseException;
import site.danjam.mate.user_service.global.exception.Code;

public class RefreshTokenOwnerMismatchException extends BaseException {
    public RefreshTokenOwnerMismatchException(String message) {
        super(Code.CAN_NOT_ACCESS_RESOURCE, message);
    }
}
