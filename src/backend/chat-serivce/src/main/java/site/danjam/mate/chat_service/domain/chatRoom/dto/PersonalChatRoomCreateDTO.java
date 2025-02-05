package site.danjam.mate.chat_service.domain.chatRoom.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.danjam.mate.chat_service.domain.mate.enums.MateType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalChatRoomCreateDTO {
    private String title;
    private Long chatRoomId;
    private MateType mateType;

    public static PersonalChatRoomCreateDTO of(String title, Long chatRoomId, MateType mateType) {
        return PersonalChatRoomCreateDTO.builder()
                .title(title)
                .chatRoomId(chatRoomId)
                .mateType(mateType)
                .build();
    }
}
