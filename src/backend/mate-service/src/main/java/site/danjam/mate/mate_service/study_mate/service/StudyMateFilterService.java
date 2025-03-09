package site.danjam.mate.mate_service.study_mate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import site.danjam.mate.mate_service.global.common.dto.ApiResponseData;
import site.danjam.mate.mate_service.global.feign.UserInquiryFeignClient;
import site.danjam.mate.mate_service.global.feign.dto.UserFilterInputDTO;
import site.danjam.mate.mate_service.global.feign.dto.UserFilterOutputDTO;
import site.danjam.mate.mate_service.global.util.RequiredAuthUser;
import site.danjam.mate.mate_service.study_mate.domain.StudyMateProfile;
import site.danjam.mate.mate_service.study_mate.dto.StudyMateFilterInputDTO;
import site.danjam.mate.mate_service.study_mate.dto.StudyMateFilterOutputDTO;
import site.danjam.mate.mate_service.study_mate.repository.StudyMateProfileRepository;

@Service
@RequiredArgsConstructor
public class StudyMateFilterService {

    private final StudyMateProfileRepository studyMateProfileRepository;
    private final UserInquiryFeignClient userInquiryFeignClient;



    @RequiredAuthUser
    public List<StudyMateFilterOutputDTO> getStudyMateFilterOutputDTOList(StudyMateFilterInputDTO studyMateFilterInputDTO, Long userId, String role) {

        // 1. study-mate 프로필 필터링 진행
        List<StudyMateProfile> profilesByFilters = studyMateProfileRepository.findProfilesByFilters(studyMateFilterInputDTO);

        // 2. userIds만 뽑아냄.
        List<Long> userIds = profilesByFilters.stream()
                .map(StudyMateProfile::getUserId)
                .collect(Collectors.toList());

        // 3. User-service에 요청하여 필터링 진행
        UserFilterInputDTO userFilterInputDTO = new UserFilterInputDTO(studyMateFilterInputDTO, userId, userIds);
        List<UserFilterOutputDTO> userFilterOutputList = userInquiryFeignClient.getUserFilterOutputList(userFilterInputDTO).getBody().getData();

        // 4. userId 기준으로 매핑하기 위한 Map 생성
        Map<Long, UserFilterOutputDTO> userFilterOutputDTOMap = userFilterOutputList.stream()
                .collect(Collectors.toMap(UserFilterOutputDTO::getUserId, Function.identity()));

        Map<Long, StudyMateProfile> studyMateProfileMap = profilesByFilters.stream()
                .collect(Collectors.toMap(StudyMateProfile::getUserId, Function.identity()));

        // 5. StudyMateProfile과 UserFilterOutputDTO 결합
        List<StudyMateFilterOutputDTO> result = userFilterOutputDTOMap.values().stream()
                .map(userDto -> new StudyMateFilterOutputDTO(userDto, studyMateProfileMap.get(userDto.getUserId())))
                .collect(Collectors.toList());

        return result;
    }
}
