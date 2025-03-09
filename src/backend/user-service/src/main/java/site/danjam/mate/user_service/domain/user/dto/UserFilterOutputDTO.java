package site.danjam.mate.user_service.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserFilterOutputDTO {
    Long userId;
    String nickname;
    String mbti;
    String major;
    String entryYear;
    String birth;
    String profileImgUrl;
    String greeting;
    Integer gender;
}
