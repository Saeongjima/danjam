package site.danjam.mate.user_service.domain.myProfile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MyProfileRequestDto {
    @NotNull
    private Long userId;
    @NotBlank
    private String userName;
}
