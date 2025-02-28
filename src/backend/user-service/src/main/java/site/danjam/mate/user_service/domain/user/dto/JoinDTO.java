package site.danjam.mate.user_service.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinDTO {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "유저명을 입력해주세요.")
    @Size(min = 2, max = 32, message = "유저명은 2자 이상, 32자 이내여야 합니다.")
    private String username;
    @NotNull(message = "성별을 입력해주세요.")
    private Integer gender;
    @Pattern(regexp = "^\\d{6}$", message = "생년월일은 6자리 숫자여야 합니다.")
    @NotBlank(message = "생년월일을 입력해주세요.")
    private String birth;
    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email(message = "유효하지 않은 이메일입니다.")
    private String email;
    @NotNull(message = "입학년도를 입력해주세요.")
    private Integer entryYear;
    @NotNull(message = "학교를 입력해주세요.")
    private Long schoolId;
    @NotBlank(message = "전공을 입력해주세요.")
    private String major;
    @Size(min = 2, max = 32, message = "닉네임은 2자 이상, 32자 이내여야 합니다.")
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입혁해주세요.")
    @Size(min = 8, max = 32, message = "비밀번호는 8자 이상, 32자 이내여야 합니다.")
    private String password;
}
