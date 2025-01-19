package site.danjam.mate.mate_service.romm_mate.service;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.danjam.mate.mate_service.enums.MateType;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.global.common.dto.ApiResponseMessage;
import site.danjam.mate.mate_service.global.exception.BaseException;
import site.danjam.mate.mate_service.global.exception.Code;
import site.danjam.mate.mate_service.romm_mate.domain.RoomMateProfile;
import site.danjam.mate.mate_service.romm_mate.dto.RoomMateProfileInputDTO;
import site.danjam.mate.mate_service.romm_mate.repository.RoomMateProfileRepository;
import site.danjam.mate.mate_service.service.MateProfileService;
import site.danjam.mate.mate_service.utils.DataConvert;

@Service
@RequiredArgsConstructor
public class RoomMateProfileService implements MateProfileService {

    private final RoomMateProfileRepository roomMateProfileRepository;

    @Override
    @Transactional
    public void createMateProfile(Object inputDTO, String username){
        // inputDTO의 타입 확인 및 변환
        if(!isValidInputDTO(inputDTO)){
            throw new IllegalArgumentException("Invalid DTO type for ROOMMATE");
        }
        RoomMateProfileInputDTO roomMateProfileInputDTO = (RoomMateProfileInputDTO)inputDTO;


        // 이미 프로필이 있는지 확인
        if(roomMateProfileRepository.findByUserId(username).isPresent()){
            throw new BaseException(Code.ALREADY_PROFILE_EXIST);
        }

        // RoomMAteProfileInputDTO를 RoomMateProfile로 변환하여 저장
        RoomMateProfile roomMateProfile = createBuildRoomMateProfile(roomMateProfileInputDTO, username);
        roomMateProfileRepository.save(roomMateProfile);
    }


    @MethodDescription(description = "빌더 패턴을 통해 RoomMateProfile을 반환받습니다.")
    private RoomMateProfile createBuildRoomMateProfile(RoomMateProfileInputDTO roomMateProfileInputDTO, String userId) {
        return RoomMateProfile.builder()
                .userId(userId)
                .mateType(MateType.ROOMMATE)
                .isSmoking(roomMateProfileInputDTO.getIsSmoking())
                .hotLevel(roomMateProfileInputDTO.getHotLevel())
                .coldLevel(roomMateProfileInputDTO.getColdLevel())
                .activityTime(roomMateProfileInputDTO.getActivityTime())
                .cleanPeriod(roomMateProfileInputDTO.getCleanPeriod())
                .showerTime(roomMateProfileInputDTO.getShowerTime())
                .hopeRoomPersons(DataConvert.setToString(roomMateProfileInputDTO.getHopeRoomPersons()))
                .hopeDormitories(DataConvert.setToString(roomMateProfileInputDTO.getHopeDormitories()))
                .ownSleepHbits(DataConvert.setToString(roomMateProfileInputDTO.getSleepHabits()))
                .build();
    }

    @Override
    public MateType getMateType(){
        return MateType.ROOMMATE;
    }

    @Override
    public boolean isValidInputDTO(Object inputDTO){
        if (!(inputDTO instanceof RoomMateProfileInputDTO)) {
            return false;
        }
        return true;
    }

}
