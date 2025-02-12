package site.danjam.mate.schedule.global.exception;

import static site.danjam.mate.schedule.global.entity.Code.CAN_NOT_FIND_USER;
public class NotFoundUserException extends BaseException{
    public NotFoundUserException() {
        super(CAN_NOT_FIND_USER);
    }
}
