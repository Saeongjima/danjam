package site.danjam.mate.user_service.domain.user.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateLoginDTO {
    private String username;
    private String password;
}
