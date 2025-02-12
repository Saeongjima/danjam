package site.danjam.mate.mate_service.global.exception;

public class CanNotFindResourceException extends BaseException{
    public CanNotFindResourceException() {
        super(Code.CAN_NOT_FIND_RESOURCE);
    }

    public CanNotFindResourceException(String message) {
        super(Code.CAN_NOT_FIND_RESOURCE, message);
    }
}
