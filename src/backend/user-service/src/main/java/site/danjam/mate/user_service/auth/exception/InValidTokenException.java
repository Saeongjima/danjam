package site.danjam.mate.user_service.auth.exception;

import site.danjam.mate.user_service.global.exception.BaseException;

public class InValidTokenException extends BaseException {
    public InValidTokenException(String message) {
        super(message);
    }
}
