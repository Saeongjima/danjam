package site.danjam.mate.user_service.domain.school.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.user_service.domain.school.domain.School;
import site.danjam.mate.user_service.domain.school.exception.NotFoundSchoolException;

@Repository
@RequiredArgsConstructor
public class SchoolRepository {
    private final SchoolJpaRepository schoolJpaRepository;

    public School findBySchool(Long id) {
        return schoolJpaRepository.findById(id).orElseThrow(NotFoundSchoolException::new);
    }

    public School save(School school){
        return schoolJpaRepository.save(school);
    }
}
