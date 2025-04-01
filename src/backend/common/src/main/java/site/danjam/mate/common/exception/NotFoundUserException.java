package site.danjam.mate.common.exception;

public class NotFoundUserException extends BaseException {
    public NotFoundUserException() {
        super(Code.USER_CAN_NOT_FIND_USER);
    }
}
