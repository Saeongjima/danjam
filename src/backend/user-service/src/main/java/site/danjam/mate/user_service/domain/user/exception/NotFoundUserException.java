package site.danjam.mate.user_service.domain.user.exception;

import static site.danjam.mate.user_service.global.exception.Code.CAN_NOT_FIND_USER;

import site.danjam.mate.user_service.global.exception.BaseException;

public class NotFoundUserException extends BaseException {
    public NotFoundUserException() {
        super(CAN_NOT_FIND_USER);
    }

    public NotFoundUserException(String message) {
        super(CAN_NOT_FIND_USER, message);
    }
}
