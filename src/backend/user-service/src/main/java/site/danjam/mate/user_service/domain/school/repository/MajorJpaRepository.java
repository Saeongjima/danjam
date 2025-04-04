package site.danjam.mate.user_service.domain.school.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import site.danjam.mate.user_service.domain.school.domain.College;
import site.danjam.mate.user_service.domain.school.domain.Major;

public interface MajorJpaRepository extends JpaRepository<Major, Long> {
    Optional<Major> findByMajorNameAndCollege_School_Id(String majorName, Long schoolId);

    List<Major> findAllByCollegeIn(List<College> collegeList);
}
