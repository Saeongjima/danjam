package site.danjam.mate.user_service.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    STRANGER("ROLE_STRANGER"),
    FULL_USER("ROLE_FULL_USER"),
    AUTH_USER("ROLE_AUTH_USER"),
    ADMIN("ROLE_ADMIN");

    private final String roleValue;
}
