package site.danjam.mate.chat_service.domain.chat.exception;

import static site.danjam.mate.chat_service.global.exception.Code.NOT_FIND_CHAT_MESSAGE;

import site.danjam.mate.chat_service.global.exception.BaseException;

public class NotFoundMessageException extends BaseException {
    public NotFoundMessageException() {
        super(NOT_FIND_CHAT_MESSAGE);
    }
}
