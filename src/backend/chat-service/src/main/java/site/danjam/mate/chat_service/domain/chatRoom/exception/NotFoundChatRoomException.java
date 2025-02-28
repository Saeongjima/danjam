package site.danjam.mate.chat_service.domain.chatRoom.exception;

import static site.danjam.mate.chat_service.global.exception.Code.NOT_FIND_CHATROOM;

import site.danjam.mate.chat_service.global.exception.BaseException;

public class NotFoundChatRoomException extends BaseException {
    public NotFoundChatRoomException() {
        super(NOT_FIND_CHATROOM);
    }
}
