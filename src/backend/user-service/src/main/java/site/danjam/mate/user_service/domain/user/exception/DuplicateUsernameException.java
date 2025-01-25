package site.danjam.mate.user_service.domain.user.exception;

import static site.danjam.mate.user_service.global.exception.Code.DUPLICATE_USERNAME;

import site.danjam.mate.user_service.global.exception.BaseException;

public class DuplicateUsernameException extends BaseException {
    public DuplicateUsernameException() {
        super(DUPLICATE_USERNAME);
    }
}
