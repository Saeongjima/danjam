package site.danjam.mate.mate_service.mate.exception;

import site.danjam.mate.mate_service.global.exception.BaseException;
import site.danjam.mate.mate_service.global.exception.Code;

public class AlreadyProfileExistException extends BaseException {
    public AlreadyProfileExistException(){
        super(Code.ALREADY_PROFILE_EXIST);
    }
}
