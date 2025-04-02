package site.danjam.mate.common.exception.mate_service;

import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;

public class AlreadyProfileExistException extends BaseException {
    public AlreadyProfileExistException() {
        super(Code.ALREADY_PROFILE_EXIST);
    }
}
