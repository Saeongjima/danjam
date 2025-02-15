package site.danjam.mate.chat_service.domain.chat.domaiin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.danjam.mate.chat_service.domain.chat.enums.ChatType;
import site.danjam.mate.chat_service.global.common.entity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_message")
public class ChatMessage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long chatRoomId;

    @Column(nullable = false)
    private String sender;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(columnDefinition = "JSON")
    private List<Long> unreadUsers; // userId json

    @Enumerated(EnumType.STRING)
    private ChatType chatType;

    @Builder
    public ChatMessage(Long chatRoomId, String sender, String message, List<Long> unreadUsers, ChatType chatType) {
        this.chatRoomId = chatRoomId;
        this.sender = sender;
        this.message = message;
        this.unreadUsers = unreadUsers;
        this.chatType = chatType;
    }
}
