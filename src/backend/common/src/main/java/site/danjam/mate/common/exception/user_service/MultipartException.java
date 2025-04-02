package site.danjam.mate.common.exception.user_service;

import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;

public class MultipartException extends BaseException {
    public MultipartException() {
        super(Code.USER_INVALID_FILE_EXTENSION);
    }
}
