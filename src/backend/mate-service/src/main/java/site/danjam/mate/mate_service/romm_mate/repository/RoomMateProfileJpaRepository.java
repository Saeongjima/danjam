package site.danjam.mate.mate_service.romm_mate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.romm_mate.domain.RoomMateProfile;

public interface RoomMateProfileJpaRepository extends JpaRepository<RoomMateProfile, Long> {

    @MethodDescription(description = "userId로 룸메이트 프로필 조회 메서드")
    Optional<RoomMateProfile> findByUserId(Long userId);
}
