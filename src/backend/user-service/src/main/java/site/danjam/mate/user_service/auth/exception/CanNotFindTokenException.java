package site.danjam.mate.user_service.auth.exception;

import site.danjam.mate.user_service.global.exception.BaseException;
import site.danjam.mate.user_service.global.exception.Code;

public class CanNotFindTokenException extends BaseException {
    public CanNotFindTokenException(String message) {
        super(Code.CAN_NOT_FIND_RESOURCE, message);
    }
}
