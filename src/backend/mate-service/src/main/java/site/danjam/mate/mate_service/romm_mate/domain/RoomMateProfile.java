package site.danjam.mate.mate_service.romm_mate.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import site.danjam.mate.mate_service.domain.MateProfile;
import site.danjam.mate.mate_service.romm_mate.enums.ActivityTime;
import site.danjam.mate.mate_service.romm_mate.enums.CleanPeriod;
import site.danjam.mate.mate_service.romm_mate.enums.Level;
import site.danjam.mate.mate_service.romm_mate.enums.ShowerTime;

@Entity
@Getter
@Setter
public class RoomMateProfile extends MateProfile {

    @Column(nullable = false)
    private Character isSmoking; // Y: 흡연, N: 비흡연

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Level hotLevel; // 많이타요, 적게타요, 상관없어요

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Level coldLevel; // 많이타요, 적게타요, 상관없어요

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ActivityTime activityTime; // 아침형, 새벽형, 상관없어요

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    CleanPeriod cleanPeriod; // 매일해요, 주마다해요, 달마다해요, 상관없어요

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ShowerTime showerTime; // 10-20분, 20-30분, 30-40분, 40분이상

}
