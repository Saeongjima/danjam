package site.danjam.mate.user_service.domain.user.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.danjam.mate.user_service.domain.user.dto.UserFilterInputDTO;
import site.danjam.mate.user_service.domain.user.dto.UserFilterOutputDTO;
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
