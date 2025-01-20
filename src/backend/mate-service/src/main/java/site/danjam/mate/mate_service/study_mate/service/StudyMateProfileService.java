package site.danjam.mate.mate_service.study_mate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.danjam.mate.mate_service.enums.MateType;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.global.exception.BaseException;
import site.danjam.mate.mate_service.global.exception.Code;
import site.danjam.mate.mate_service.global.utils.AuthUtil;
import site.danjam.mate.mate_service.romm_mate.dto.RoomMateProfileDTO;
import site.danjam.mate.mate_service.service.MateProfileService;
import site.danjam.mate.mate_service.study_mate.domain.StudyMateProfile;
import site.danjam.mate.mate_service.study_mate.dto.StudyMateProfileInputDTO;
import site.danjam.mate.mate_service.study_mate.repository.StudyMateProfileRepository;
import site.danjam.mate.mate_service.utils.DataConvert;
import site.danjam.mate.mate_service.utils.ValidationUtil;

@Service
@RequiredArgsConstructor
public class StudyMateProfileService implements MateProfileService {

    private final StudyMateProfileRepository studyMateProfileRepository;
    private final ObjectMapper objectMapper;


    @Override
    public void createMateProfile(Object inputDTO, String username, String role) {

        // 요청 권한을 확인
        if(!AuthUtil.checkAuthUser(role)){
            throw new BaseException(Code.ACCESS_DENIED);
        }

        // 이미 해당 프로필이 있는지 확인
        if(studyMateProfileRepository.findByUsername(username).isPresent()){
            throw new BaseException(Code.ALREADY_PROFILE_EXIST);
        }

        // inputDTO의 타입 확인 및 Validation 체크
        String validationMessage = ValidationUtil.validateInput(inputDTO, StudyMateProfileInputDTO.class);
        if(validationMessage!=null){
            throw new BaseException(Code.VALIDATION_ERROR, Code.VALIDATION_ERROR.getMessage()+" : "+validationMessage);
        }

        // ObjectMapper를 사용해 타입 변환
        StudyMateProfileInputDTO studyMateProfileInputDTO = objectMapper.convertValue(inputDTO, StudyMateProfileInputDTO.class);

        studyMateProfileRepository.save(createBuildStudyMateProfile(studyMateProfileInputDTO,username));
    }

    @MethodDescription(description = "메이트 프로필 생성할 때 사용합니다. 빌더 패턴을 통해 StudyMateProfile을 반환 합니다.")
    private StudyMateProfile createBuildStudyMateProfile(StudyMateProfileInputDTO studyMateProfileInputDTO, String username){
        return StudyMateProfile.builder()
                .username(username)
                .mateType(MateType.STUDYMATE)
                .averageGrade(studyMateProfileInputDTO.getAverageGrade())
                .preferredStudyTime(studyMateProfileInputDTO.getPreferredStudyTime())
                .preferredStudyTypes(DataConvert.setToString(studyMateProfileInputDTO.getPreferredStudyTypes()))
                .userSubjects(DataConvert.setToString(studyMateProfileInputDTO.getSubjects()))
                .build();
    }

    @Override
    public RoomMateProfileDTO getMateProfile(String username, String role) {
        return null;
    }

    @Override
    public MateType getMateType() {
        return MateType.STUDYMATE;
    }

}
