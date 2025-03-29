package site.danjam.mate.user_service.domain.user.exception;

import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;

public class NotFoundUserException extends BaseException {
    public NotFoundUserException() {
        super(Code.USER_CAN_NOT_FIND_USER);
    }
}
