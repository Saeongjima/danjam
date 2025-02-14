package site.danjam.mate.chat_service.domain.chatRoom.exception;

import static site.danjam.mate.chat_service.global.exception.Code.NOT_EQUAL_GENDER;

import site.danjam.mate.chat_service.global.exception.BaseException;

public class InvalidRequestGenderException extends BaseException {
    public InvalidRequestGenderException() {
        super(NOT_EQUAL_GENDER);
    }

    public InvalidRequestGenderException(String message) {
        super(NOT_EQUAL_GENDER, message);
    }
}
