package site.danjam.mate.mate_service.global.exception;

public class AlreadyProfileExistException extends BaseException{
    public AlreadyProfileExistException(){
        super(Code.ALREADY_PROFILE_EXIST);
    }
}
