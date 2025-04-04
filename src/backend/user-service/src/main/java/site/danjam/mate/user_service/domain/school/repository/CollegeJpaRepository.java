package site.danjam.mate.user_service.domain.school.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import site.danjam.mate.user_service.domain.school.domain.College;

public interface CollegeJpaRepository extends JpaRepository<College, Long> {

    List<College> findAllBySchoolId(Long schoolId);
}