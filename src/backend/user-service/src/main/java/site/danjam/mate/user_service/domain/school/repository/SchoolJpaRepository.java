package site.danjam.mate.user_service.domain.school.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.danjam.mate.user_service.domain.school.domain.School;

@Repository
public interface SchoolJpaRepository extends JpaRepository<School, Long> {
    Optional<School> findById(Long id);
}
