package site.danjam.mate.schedule_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.danjam.mate.schedule_service.entity.MyScheduleEntity;

import java.util.List;

public interface MyScheduleRepository extends JpaRepository<MyScheduleEntity, Long> {

    List<MyScheduleEntity> findByUserId(Long userId);
}

