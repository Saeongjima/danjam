package site.danjam.mate.user_service.domain.myProfile.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateLoginDTO {
    private String username;
    private String password;
}
