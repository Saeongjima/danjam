package site.danjam.mate.common.exception;

public class MultipartException extends BaseException {
    public MultipartException() {
        super(Code.USER_INVALID_FILE_EXTENSION);
    }
}
