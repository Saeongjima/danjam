package site.danjam.mate.mate_service.study_mate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.study_mate.domain.StudyMateProfile;

public interface StudyMateProfileRepository extends JpaRepository<StudyMateProfile, Long> {
    @MethodDescription(description = "username로 스터디메이트 프로필 조회 메서드")
    Optional<StudyMateProfile> findByUsername(String userId);
}
