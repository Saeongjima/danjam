package site.danjam.mate.user_service.global.exception;

public class MultipartException extends BaseException {
    public MultipartException() {
        super(Code.INVALID_FILE_EXTENSION);
    }
}
