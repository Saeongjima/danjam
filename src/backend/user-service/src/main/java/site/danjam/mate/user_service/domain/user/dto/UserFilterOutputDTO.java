package site.danjam.mate.user_service.domain.user.dto;

import lombok.Getter;

@Getter
public class UserFilterOutputDTO {
    String nickname;
    String mbti;
    String major;
    String entryYear;
    String birth;
    String profileImgUrl;
    Integer gender;
}
