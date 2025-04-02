package site.danjam.mate.common.exception.user_service;


import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;

public class DuplicateUsernameException extends BaseException {
    public DuplicateUsernameException() {
        super(Code.USER_DUPLICATE_USERNAME);
    }
}
