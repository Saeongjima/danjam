package site.danjam.mate.user_service.domain.school.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.common.exception.global.CanNotFindResourceException;
import site.danjam.mate.common.exception.Code;
import site.danjam.mate.user_service.domain.school.domain.School;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SchoolRepository {
    private final SchoolJpaRepository jpaRepository;

    public School findById(Long id) {
        return jpaRepository.findById(id)
                .orElseThrow(() -> new CanNotFindResourceException(Code.USER_CAN_NOT_FIND_SCHOOL));
    }

    public School save(School school) {
        return jpaRepository.save(school);
    }

    public List<School> findAll() {
        return jpaRepository.findAll();
    }

}
