package site.danjam.mate.user_service.domain.school.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.common.exception.global.CanNotFindResourceException;
import site.danjam.mate.common.exception.Code;
import site.danjam.mate.user_service.domain.school.domain.College;

@Repository
@RequiredArgsConstructor
public class CollegeRepository {
    private final CollegeJpaRepository collegeJpaRepository;

    public College findByCollege(Long id) {
        return collegeJpaRepository.findById(id)
                .orElseThrow(() -> new CanNotFindResourceException(Code.USER_CAN_NOT_FIND_COLLEGE));
    }

    public List<College> findAllBySchoolId(Long schoolId) {
        return collegeJpaRepository.findAllBySchoolId(schoolId);
    }

    public College save(College college) {
        return collegeJpaRepository.save(college);
    }
}