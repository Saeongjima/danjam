package site.danjam.mate.common.security;

import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class GlobalCustomUserDetails implements UserDetails {
    private final String username;
    private final String role;
    private final Long id;
    private final boolean enabled;

    public GlobalCustomUserDetails(String username, String role, Long id) {
        this.username = username;
        this.role = role;
        this.id = id;
        this.enabled = true;
    }

    // 필수 메서드 구현
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    // 사용하지 않음
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

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
        return enabled;
    }


}