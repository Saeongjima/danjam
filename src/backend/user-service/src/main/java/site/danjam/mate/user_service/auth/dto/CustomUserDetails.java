package site.danjam.mate.user_service.auth.dto;

import java.util.Collection;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import site.danjam.mate.common.annotation.MethodDescription;
import site.danjam.mate.user_service.domain.user.domain.Certification;
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final Certification certification;

    @MethodDescription(description = "user를 반환합니다.")
    public Certification getCertification() {
        return certification;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> certification.getRole().name());
    }

    @MethodDescription(description = "password를 반환합니다.")
    @Override
    public String getPassword() {
        return certification.getPassword();
    }

    @MethodDescription(description = "username을 반환합니다.")
    @Override
    public String getUsername() {
        return certification.getUsername();
    }

    @MethodDescription(description = "id를 반환합니다.")
    public Long getId() {
        return certification.getId();
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

