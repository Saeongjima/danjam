package site.danjam.mate.user_service.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInputDTO {

    private String username;
    private String password;
}

