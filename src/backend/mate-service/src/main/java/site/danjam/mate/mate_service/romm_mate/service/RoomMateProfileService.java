package site.danjam.mate.mate_service.romm_mate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.danjam.mate.mate_service.global.exception.AccessDeniedException;
import site.danjam.mate.mate_service.global.exception.AlreadyProfileExistException;
import site.danjam.mate.mate_service.global.exception.CanNotFindResourceException;
import site.danjam.mate.mate_service.global.exception.ValidationExcepiton;
import site.danjam.mate.mate_service.mate.enums.MateType;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.global.exception.BaseException;
import site.danjam.mate.mate_service.global.exception.Code;
import site.danjam.mate.mate_service.romm_mate.domain.HopeDormitory;
import site.danjam.mate.mate_service.romm_mate.domain.HopeRoomPerson;
import site.danjam.mate.mate_service.romm_mate.domain.OwnSleepHabit;
import site.danjam.mate.mate_service.romm_mate.domain.RoomMateProfile;
import site.danjam.mate.mate_service.romm_mate.dto.RoomMateProfileDTO;
import site.danjam.mate.mate_service.romm_mate.dto.RoomMateProfileInputDTO;
import site.danjam.mate.mate_service.romm_mate.enums.SleepHabit;
import site.danjam.mate.mate_service.romm_mate.repository.HopeDormitoryRepository;
import site.danjam.mate.mate_service.romm_mate.repository.HopeRoomPersonRepository;
import site.danjam.mate.mate_service.romm_mate.repository.OwnSleepHabitRepository;
import site.danjam.mate.mate_service.romm_mate.repository.RoomMateProfileRepository;
import site.danjam.mate.mate_service.mate.service.MateProfileService;
import site.danjam.mate.mate_service.global.utils.AuthUtil;
import site.danjam.mate.mate_service.utils.DataConvert;
import site.danjam.mate.mate_service.utils.ValidationUtil;

@Service
@RequiredArgsConstructor
public class RoomMateProfileService implements MateProfileService {

    private final RoomMateProfileRepository roomMateProfileRepository;
    private final HopeDormitoryRepository hopeDormitoryRepository;
    private final HopeRoomPersonRepository hopeRoomPersonRepository;
    private final OwnSleepHabitRepository ownSleepHabitRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void createMateProfile(Object inputDTO, String username, String role){

        // 요청 권한을 확인
        if(!AuthUtil.checkAuthUser(role)){
            throw new AccessDeniedException();
        }

        Long userId=1L;//todo - openfeign을 이용해서 suerId조회해야함.
        // 이미 해당 프로필이 있는지 확인
        if(roomMateProfileRepository.findByUserId(userId).isPresent()){
            throw new AlreadyProfileExistException();
        }

        // inputDTO의 타입 확인 및 Validation 체크
        String validationMessage = ValidationUtil.validateInput(inputDTO, RoomMateProfileInputDTO.class);
        if(validationMessage!=null){
            throw new ValidationExcepiton(Code.VALIDATION_ERROR.getMessage()+" : "+validationMessage) ;
        }

        // ObjectMapper를 사용해 타입 변환
        RoomMateProfileInputDTO roomMateProfileInputDTO = objectMapper.convertValue(inputDTO, RoomMateProfileInputDTO.class);

        // RoomMAteProfileInputDTO를 RoomMateProfile로 변환하여 저장
        RoomMateProfile roomMateProfile = createBuildRoomMateProfile(roomMateProfileInputDTO, userId);
        roomMateProfileRepository.save(roomMateProfile);

        // HopeDormitories 저장
        saveHopeDormitories(roomMateProfileInputDTO.getHopeDormitories(),roomMateProfile);

        // HopeRoomPersons 저장
        saveHopeRoomPersons(roomMateProfileInputDTO.getHopeRoomPersons(),roomMateProfile);

        // OwnSleepHabits 저장
        saveOwnSleepHabits(roomMateProfileInputDTO.getSleepHabits(),roomMateProfile);
    }

    @Override
    public RoomMateProfileDTO getMateProfile(String username, String role) {
        // 요청 권한을 확인
        if(!AuthUtil.checkAuthUser(role)){
            throw new AccessDeniedException();
        }
        Long userId = 1L; //todo - openfeign을 이용해서 suerId조회해야함.
        // 유저의 메이트 프로필이 있는지 확인
        RoomMateProfile roomMateProfile = roomMateProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new CanNotFindResourceException(Code.CAN_NOT_FIND_RESOURCE.getMessage() + " 해당 프로필을 찾을 수 없습니다."));

        return createBuildRoomMateProfileDTO(roomMateProfile);
    }

    @Override
    @Transactional
    public void updateMateProfile(Object inputDTO, String username, String role, Long mateProfileId){
        /**
         * 1. 사전작업 : 권한/유효성 검증, 타입 변환
         */
        // 요청 권한을 확인
        if(!AuthUtil.checkAuthUser(role)){
            throw new AccessDeniedException();
        }

        RoomMateProfile roomMateProfile = roomMateProfileRepository.findById(mateProfileId)
                .orElseThrow(()-> new CanNotFindResourceException(Code.CAN_NOT_FIND_RESOURCE.getMessage() + " 해당 프로필을 찾을 수 없습니다."));

        Long userId = 1L;//todo - openfeign을 이용해서 suerId조회해야함.
        // 본인 프로필이 맞는지 검증
        if(!roomMateProfile.getUserId().equals(userId)){
            throw new AccessDeniedException();
        }

        // inputDTO의 타입 확인 및 Validation 체크
        String validationMessage = ValidationUtil.validateInput(inputDTO, RoomMateProfileInputDTO.class);
        if(validationMessage!=null){
            throw new ValidationExcepiton(Code.VALIDATION_ERROR.getMessage()+" : "+validationMessage) ;
        }

        // ObjectMapper를 사용해 타입 변환
        RoomMateProfileInputDTO roomMateProfileInputDTO = objectMapper.convertValue(inputDTO, RoomMateProfileInputDTO.class);

        /**
         * 2. 룸메이트 프로필 수정
         */
        roomMateProfile.updateMateProfile(roomMateProfileInputDTO);

        /**
         * 3. 기존 데이터 삭제 후 저장
         */
        hopeRoomPersonRepository.deleteAllByRoomMateProfileId(roomMateProfile.getId());
        hopeDormitoryRepository.deleteAllByRoomMateProfileId(roomMateProfile.getId());
        ownSleepHabitRepository.deleteAllByRoomMateProfileId(roomMateProfile.getId());
        saveHopeDormitories(roomMateProfileInputDTO.getHopeDormitories(),roomMateProfile);
        saveHopeRoomPersons(roomMateProfileInputDTO.getHopeRoomPersons(),roomMateProfile);
        saveOwnSleepHabits(roomMateProfileInputDTO.getSleepHabits(),roomMateProfile);

    }

    @MethodDescription(description = "빌더 패턴을 통해 RoomMateProfile을 반환 합니다. 메이트 프로필 생성할 때 사용합니다. ")
    private RoomMateProfile createBuildRoomMateProfile(RoomMateProfileInputDTO roomMateProfileInputDTO, Long userId) {
        return RoomMateProfile.builder()
                .userId(userId)
                .mateType(MateType.ROOMMATE)
                .isSmoking(roomMateProfileInputDTO.getIsSmoking())
                .hotLevel(roomMateProfileInputDTO.getHotLevel())
                .coldLevel(roomMateProfileInputDTO.getColdLevel())
                .activityTime(roomMateProfileInputDTO.getActivityTime())
                .cleanPeriod(roomMateProfileInputDTO.getCleanPeriod())
                .showerTime(roomMateProfileInputDTO.getShowerTime())
                .build();
    }

    @MethodDescription(description = "희망기숙사들을 저장합니다. 메이트 프로필 생성할 때 사용합니다. ")
    private void saveHopeDormitories(Set<String> hopeDormitories, RoomMateProfile roomMateProfile) {
        //HopeDormitory를 저장
        for (String hopeDormitory : hopeDormitories) {
            HopeDormitory hopeDormitoryEntity = HopeDormitory.builder()
                    .roomMateProfile(roomMateProfile)
                    .hopeDormitory(hopeDormitory)
                    .build();
            hopeDormitoryRepository.save(hopeDormitoryEntity);
        }
    }

    @MethodDescription(description = "희망 인원을 저장합니다. 메이트 프로필 생성할 때 사용합니다. ")
    private void saveHopeRoomPersons(Set<Integer> hopeRoomPersons, RoomMateProfile roomMateProfile) {
        for (Integer hopeRoomPerson : hopeRoomPersons) {
            HopeRoomPerson hopeRoomPersonEntity = HopeRoomPerson.builder()
                    .roomMateProfile(roomMateProfile)
                    .hopeRoomPerson(hopeRoomPerson)
                    .build();
            hopeRoomPersonRepository.save(hopeRoomPersonEntity);
        }
    }

    @MethodDescription(description = "가지고 있는 수면습관을 저장합니다. 메이트 프로필 생성할 때 사용합니다. ")
    private void saveOwnSleepHabits(Set<SleepHabit> sleepHabits, RoomMateProfile roomMateProfile) {
        //list에 NO_MATTER가 포함되어 있지 않으면 저장
        if (!sleepHabits.contains(SleepHabit.NO_MATTER)) {
            for(SleepHabit sleepHabit : sleepHabits) {
                OwnSleepHabit ownSleepHabitEntity = OwnSleepHabit.builder()
                        .roomMateProfile(roomMateProfile)
                        .sleepHabit(sleepHabit)
                        .build();
                ownSleepHabitRepository.save(ownSleepHabitEntity);
            }
        }
    }

    @MethodDescription(description = "빌더 패턴을 통해 RoomMateProfile를 인자로 받아 RoomMateProfileDTO를 반환합니다. 메이트 프로필을 조회할 때 사용합니다. " )
    private RoomMateProfileDTO createBuildRoomMateProfileDTO(RoomMateProfile roomMateProfile){
        return RoomMateProfileDTO.builder()
                .profileId(roomMateProfile.getId())
                .isSmoking(roomMateProfile.getIsSmoking())
                .hotLevel(roomMateProfile.getHotLevel().toString())
                .coldLevel(roomMateProfile.getColdLevel().toString())
                .activityTime(roomMateProfile.getActivityTime().toString())
                .cleanPeriod(roomMateProfile.getCleanPeriod().toString())
                .showerTime(roomMateProfile.getShowerTime().toString())
                .hopeRoomPersons(roomMateProfile.getHopeRoomPersonValues())
                .hopeDormitories(roomMateProfile.getHopeDormitoryNames())
                .ownSleepHabits(roomMateProfile.getOwnSleepHabitValues())
                .build();
    }

    @Override
    public MateType getMateType(){
        return MateType.ROOMMATE;
    }

}
