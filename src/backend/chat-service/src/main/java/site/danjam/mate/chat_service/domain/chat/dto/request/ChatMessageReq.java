package site.danjam.mate.chat_service.domain.chat.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import site.danjam.mate.chat_service.domain.chat.enums.ChatType;

@Getter
@NoArgsConstructor
public class ChatMessageReq {
    private String message;
    private ChatType chatType;

    @Builder
    private ChatCreateReq(String message, ChatType chatType) {
        this.message = message;
        this.chatType = chatType;
    }
}
