package site.danjam.mate.chat_service.domain.chat.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.danjam.mate.chat_service.domain.chat.domaiin.ChatMessage;
import site.danjam.mate.chat_service.domain.chat.enums.ChatType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    private Long id;
    private Long chatRoomId;
    private Long senderId;
    private List<Long> unreadUsers;
    private String message;
    private ChatType chatType;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ChatMessageDTO from(ChatMessage chat) {
        return ChatMessageDTO.builder()
                .id(chat.getId())
                .chatRoomId(chat.getChatRoomId())
                .senderId(chat.getSenderId())
                .unreadUsers(chat.getUnreadUsers())
                .message(chat.getMessage())
                .chatType(chat.getChatType())
                .createdAt(chat.getCreatedDateTime())
                .modifiedAt(chat.getModifiedDateTime())
                .build();
    }
}
