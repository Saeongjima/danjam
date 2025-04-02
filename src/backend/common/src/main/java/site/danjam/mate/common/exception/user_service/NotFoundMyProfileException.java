package site.danjam.mate.common.exception.user_service;


import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;

public class NotFoundMyProfileException extends BaseException {
    public NotFoundMyProfileException() {
        super(Code.USER_CAN_NOT_FIND_MYPROFILE);
    }
}
