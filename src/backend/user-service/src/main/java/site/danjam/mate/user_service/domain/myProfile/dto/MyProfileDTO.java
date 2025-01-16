package site.danjam.mate.user_service.domain.myProfile.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.danjam.mate.user_service.domain.myProfile.domain.MyProfile;
import site.danjam.mate.user_service.domain.user.domain.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MyProfileDTO {
    private String profileImgUrl; //이미지
    private String nickname; //닉네임
    private String birth; //생년월일
    private String major; //전공
    private String greeting; //소개글

    //태그 정보
    private String mbti; // mbti
    private Integer gender; //성별

    public static MyProfileDTO from(MyProfile myProfile, User user) {
        return MyProfileDTO.builder()
                .profileImgUrl(myProfile.getProfileImgUrl())
                .nickname(user.getNickname())
                .birth(myProfile.getBirth())
                .major(myProfile.getMajor())
                .greeting(myProfile.getGreeting())
                .mbti(myProfile.getMbti())
                .gender(user.getGender())
                .build();
    }
}
