package site.danjam.mate.chat_service.domain.chatRoom.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import site.danjam.mate.chat_service.global.common.entity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ChatRoomUserPK.class)
@Table(name = "chat_room_user")
public class ChatRoomUser extends BaseTimeEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ChatRoom chatRoom;

    @Id
    @Column(nullable = false)
    private Long userId;

    @Builder
    public ChatRoomUser(ChatRoom chatRoom, Long userId) {
        this.chatRoom = chatRoom;
        this.userId = userId;
    }
}
