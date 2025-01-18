package site.danjam.mate.user_service.dto;

import java.util.Collection;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import site.danjam.mate.user_service.domain.User;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final User user;

    @MethodDescription(description = "user를 반환합니다.")
    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> "ROLE_" + user.getRole().name());
    }

    @MethodDescription(description = "password를 반환합니다.")
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @MethodDescription(description = "username을 반환합니다.")
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @MethodDescription(description = "id를 반환합니다.")
    public Long getId() {
        return user.getId();
    }

    //UserDetails 인터페이스의 메소드들
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}

