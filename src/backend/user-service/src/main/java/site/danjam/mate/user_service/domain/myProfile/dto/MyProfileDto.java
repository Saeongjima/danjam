package site.danjam.mate.user_service.domain.myProfile.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyProfileDto {
    private String profileImgUrl; //이미지
    private String nickname; //닉네임
    private String birth; //생년월일
    private String major; //전공
    private String greeting; //소개글

    //태그 정보
    private String mbti; // mbti
    private Integer gender; //성별
}
