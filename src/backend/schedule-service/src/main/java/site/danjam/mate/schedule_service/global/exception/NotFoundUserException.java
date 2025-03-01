package site.danjam.mate.schedule_service.global.exception;

import static site.danjam.mate.schedule_service.global.entity.Code.CAN_NOT_FIND_USER;
public class NotFoundUserException extends BaseException{
    public NotFoundUserException() {
        super(CAN_NOT_FIND_USER);
    }
}

