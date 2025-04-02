package site.danjam.mate.common.exception.user_service;

import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;

public class CanNotFindUserException extends BaseException {
    public CanNotFindUserException() {
        super(Code.USER_CAN_NOT_FIND_USER);
    }

    public CanNotFindUserException(String message) {
        super(Code.USER_CAN_NOT_FIND_USER, message);
    }

    public CanNotFindUserException(Code code, String message) {
        super(code,message);
    }
}
