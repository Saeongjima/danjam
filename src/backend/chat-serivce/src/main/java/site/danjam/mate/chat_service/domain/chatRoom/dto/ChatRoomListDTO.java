package site.danjam.mate.chat_service.domain.chatRoom.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.danjam.mate.chat_service.domain.chat.domaiin.ChatMessage;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoom;
import site.danjam.mate.chat_service.global.common.enums.MateType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomListDTO {

    private Long chatRoomId;
    private String title;
    private MateType mateType;
    private String lastChat;
    private LocalDateTime lastChatTime;
    private Integer unreadUserCount;

    @Builder
    public ChatRoomListDTO(Long chatRoomId, String title, String lastChat, MateType mateType,
                           LocalDateTime lastChatTime,
                           Integer unreadUserCount) {
        this.chatRoomId = chatRoomId;
        this.title = title;
        this.mateType = mateType;
        this.lastChat = lastChat;
        this.lastChatTime = lastChatTime;
        this.unreadUserCount = unreadUserCount;
    }

    public static ChatRoomListDTO of(ChatRoom chatRoom, ChatMessage chat, Integer unreadUserCount) {
        return ChatRoomListDTO.builder()
                .chatRoomId(chatRoom.getId())
                .title(chatRoom.getTitle())
                .mateType(chatRoom.getMateType())
                .lastChat(chat != null ? chat.getMessage() : "No Message")
                .lastChatTime(chat != null ? chat.getCreatedDateTime() : null)
                .unreadUserCount(unreadUserCount)
                .build();
    }
}
