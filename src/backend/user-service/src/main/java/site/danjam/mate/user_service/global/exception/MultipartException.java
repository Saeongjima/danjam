package site.danjam.mate.user_service.global.exception;

import static site.danjam.mate.user_service.global.exception.Code.INVALID_FILE_EXTENSION;

public class MultipartException extends BaseException {
    public MultipartException() {
        super(INVALID_FILE_EXTENSION);
    }
}
