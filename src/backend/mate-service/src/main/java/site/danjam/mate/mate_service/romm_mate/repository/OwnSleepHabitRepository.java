package site.danjam.mate.mate_service.romm_mate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.danjam.mate.mate_service.romm_mate.domain.OwnSleepHabit;

public interface OwnSleepHabitRepository extends JpaRepository<OwnSleepHabit, Long> {
    void deleteAllByRoomMateProfileId(Long id);
}
