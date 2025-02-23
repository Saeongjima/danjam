package site.danjam.mate.api_gateway_service.exception;

import site.danjam.mate.api_gateway_service.global.exception.BaseException;
import site.danjam.mate.api_gateway_service.global.exception.Code;

public class ExpiredTokenException extends BaseException {
    public ExpiredTokenException(Code code) {
        super(code);
    }
}