package site.danjam.mate.user_service.domain.certification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.danjam.mate.common.annotation.MethodDescription;
import site.danjam.mate.user_service.domain.certification.repository.CertificationRepository;

@Service
@RequiredArgsConstructor
public class UserInquiryService {
    private final CertificationRepository certificationRepository;

    @MethodDescription(description = "username을 바탕으로 userId를 조회합니다.")
    public Long getUserId(String username){
        return certificationRepository.findByUsername(username).getId();
    }
}
