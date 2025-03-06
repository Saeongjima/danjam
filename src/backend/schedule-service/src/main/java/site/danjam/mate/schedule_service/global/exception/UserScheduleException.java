package site.danjam.mate.schedule_service.global.exception;

import static site.danjam.mate.schedule_service.global.entity.Code.SCHEDULE_ERROR;
public class UserScheduleException extends BaseException{
    public UserScheduleException(String msg) {
        super(SCHEDULE_ERROR,msg);
    }
}
