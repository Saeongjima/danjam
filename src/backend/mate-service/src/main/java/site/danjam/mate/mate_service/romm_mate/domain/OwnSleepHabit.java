package site.danjam.mate.mate_service.romm_mate.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import site.danjam.mate.mate_service.global.common.entity.BaseTimeEntity;
import site.danjam.mate.mate_service.romm_mate.enums.SleepHabit;

@Entity
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OwnSleepHabit extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "roomMateProfileDetailId", nullable = false)
    private RoomMateProfile roomMateProfile;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SleepHabit sleepHabit;
}