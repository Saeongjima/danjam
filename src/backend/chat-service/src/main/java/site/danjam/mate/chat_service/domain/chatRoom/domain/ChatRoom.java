package site.danjam.mate.chat_service.domain.chatRoom.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.danjam.mate.chat_service.global.common.entity.BaseTimeEntity;
import site.danjam.mate.chat_service.global.common.enums.MateType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_room")
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private MateType mateType;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private Integer userCount;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<ChatRoomUser> chatRoomUsers = new ArrayList<>();

    @Builder
    public ChatRoom(MateType mateType, String title, Integer userCount) {
        this.mateType = mateType;
        this.title = title;
        this.userCount = userCount;
    }
}
