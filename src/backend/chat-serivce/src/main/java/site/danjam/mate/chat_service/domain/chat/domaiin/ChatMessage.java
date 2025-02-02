package site.danjam.mate.chat_service.domain.chat.domaiin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column
    private String sender;

    @Column
    private String message;

    @Column
    private String files;

    @Column(columnDefinition = "TEXT")
    private String unreadUsers;

    @Builder
    public ChatMessage(Long chatRoomId, String sender, String message, String files, String unreadUsers) {
        this.chatRoomId = chatRoomId;
        this.sender = sender;
        this.message = message;
        this.files = files;
        this.unreadUsers = unreadUsers;
    }
}
