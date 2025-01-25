package site.danjam.mate.chat_service.global.exception;

import static site.danjam.mate.chat_service.global.exception.Code.INVALID_FILE_EXTENSION;

public class MultipartException extends BaseException {
    public MultipartException() {
        super(INVALID_FILE_EXTENSION);
    }
}
