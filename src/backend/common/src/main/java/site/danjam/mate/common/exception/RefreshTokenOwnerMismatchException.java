package site.danjam.mate.common.exception;

public class RefreshTokenOwnerMismatchException extends BaseException {
    public RefreshTokenOwnerMismatchException(String message) {
        super(Code.CAN_NOT_ACCESS_RESOURCE, message);
    }
}
