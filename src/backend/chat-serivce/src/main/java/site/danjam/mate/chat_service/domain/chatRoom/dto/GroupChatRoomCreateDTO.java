package site.danjam.mate.chat_service.domain.chatRoom.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoom;
import site.danjam.mate.chat_service.global.common.enums.MateType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupChatRoomCreateDTO {
    private Long chatRoomId;
    private String title;
    private MateType mateType;

    public static GroupChatRoomCreateDTO from(ChatRoom chatRoom) {
        return GroupChatRoomCreateDTO.builder()
                .chatRoomId(chatRoom.getId())
                .title(chatRoom.getTitle())
                .mateType(chatRoom.getMateType())
                .build();
    }
}
