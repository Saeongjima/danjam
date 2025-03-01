package site.danjam.mate.schedule_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.danjam.mate.schedule_service.entity.MyFixScheduleEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyFixScheduleRepository extends JpaRepository<MyFixScheduleEntity, Long> {
    List<MyFixScheduleEntity> findAllByUserId(Long userId);
    Optional<MyFixScheduleEntity> findByUserId(Long userId);
}

