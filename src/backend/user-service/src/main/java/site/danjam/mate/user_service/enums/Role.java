package site.danjam.mate.user_service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;

@Getter
@RequiredArgsConstructor
public enum Role {
    STRANGER("ROLE_STRANGER"),
    AUTH_USER("ROLE_AUTH_USER"),
    ADMIN("ROLE_ADMIN");

    private final String roleValue;

    @MethodDescription(description = "roleValue로 Role을 찾아 반환하는 메소드")
    public static Role fromString(String roleStr) {
        for (Role role : Role.values()) {
            if (role.getRoleValue().equalsIgnoreCase(roleStr)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No role with text: " + roleStr);
    }
}
