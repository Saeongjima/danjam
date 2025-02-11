package site.danjam.mate.chat_service.domain.chatRoom.exception;

import static site.danjam.mate.chat_service.global.exception.Code.NOT_EQUAL_GENDER;

import site.danjam.mate.chat_service.global.exception.BaseException;

public class InvalidRequestException extends BaseException {
    public InvalidRequestException() {
        super(NOT_EQUAL_GENDER);
    }
}
