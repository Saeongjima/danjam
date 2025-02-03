package site.danjam.mate.mate_service.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// User-service의 Role Enum과 동일
@Getter
@RequiredArgsConstructor
public enum Role {
    STRANGER("ROLE_STRANGER"),
    FULL_USER("ROLE_FULL_USER"),
    AUTH_USER("ROLE_AUTH_USER"),
    ADMIN("ROLE_ADMIN");

    private final String roleValue;
}
