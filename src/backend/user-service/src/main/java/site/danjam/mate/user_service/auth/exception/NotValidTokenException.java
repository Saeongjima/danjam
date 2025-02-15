package site.danjam.mate.user_service.auth.exception;

public class NotValidTokenException extends RuntimeException {
    public NotValidTokenException(String message) {
        super(message);
    }
}
