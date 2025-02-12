package site.danjam.mate.mate_service.study_mate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.danjam.mate.mate_service.study_mate.domain.PreferredStudyType;

public interface PreferredStudyTypeJpaRepository extends JpaRepository<PreferredStudyType,Long> {
    void deleteAllByStudyMateProfileId(Long id);
}
