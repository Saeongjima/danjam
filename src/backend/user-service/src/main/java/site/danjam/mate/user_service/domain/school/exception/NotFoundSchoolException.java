package site.danjam.mate.user_service.domain.school.exception;

import static site.danjam.mate.user_service.global.exception.Code.NO_MATCHING_SCHOOL_FOUND;

import site.danjam.mate.user_service.global.exception.BaseException;

public class NotFoundSchoolException extends BaseException {
    public NotFoundSchoolException() {
        super(NO_MATCHING_SCHOOL_FOUND);
    }
}
