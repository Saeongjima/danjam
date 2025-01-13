package site.danjam.mate.user_service.domain.myProfile.exception;

import static site.danjam.mate.user_service.global.exception.Code.CAN_NOT_FIND_MYPROFILE;

import site.danjam.mate.user_service.global.exception.BaseException;

public class NotFoundMyProfileException extends BaseException {
    public NotFoundMyProfileException() {
        super(CAN_NOT_FIND_MYPROFILE);
    }

    public NotFoundMyProfileException(String message) {
        super(CAN_NOT_FIND_MYPROFILE, message);
    }
}
