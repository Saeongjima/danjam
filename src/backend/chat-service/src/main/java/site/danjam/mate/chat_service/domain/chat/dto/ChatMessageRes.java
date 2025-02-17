package site.danjam.mate.chat_service.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.danjam.mate.chat_service.domain.chat.enums.ChatType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRes {
    private Long id;
    private Long chatRoomId;
    private String sender;
    private String message;
    private String timestamp;
    private ChatType chatType;
    private Integer unreadCount;

    public static ChatMessageRes from(ChatMessageDTO chat) {
        return ChatMessageRes.builder()
                .id(chat.getId())
                .chatRoomId(chat.getChatRoomId())
                .sender(chat.getSender())
                .message(chat.getMessage())
                .timestamp(chat.getCreatedAt().toString())
                .chatType(chat.getChatType())
                .unreadCount(chat.getUnreadUsers().size())
                .build();
    }
}