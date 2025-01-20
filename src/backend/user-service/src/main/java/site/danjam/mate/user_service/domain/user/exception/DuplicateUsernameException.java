package site.danjam.mate.user_service.domain.user.exception;

import static site.danjam.mate.user_service.global.exception.Code.CAN_NOT_FIND_USER;

import site.danjam.mate.user_service.global.exception.BaseException;

public class DuplicateUsernameException extends BaseException {
    public DuplicateUsernameException() {
        super(CAN_NOT_FIND_USER);
    }
}
