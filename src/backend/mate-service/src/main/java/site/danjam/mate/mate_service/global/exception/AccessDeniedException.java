package site.danjam.mate.mate_service.global.exception;

public class AccessDeniedException extends BaseException{
    public AccessDeniedException(){
        super(Code.ACCESS_DENIED);
    }
}
