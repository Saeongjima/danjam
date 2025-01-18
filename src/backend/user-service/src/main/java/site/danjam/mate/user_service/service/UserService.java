package site.danjam.mate.user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import site.danjam.mate.user_service.domain.User;
import site.danjam.mate.user_service.enums.Role;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;
import site.danjam.mate.user_service.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @MethodDescription(description = "회원가입 메서드. 로그인 테스트를 위해 구현하였습니다.")
    public void registerUser(String username, String rawPassword) {
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRole(Role.STRANGER);
        userRepository.save(user);
    }
}
