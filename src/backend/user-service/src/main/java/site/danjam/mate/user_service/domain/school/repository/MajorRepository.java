package site.danjam.mate.user_service.domain.school.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.common.exception.CanNotFindResourceException;
import site.danjam.mate.common.exception.Code;
import site.danjam.mate.user_service.domain.school.domain.Major;
import site.danjam.mate.user_service.domain.school.domain.School;

@Repository
@RequiredArgsConstructor
public class MajorRepository {
    private final MajorJpaRepository jpaRepository;

    public Optional<Major> findById(Long id) {
        return jpaRepository.findById(id);
    }

    public Major save(Major major) {
        return jpaRepository.save(major);
    }

    public List<Major> saveAll(List<Major> majors) {
        return jpaRepository.saveAll(majors);
    }

    public Major findByMajorNameAndSchoolId(String majorName, Long schoolId) {
        return jpaRepository.findByMajorNameAndCollege_School_Id(majorName, schoolId)
                .orElseThrow(() -> new CanNotFindResourceException(Code.USER_CAN_NOT_FIND_MAJOR));
    }
}
