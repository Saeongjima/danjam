package site.danjam.mate.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.danjam.mate.schedule.entity.MyScheduleEntity;

public interface MyScheduleRepository extends JpaRepository<MyScheduleEntity, Long> {

}
