package site.danjam.mate.user_service.domain.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.danjam.mate.user_service.domain.school.domain.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
}
