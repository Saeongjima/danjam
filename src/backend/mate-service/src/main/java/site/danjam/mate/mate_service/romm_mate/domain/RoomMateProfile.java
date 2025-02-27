package site.danjam.mate.mate_service.romm_mate.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.mate.domain.MateProfile;
import site.danjam.mate.mate_service.romm_mate.dto.RoomMateProfileInputDTO;
import site.danjam.mate.mate_service.romm_mate.enums.ActivityTime;
import site.danjam.mate.mate_service.romm_mate.enums.CleanPeriod;
import site.danjam.mate.mate_service.romm_mate.enums.Level;
import site.danjam.mate.mate_service.romm_mate.enums.ShowerTime;
import site.danjam.mate.mate_service.romm_mate.enums.SleepHabit;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RoomMateProfile extends MateProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

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
    ShowerTime showerTime; // _10_20분, _20_30분, _30_40분, _40분이상

    @OneToMany(mappedBy = "roomMateProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HopeDormitory> hopeDormitories;

    @OneToMany(mappedBy = "roomMateProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HopeRoomPerson> hopeRoomPersons;

    @OneToMany(mappedBy = "roomMateProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OwnSleepHabit> ownSleepHabits;

    @MethodDescription(description = "필드들을 업데이트할 때 사용합니다.")
    public void updateMateProfile(RoomMateProfileInputDTO roomMateProfileInputDTO){
        this.isSmoking= roomMateProfileInputDTO.getIsSmoking();
        this.hotLevel = roomMateProfileInputDTO.getHotLevel();
        this.coldLevel = roomMateProfileInputDTO.getColdLevel();
        this.activityTime = roomMateProfileInputDTO.getActivityTime();
        this.cleanPeriod = roomMateProfileInputDTO.getCleanPeriod();
        this.showerTime = roomMateProfileInputDTO.getShowerTime();
    }


    @MethodDescription(description = "희망 기숙사 이름만 추출하는 메서드. 프로필을 조회할 때 사용합니다.")
    public Set<String> getHopeDormitoryNames() {
        if (hopeDormitories == null) {
            return Set.of(); // null인 경우 빈 Set 반환
        }

        return hopeDormitories.stream()
                .map(HopeDormitory::getHopeDormitory) // 각 HopeDormitory의 hopeDormitory 값을 추출
                .collect(Collectors.toSet()); // 중복 제거 후 Set으로 변환
    }

    @MethodDescription(description = "희망 인원 값만 추출하는 메서드. 프로필을 조회할 때 사용합니다.")
    public Set<String> getHopeRoomPersonValues() {
        if (hopeRoomPersons == null) {
            return Set.of(); // null인 경우 빈 Set 반환
        }

        return hopeRoomPersons.stream()
                .map(hopeRoomPerson -> String.valueOf(hopeRoomPerson.getHopeRoomPerson())) // Integer를 String으로 변환
                .collect(Collectors.toSet()); // 중복 제거 후 Set으로 변환
    }

    @MethodDescription(description = "가지고 있는 습관 값만 추출하는 메서드. 프로필을 조회할 때 사용합니다.")
    public Set<String> getOwnSleepHabitValues() {
        if (ownSleepHabits == null) {
            return Set.of(); // null인 경우 빈 Set 반환
        }

        return ownSleepHabits.stream()
                .map(ownSleepHabit -> ownSleepHabit.getSleepHabit().toString()) // SleepHabit을 String으로 변환
                .collect(Collectors.toSet()); // 중복 제거 후 Set으로 변환
    }
}
