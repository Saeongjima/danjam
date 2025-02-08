package site.danjam.mate.chat_service.domain.chatRoom.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.danjam.mate.chat_service.global.common.enums.MateType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalChatRoomCreateDTO {
    private String title;
    private String friendUsername;
    private MateType mateType;

    public static PersonalChatRoomCreateDTO of(String title, String friendUsername, MateType mateType) {
        return PersonalChatRoomCreateDTO.builder()
                .title(title)
                .friendUsername(friendUsername)
                .mateType(mateType)
                .build();
    }
}
