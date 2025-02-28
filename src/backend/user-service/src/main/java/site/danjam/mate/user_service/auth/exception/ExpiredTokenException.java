package site.danjam.mate.user_service.auth.exception;

import site.danjam.mate.user_service.global.exception.BaseException;
import site.danjam.mate.user_service.global.exception.Code;

public class ExpiredTokenException extends BaseException {
    public ExpiredTokenException(String message) {
        super(Code.EXPIRED_TOKEN, message);
    }
}
