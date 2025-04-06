package site.danjam.mate.user_service.domain.certification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import site.danjam.mate.common.annotation.MethodDescription;
import site.danjam.mate.common.exception.global.InvalidInputException;
import site.danjam.mate.user_service.domain.certification.domain.Certification;
import site.danjam.mate.user_service.domain.certification.repository.CertificationRepository;
import site.danjam.mate.user_service.domain.user.dto.UpdateLoginDTO;

@Service
@RequiredArgsConstructor
public class DeleteUserService {

    CertificationRepository certificationRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @MethodDescription(description = "회원을 탈퇴합니다.")
    public void deleteUser(Long userId, UpdateLoginDTO dto) {
        Certification certification = certificationRepository.findByUserId(userId);
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), certification.getPassword())) {
            throw new InvalidInputException("비밀번호가 일치하지 않습니다.");
        }
        certification.softDelete(userId.toString());
        certificationRepository.save(certification);
    }
}
