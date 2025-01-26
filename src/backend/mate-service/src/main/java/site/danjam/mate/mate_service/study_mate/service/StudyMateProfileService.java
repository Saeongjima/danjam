package site.danjam.mate.mate_service.study_mate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.danjam.mate.mate_service.global.exception.AccessDeniedException;
import site.danjam.mate.mate_service.global.exception.AlreadyProfileExistException;
import site.danjam.mate.mate_service.global.exception.CanNotFindResourceException;
import site.danjam.mate.mate_service.global.exception.ValidationExcepiton;
import site.danjam.mate.mate_service.mate.enums.MateType;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.global.exception.Code;
import site.danjam.mate.mate_service.global.utils.AuthUtil;
import site.danjam.mate.mate_service.mate.service.MateProfileService;
import site.danjam.mate.mate_service.study_mate.domain.StudyMateProfile;
import site.danjam.mate.mate_service.study_mate.dto.StudyMateProfileDTO;
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
            throw new AccessDeniedException();
        }

        // 이미 해당 프로필이 있는지 확인
        if(studyMateProfileRepository.findByUsername(username).isPresent()){
            throw new AlreadyProfileExistException();
        }

        // inputDTO의 타입 확인 및 Validation 체크
        String validationMessage = ValidationUtil.validateInput(inputDTO, StudyMateProfileInputDTO.class);
        if(validationMessage!=null){
            throw new ValidationExcepiton(Code.VALIDATION_ERROR.getMessage()+" : "+validationMessage);
        }

        // ObjectMapper를 사용해 타입 변환
        StudyMateProfileInputDTO studyMateProfileInputDTO = objectMapper.convertValue(inputDTO, StudyMateProfileInputDTO.class);

        studyMateProfileRepository.save(createBuildStudyMateProfile(studyMateProfileInputDTO,username));
    }


    @Override
    public StudyMateProfileDTO getMateProfile(String username, String role) {
        // 요청 권한을 확인
        if(!AuthUtil.checkAuthUser(role)){
            throw new AccessDeniedException();
        }

        // 유저의 메이트 프로필이 있는지 확인
        StudyMateProfile studyMateProfile = studyMateProfileRepository.findByUsername(username)
                .orElseThrow(() -> new CanNotFindResourceException(" 해당 프로필을 찾을 수 없습니다."));

        return createBuildStudyMateProfileDTO(studyMateProfile);
    }

    @Override
    public void updateMateProfile(Object inputDTO, String username, String role, Long mateProfileId) {

    }

    @Override
    public MateType getMateType() {
        return MateType.STUDYMATE;
    }

    @MethodDescription(description = "메이트 프로필 생성할 때 사용합니다. 빌더 패턴을 통해 StudyMateProfile을 반환 합니다.")
    private StudyMateProfile createBuildStudyMateProfile(StudyMateProfileInputDTO studyMateProfileInputDTO, String username){
        return StudyMateProfile.builder()
                .username(username)
                .mateType(MateType.STUDYMATE)
                .averageGrade(studyMateProfileInputDTO.getAverageGrade())
                .preferredStudyTime(studyMateProfileInputDTO.getPreferredStudyTime())
                .preferredStudyTypes(DataConvert.setToString(studyMateProfileInputDTO.getPreferredStudyTypes()))
                .userSubjects(DataConvert.setToString(studyMateProfileInputDTO.getUserSubjects()))
                .build();
    }

    @MethodDescription(description = "메이트 프로플일 조회할 때 사용합니다. 빌더 패턴을 통해 mateProfile를 인자로 받아 mateProfileDTO를 반환합니다.")
    private StudyMateProfileDTO createBuildStudyMateProfileDTO(StudyMateProfile studyMateProfile){
        return StudyMateProfileDTO.builder()
                .id(studyMateProfile.getId())
                .averageGrade(studyMateProfile.getAverageGrade().toString())
                .preferredStudyTime(studyMateProfile.getPreferredStudyTypes())
                .preferredStudyTypes(DataConvert.stringToSet(studyMateProfile.getPreferredStudyTypes()))
                .userSubjects(DataConvert.stringToSet(studyMateProfile.getUserSubjects()))
                .build();
    }

}
