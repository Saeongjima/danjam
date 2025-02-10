package site.danjam.mate.chat_service.domain.chat.dto;

import java.time.LocalDateTime;
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
public class ChatMessageDTO {
    private String id;
    private Long chatRoomId;
    private String sender;
    private Integer unreadCount;
    private String message;
    private ChatType chatType;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ChatMessageDTO from(ChatMessageRes chat) {
        return new ChatMessageDTO(
                chat.getId(),
                chat.getChatRoomId(),
                chat.getSender(),
                chat.getUnreadCount(),
                chat.getMessage(),
                chat.getChatType(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
