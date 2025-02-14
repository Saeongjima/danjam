package site.danjam.mate.chat_service.global.client.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoDTO {

    private Long userId;
    private String username;
    private String gender;
}
