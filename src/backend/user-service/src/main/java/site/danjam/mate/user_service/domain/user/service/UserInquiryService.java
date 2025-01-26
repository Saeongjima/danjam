package site.danjam.mate.user_service.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.danjam.mate.user_service.domain.user.repository.UserRepository;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;

@Service
@RequiredArgsConstructor
public class UserInquiryService {
    private final UserRepository userRepository;

    @MethodDescription(description = "username을 바탕으로 userId를 조회합니다.")
    public Long getUserId(String username){
        return userRepository.findByUsername(username).getId();
    }
}
