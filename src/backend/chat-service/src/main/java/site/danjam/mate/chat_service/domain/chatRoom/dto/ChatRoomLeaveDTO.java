package site.danjam.mate.chat_service.domain.chatRoom.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoom;
import site.danjam.mate.chat_service.global.common.enums.MateType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomLeaveDTO {
    private String title;
    private Long chatRoomId;
    private MateType mateType;

    @Builder
    public ChatRoomLeaveDTO(String title, Long chatRoomId, MateType mateType) {
        this.title = title;
        this.chatRoomId = chatRoomId;
        this.mateType = mateType;
    }

    public static ChatRoomLeaveDTO from(ChatRoom chatRoom) {
        return ChatRoomLeaveDTO.builder()
                .title(chatRoom.getTitle())
                .chatRoomId(chatRoom.getId())
                .mateType(chatRoom.getMateType())
                .build();
    }
}
