package site.danjam.mate.chat_service.domain.chatRoom.domain;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ChatRoomUserPK implements Serializable {
    private Long user;
    private Long chatRoom;
}
