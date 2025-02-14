package site.danjam.mate.chat_service.domain.chatRoom.dto.request;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.danjam.mate.chat_service.global.common.enums.MateType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupChatRoomRequestDTO {
    private List<String> friendUsernames;
    private String title;
    private MateType mateType;

    @Builder
    public GroupChatRoomRequestDTO(List<String> friendUsernames, String title, MateType mateType) {
        this.friendUsernames = friendUsernames;
        this.title = title;
        this.mateType = mateType;
    }
}
