package site.danjam.mate.mate_service.romm_mate.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import site.danjam.mate.mate_service.domain.MateProfile;
import site.danjam.mate.mate_service.enums.MateType;
import site.danjam.mate.mate_service.romm_mate.dto.RoomMateProfileInputDTO;
import site.danjam.mate.mate_service.romm_mate.enums.ActivityTime;
import site.danjam.mate.mate_service.romm_mate.enums.CleanPeriod;
import site.danjam.mate.mate_service.romm_mate.enums.Level;
import site.danjam.mate.mate_service.romm_mate.enums.ShowerTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RoomMateProfile extends MateProfile {

    @Column(nullable = false)
    private String isSmoking; // Y: 흡연, N: 비흡연

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Level hotLevel; // 많이타요, 적게타요, 상관없어요

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Level coldLevel; // 많이타요, 적게타요, 상관없어요

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityTime activityTime; // 아침형, 새벽형, 상관없어요

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CleanPeriod cleanPeriod; // 매일해요, 주마다해요, 달마다해요, 상관없어요

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShowerTime showerTime; // 10-20분, 20-30분, 30-40분, 40분이상

    @Column(nullable = false, length = 20)
    private String hopeRoomPersons; // ex) "[2,3,4]", "[1,2]"

    @Column(nullable = false, length = 100)
    private String hopeDormitories; // ex) "[정의관,자유관]"

    @Column(nullable = false)
    private String ownSleepHbits; // ex) "[SENSITIVE_TO_SOUND,SNORE]"

}
