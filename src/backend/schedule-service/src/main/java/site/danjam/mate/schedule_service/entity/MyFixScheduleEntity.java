package site.danjam.mate.schedule_service.entity;


import jakarta.persistence.*;
import lombok.Data;
import site.danjam.mate.schedule_service.global.enums.DayOfWeek;

import java.time.LocalTime;

@Entity
@Data
@Table(name = "myfixschedule")
public class MyFixScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private Long userId;
}