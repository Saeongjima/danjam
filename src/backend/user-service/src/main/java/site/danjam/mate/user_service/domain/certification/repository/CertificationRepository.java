package site.danjam.mate.user_service.domain.certification.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.common.exception.user_service.CanNotFindUserException;
import site.danjam.mate.user_service.domain.certification.domain.Certification;

@Repository
@RequiredArgsConstructor
public class CertificationRepository {

    private final CertificationJpaRepository certificationJpaRepository;

    public Certification save(Certification certification) {
        return certificationJpaRepository.save(certification);
    }

    public boolean existsByUsername(String username) {
        return certificationJpaRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return certificationJpaRepository.existsByEmail(email);
    }

    public Certification findByUsername(String username) {
        return certificationJpaRepository.findByUsername(username).orElseThrow(()->new CanNotFindUserException());
    }

    public Optional<Certification> findById(Long userId){
        return certificationJpaRepository.findById(userId);
    }
}
