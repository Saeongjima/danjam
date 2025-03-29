package site.danjam.mate.common.exception;

public class CanNotFindUserException extends BaseException {
    public CanNotFindUserException(String message) {
        super(Code.CAN_NOT_FIND_RESOURCE, message);
    }

    public CanNotFindUserException(Code code, String message) {
        super(code,message);
    }
}
