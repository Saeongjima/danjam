package site.danjam.mate.user_service.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GreetingDTO {
    @NotNull(message = "MBTI를 입력해주세요.")
    @Pattern(regexp = "^(i|e)(n|s)(f|t)(p|j)$", message = "존재하지 않는 MBTI 유형입니다.")
    private String mbti;

    @NotBlank(message = "소개글을 입력해주세요.")
    @Size(max = 40, message = "소개글은 40자 이내여야 합니다.")
    private String greeting;
}
