package site.danjam.mate.user_service.domain.user.exception;


import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;

public class DuplicateUsernameException extends BaseException {
    public DuplicateUsernameException() {
        super(Code.USER_DUPLICATE_USERNAME);
    }
}
