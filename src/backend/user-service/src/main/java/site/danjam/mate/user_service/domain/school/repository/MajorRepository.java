package site.danjam.mate.user_service.domain.school.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.common.annotation.MethodDescription;
import site.danjam.mate.common.exception.global.CanNotFindResourceException;
import site.danjam.mate.common.exception.Code;
import site.danjam.mate.user_service.domain.school.domain.College;
import site.danjam.mate.user_service.domain.school.domain.Major;

@Repository
@RequiredArgsConstructor
public class MajorRepository {
    private final MajorJpaRepository jpaRepository;

    public Major findById(Long id) {
        return jpaRepository.findById(id).orElseThrow(()-> new CanNotFindResourceException(Code.USER_CAN_NOT_FIND_MAJOR));
    }

    public Major save(Major major) {
        return jpaRepository.save(major);
    }

    public Major findByMajorNameAndSchoolId(String majorName, Long schoolId) {
        return jpaRepository.findByMajorNameAndCollege_School_Id(majorName, schoolId)
                .orElseThrow(() -> new CanNotFindResourceException(Code.USER_CAN_NOT_FIND_MAJOR));
    }

    public List<Major> findAll() {
        return jpaRepository.findAll();
    }

    public List<Major> findAllByCollegeIn(List<College> collegeList) {
        return jpaRepository.findAllByCollegeIn(collegeList);
    }
}
