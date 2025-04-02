package site.danjam.mate.user_service.domain.school.exception;


import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;

public class NotFoundSchoolException extends BaseException {
    public NotFoundSchoolException() {
        super(Code.USER_NO_MATCHING_SCHOOL_FOUND);
    }
}
