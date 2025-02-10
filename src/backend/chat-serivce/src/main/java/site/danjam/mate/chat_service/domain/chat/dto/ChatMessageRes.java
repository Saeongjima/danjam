package site.danjam.mate.chat_service.domain.chat.dto;

import lombok.Getter;
import site.danjam.mate.chat_service.domain.chat.enums.ChatType;

@Getter
public class ChatMessageRes {
    private String id;
    private Long chatRoomId;
    private String sender;
    private String message;
    private String timestamp;
    private ChatType chatType;
    private Integer unreadCount; // 추가: 안읽은 인원수 필드
}
