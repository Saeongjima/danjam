package site.danjam.mate.mate_service.romm_mate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.danjam.mate.mate_service.enums.MateType;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.global.exception.BaseException;
import site.danjam.mate.mate_service.global.exception.Code;
import site.danjam.mate.mate_service.romm_mate.domain.RoomMateProfile;
import site.danjam.mate.mate_service.romm_mate.dto.RoomMateProfileInputDTO;
import site.danjam.mate.mate_service.romm_mate.repository.HopeDormitoryRepository;
import site.danjam.mate.mate_service.romm_mate.repository.HopeRoomPersonRepository;
import site.danjam.mate.mate_service.romm_mate.repository.OwnSleepHabitRepository;
import site.danjam.mate.mate_service.romm_mate.repository.RoomMateProfileRepository;

@Service
@RequiredArgsConstructor
public class RoomMateProfileService {

    private final RoomMateProfileRepository roomMateProfileRepository;
    private final HopeDormitoryRepository hopeDormitoryRepository;
    private final OwnSleepHabitRepository ownSleepHabitRepository;
    private final HopeRoomPersonRepository hopeRoomPersonRepository;

    @MethodDescription(description = "룸메이트 프로필을 저장하는 메서드")
    @Transactional
    public void createRoomMateProfile(RoomMateProfileInputDTO roomMateProfileInputDTO, String userId){

        // 이미 프로필이 있는지 확인
        if(roomMateProfileRepository.findByUserId(userId).isPresent()){
            throw new BaseException(Code.ALREADY_PROFILE_EXIST);
        }

        // RoomMAteProfileInputDTO를 RoomMateProfile로 변환하여 저장
        RoomMateProfile roomMateProfile = new RoomMateProfile();
        roomMateProfile.setUserId(userId);
        roomMateProfile.setCleanPeriod(roomMateProfileInputDTO.getCleanPeriod());
        roomMateProfile.setActivityTime(roomMateProfileInputDTO.getActivityTime());
        roomMateProfile.setColdLevel(roomMateProfileInputDTO.getColdLevel());
        roomMateProfile.setHotLevel(roomMateProfileInputDTO.getHotLevel());
        roomMateProfile.setIsSmoking(roomMateProfileInputDTO.getIsSmoking());
        roomMateProfile.setShowerTime(roomMateProfileInputDTO.getShowerTime());
        roomMateProfile.setMateType(MateType.ROOMMATE);
        roomMateProfileRepository.save(roomMateProfile);

        // HopeDormitory 리스트를 저장


    }
}
