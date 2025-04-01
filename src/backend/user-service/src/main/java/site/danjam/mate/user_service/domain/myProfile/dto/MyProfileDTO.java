package site.danjam.mate.user_service.domain.myProfile.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.danjam.mate.user_service.domain.mate.dto.MateProfileDTO;
import site.danjam.mate.user_service.domain.myProfile.domain.User;
import site.danjam.mate.user_service.domain.user.domain.Certification;

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
    private MateProfileDTO mateProfileDTO;

    public static MyProfileDTO from(User user, Certification certification, MateProfileDTO mateProfileDTO, String major ) {
        return MyProfileDTO.builder()
                .profileImgUrl(user.getProfileImgUrl())
                .nickname(user.getNickname())
                .birth(user.getBirth())
                .major(major)
                .greeting(user.getGreeting())
                .mbti(user.getMbti())
                .gender(user.getGender())
                .mateProfileDTO(mateProfileDTO)
                .build();
    }
}
