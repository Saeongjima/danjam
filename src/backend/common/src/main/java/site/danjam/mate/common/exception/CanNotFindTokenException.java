package site.danjam.mate.common.exception;

public class CanNotFindTokenException extends BaseException {
    public CanNotFindTokenException(String message) {
        super(Code.CAN_NOT_FIND_RESOURCE, message);
    }
}
