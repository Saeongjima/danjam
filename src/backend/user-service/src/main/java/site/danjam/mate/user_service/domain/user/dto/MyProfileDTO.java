package site.danjam.mate.user_service.domain.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.danjam.mate.user_service.domain.mate.dto.MateProfileDTO;
import site.danjam.mate.user_service.domain.school.domain.Major;
import site.danjam.mate.user_service.domain.user.domain.User;
import site.danjam.mate.user_service.domain.certification.domain.Certification;
import site.danjam.mate.user_service.global.util.DisplayDataConvertor;

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
    private String entryYear; //입학년도

    //태그 정보
    private String mbti; // mbti
    private Integer gender; //성별
    private MateProfileDTO mateProfileDTO;

    public static MyProfileDTO from(User user, Major major  ) {
        return MyProfileDTO.builder()
                .profileImgUrl(user.getProfileImgUrl())
                .nickname(user.getNickname())
                .birth(DisplayDataConvertor.displayBirthYear(user.getBirth()))
                .entryYear(DisplayDataConvertor.displayEntryYear(user.getEntryYear()))
                .major(major.getMajorName())
                .greeting(user.getGreeting())
                .mbti(user.getMbti())
                .gender(user.getGender())
                .build();
    }
}
