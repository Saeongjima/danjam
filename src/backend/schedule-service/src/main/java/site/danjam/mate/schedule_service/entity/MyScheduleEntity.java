package site.danjam.mate.schedule_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter @Getter
@Table(name = "myschedule")
public class MyScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String memo;

    @Column(nullable = true)
    private LocalDateTime startDate;

    @Column(nullable = true)
    private LocalDateTime endDate;

    @Column(nullable = true)
    private LocalDateTime alarm;

//    @JoinColumn(name = "userId", nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private User user;

    @Column(nullable = false)
    private Long userId;


}
