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
public class PersonalChatRoomCreateDTO {
    private Long chatRoomId;
    private String title;
    private String friendUsername;
    private Long friendId;
    private MateType mateType;

    public static PersonalChatRoomCreateDTO of(ChatRoom chatRoom, String friendUsername) {
        return PersonalChatRoomCreateDTO.builder()
                .chatRoomId(chatRoom.getId())
                .title(chatRoom.getTitle())
                .friendUsername(friendUsername)
                .mateType(chatRoom.getMateType())
                .build();
    }
}
