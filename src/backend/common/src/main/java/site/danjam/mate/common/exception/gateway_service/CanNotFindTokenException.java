package site.danjam.mate.common.exception.gateway_service;

import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;

public class CanNotFindTokenException extends BaseException {
    public CanNotFindTokenException(String message) {
        super(Code.CAN_NOT_FIND_RESOURCE, message);
    }
}
