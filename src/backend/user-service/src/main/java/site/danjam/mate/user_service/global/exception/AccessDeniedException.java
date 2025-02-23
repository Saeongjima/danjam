package site.danjam.mate.user_service.global.exception;

public class AccessDeniedException extends BaseException {
    public AccessDeniedException() {
        super(Code.ACCESS_DENIED);
    }
}
