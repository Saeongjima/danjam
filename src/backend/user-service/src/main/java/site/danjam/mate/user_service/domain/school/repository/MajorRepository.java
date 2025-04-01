package site.danjam.mate.user_service.domain.school.repository;

import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.user_service.domain.school.domain.Major;
import site.danjam.mate.user_service.domain.school.domain.School;

@Repository
@RequiredArgsConstructor
public class MajorRepository {

    private final MajorJpaRepository jpaRepository;

    public Optional<Major> findById(Long id){
        return jpaRepository.findById(id);
    }

    public Optional<Major> findBySchoolAndMajorName(String major, Long schoolId) {
        return jpaRepository.findByMajorNameAndSchoolId(major, schoolId);
    }
}
