package site.danjam.mate.user_service.domain.myProfile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateSchoolDTO {
    @NotNull(message = "학교를 입력해주세요.")
    private Long schoolId;
    @NotNull(message = "입학년도를 입력해주세요.")
    private Integer entryYear;
    @NotBlank(message = "전공을 입력해주세요.")
    private String major;
}
