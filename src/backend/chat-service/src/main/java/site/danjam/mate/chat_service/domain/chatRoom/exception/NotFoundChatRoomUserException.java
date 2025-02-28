package site.danjam.mate.chat_service.domain.chatRoom.exception;

import static site.danjam.mate.chat_service.global.exception.Code.NOT_FIND_CHATROOM_USER;

import site.danjam.mate.chat_service.global.exception.BaseException;

public class NotFoundChatRoomUserException extends BaseException {
    public NotFoundChatRoomUserException() {
        super(NOT_FIND_CHATROOM_USER);
    }

    public NotFoundChatRoomUserException(String message) {
        super(NOT_FIND_CHATROOM_USER, message);
    }
}
