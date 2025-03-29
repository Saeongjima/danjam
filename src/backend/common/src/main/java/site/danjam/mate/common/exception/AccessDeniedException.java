package site.danjam.mate.common.exception;

public class AccessDeniedException extends BaseException {
    public AccessDeniedException() {
        super(Code.ACCESS_DENIED);
    }
}
