package site.danjam.mate.user_service.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.danjam.mate.common.annotation.MethodDescription;
import site.danjam.mate.user_service.domain.user.domain.Certification;
import site.danjam.mate.user_service.auth.dto.CustomUserDetails;
import site.danjam.mate.user_service.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @MethodDescription(description = "userId를 기준으로 User를 찾아 CustomUserDetails로 반환합니다.")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Certification certification = userRepository.findByUsername(username);
        return new CustomUserDetails(certification);
    }

}
