package site.danjam.mate.mate_service.global.feign.dto;

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
