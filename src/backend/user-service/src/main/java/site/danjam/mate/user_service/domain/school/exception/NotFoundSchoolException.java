package site.danjam.mate.user_service.domain.school.exception;

import site.danjam.mate.user_service.global.exception.BaseException;
import site.danjam.mate.user_service.global.exception.Code;

public class NotFoundSchoolException extends BaseException {
    public NotFoundSchoolException() {
        super(Code.NO_MATCHING_SCHOOL_FOUND);
    }
}
