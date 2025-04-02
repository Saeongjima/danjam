package site.danjam.mate.user_service.domain.certification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.danjam.mate.common.annotation.MethodDescription;
import site.danjam.mate.user_service.domain.certification.repository.CertificationRepository;
import site.danjam.mate.user_service.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class JoinCheckService {
    private final CertificationRepository certificationRepository;
    private final UserRepository userRepository;
    @MethodDescription(description = "유저 이름이 중복되는 지 확인합니다. 중복되지 않으면 true, 중복되면 false")
    public boolean isUsernameDuplicate(String username) {
        return !certificationRepository.existsByUsername(username);
    }

    @MethodDescription(description = "닉네임이 중복되는 지 확인합니다. 중복되지 않으면 true, 중복되면 false")
    public boolean isNicknameDuplicate(String nickName) {
        return !userRepository.existsByNickname(nickName);
    }

    @MethodDescription(description = "이메일이 중복되는 지 확인합니다. 중복되지 않으면 true, 중복되면 false")
    public boolean isEmailDuplicate(String email) {
        return !certificationRepository.existsByEmail(email);
    }
}
