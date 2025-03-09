package site.danjam.mate.mate_service.romm_mate.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.danjam.mate.mate_service.romm_mate.domain.HopeDormitory;

public interface HopeDormitoryJpaRepository extends JpaRepository<HopeDormitory,Long> {
    void deleteAllByRoomMateProfileId(Long id);
}
