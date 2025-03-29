package site.danjam.mate.common.exception;


public class NotFoundMyProfileException extends BaseException {
    public NotFoundMyProfileException() {
        super(Code.USER_CAN_NOT_FIND_MYPROFILE);
    }
}
