package site.danjam.mate.mate_service.study_mate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.danjam.mate.mate_service.global.exception.AccessDeniedException;
import site.danjam.mate.mate_service.mate.exception.AlreadyProfileExistException;
import site.danjam.mate.mate_service.global.exception.ValidationExcepiton;
import site.danjam.mate.mate_service.global.util.RequiredAuthUser;
import site.danjam.mate.mate_service.mate.enums.MateType;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.global.exception.Code;
import site.danjam.mate.mate_service.mate.service.MateProfileService;
import site.danjam.mate.mate_service.study_mate.domain.PreferredStudyType;
import site.danjam.mate.mate_service.study_mate.domain.StudyMateProfile;
import site.danjam.mate.mate_service.study_mate.dto.StudyMateProfileDTO;
import site.danjam.mate.mate_service.study_mate.dto.StudyMateProfileInputDTO;
import site.danjam.mate.mate_service.study_mate.enums.StudyType;
import site.danjam.mate.mate_service.study_mate.repository.PreferredStudyTypeRepository;
import site.danjam.mate.mate_service.study_mate.repository.StudyMateProfileRepository;
import site.danjam.mate.mate_service.utils.DataConvert;
import site.danjam.mate.mate_service.utils.ValidationUtil;

@Service
@RequiredArgsConstructor
public class StudyMateProfileService implements MateProfileService {

    private final StudyMateProfileRepository studyMateProfileRepository;
    private final PreferredStudyTypeRepository preferredStudyTypeRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    @RequiredAuthUser
    public void createMateProfile(Object inputDTO, Long userId, String role) {

        // 이미 해당 프로필이 있는지 확인
        if(studyMateProfileRepository.existsStudyMateProfileByUserId(userId)){
            throw new AlreadyProfileExistException();
        }

        // inputDTO의 타입 확인 및 Validation 체크
        String validationMessage = ValidationUtil.validateInput(inputDTO, StudyMateProfileInputDTO.class);
        if(validationMessage!=null){
            throw new ValidationExcepiton(Code.VALIDATION_ERROR.getMessage()+" : "+validationMessage);
        }

        // ObjectMapper를 사용해 타입 변환
        StudyMateProfileInputDTO studyMateProfileInputDTO = objectMapper.convertValue(inputDTO, StudyMateProfileInputDTO.class);

        StudyMateProfile studyMateProfile = studyMateProfileRepository.save(createBuildStudyMateProfile(studyMateProfileInputDTO, userId));
        savePreferredStudyTypes(studyMateProfileInputDTO.getPreferredStudyTypes(),studyMateProfile);
    }


    @Override
    @RequiredAuthUser
    @Transactional(readOnly = true)
    public StudyMateProfileDTO getMateProfile(String username, String role) {

        Long userId = 1L; //todo - openfeign을 이용해서 suerId조회해야함.
        // 유저의 메이트 프로필이 있는지 확인
        StudyMateProfile studyMateProfile = studyMateProfileRepository.findByUserId(userId);

        return createBuildStudyMateProfileDTO(studyMateProfile);
    }

    @Override
    @RequiredAuthUser
    public void updateMateProfile(Object inputDTO, Long userId, String role, Long mateProfileId) {

        /**
         * 1. 사전작업 : 권한/유효성 검증, 타입 변환
         */
        StudyMateProfile studyMateProfile = studyMateProfileRepository.findById(mateProfileId);
        // 본인 프로필이 맞는지 검증
        if(!studyMateProfile.getUserId().equals(userId)){
            throw new AccessDeniedException();
        }

        // inputDTO의 타입 확인 및 Validation 체크
        String validationMessage = ValidationUtil.validateInput(inputDTO, StudyMateProfileInputDTO.class);
        if(validationMessage!=null){
            throw new ValidationExcepiton(Code.VALIDATION_ERROR.getMessage()+" : "+validationMessage) ;
        }

        // ObjectMapper를 사용해 타입 변환
        StudyMateProfileInputDTO studyMateProfileInputDTO = objectMapper.convertValue(inputDTO, StudyMateProfileInputDTO.class);

        /**
         * 2. 스터디메이트 프로필 수정
         */
        studyMateProfile.updateMateProfile(studyMateProfileInputDTO);

        /**
         * 3. 기존 데이터 삭제 후 저장
         */
        preferredStudyTypeRepository.deleteAllByStudyMateProfileId(studyMateProfile.getId());
        savePreferredStudyTypes(studyMateProfileInputDTO.getPreferredStudyTypes(),studyMateProfile);
    }

    @Override
    public MateType getMateType() {
        return MateType.STUDYMATE;
    }

    @MethodDescription(description = "메이트 프로필 생성할 때 사용합니다. 빌더 패턴을 통해 StudyMateProfile을 반환 합니다.")
    private StudyMateProfile createBuildStudyMateProfile(StudyMateProfileInputDTO studyMateProfileInputDTO, Long userId){
        return StudyMateProfile.builder()
                .userId(userId)
                .mateType(MateType.STUDYMATE)
                .averageGrade(studyMateProfileInputDTO.getAverageGrade())
                .studyTime(studyMateProfileInputDTO.getStudyTime())
                .userSubjects(DataConvert.setToString(studyMateProfileInputDTO.getUserSubjects()))
                .build();
    }

    @MethodDescription(description = "희망하는 스터디 타입을 저장할 때 사용합니다.")
    private void savePreferredStudyTypes(Set<StudyType> studyTypes, StudyMateProfile studyMateProfile){
        //HopeDormitory를 저장
        for (StudyType studyType : studyTypes) {
             PreferredStudyType preferredStudyType = PreferredStudyType.builder()
                     .studyMateProfile(studyMateProfile)
                     .studyType(studyType)
                     .build();
            preferredStudyTypeRepository.save(preferredStudyType);
        }
    }

    @MethodDescription(description = "메이트 프로플일 조회할 때 사용합니다. 빌더 패턴을 통해 mateProfile를 인자로 받아 mateProfileDTO를 반환합니다.")
    private StudyMateProfileDTO createBuildStudyMateProfileDTO(StudyMateProfile studyMateProfile){
        return StudyMateProfileDTO.builder()
                .id(studyMateProfile.getId())
                .averageGrade(studyMateProfile.getAverageGrade().toString())
                .preferredStudyTime(studyMateProfile.getStudyTime().toString())
                .preferredStudyTypes(studyMateProfile.getPreferredStudyTypesNames())
                .userSubjects(DataConvert.stringToSet(studyMateProfile.getUserSubjects()))
                .build();
    }

}
