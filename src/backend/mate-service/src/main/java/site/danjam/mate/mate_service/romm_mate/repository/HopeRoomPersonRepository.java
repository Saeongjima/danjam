package site.danjam.mate.mate_service.romm_mate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.danjam.mate.mate_service.romm_mate.domain.HopeRoomPerson;

public interface HopeRoomPersonRepository extends JpaRepository<HopeRoomPerson, Long> {
    void deleteAllByRoomMateProfileId(Long id);
}
