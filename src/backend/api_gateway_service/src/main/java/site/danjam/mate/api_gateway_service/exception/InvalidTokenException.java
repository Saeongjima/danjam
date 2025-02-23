package site.danjam.mate.api_gateway_service.exception;

import site.danjam.mate.api_gateway_service.global.exception.BaseException;
import site.danjam.mate.api_gateway_service.global.exception.Code;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException(Code code) {
        super(code);
    }
}
