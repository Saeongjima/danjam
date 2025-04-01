package site.danjam.mate.common.exception;

public class CanNotFindResourceException extends BaseException {
    public CanNotFindResourceException() {
        super(Code.CAN_NOT_FIND_RESOURCE);
    }

    public CanNotFindResourceException(String message) {
        super(Code.CAN_NOT_FIND_RESOURCE, message);
    }

    public CanNotFindResourceException(Code code){
        super(code);
    }
}
