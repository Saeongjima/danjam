package site.danjam.mate.mate_service.study_mate.controller;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.global.common.dto.ApiResponseData;
import site.danjam.mate.mate_service.study_mate.dto.StudyMateFilterOutputDTO;
import site.danjam.mate.mate_service.study_mate.service.StudyMateProfileFilterService;

@RestController
@RequiredArgsConstructor
@RequestMapping("mate-service/study-mate")
public class FilterController {

    private final StudyMateProfileFilterService studyMateProfileFilterService;

    @MethodDescription(description = "스터디 메이트 프로필들을 조회한다.")
    public ResponseEntity<ApiResponseData<Set<StudyMateFilterOutputDTO>>> getStudyMateFilterOutputDTOList(
            @RequestHeader("userId") Long userId,
            @RequestHeader("role") String role
    ){
        studyMateProfileFilterService.getStudyMateFilterOutputDTOList(userId, role);
        return null;
    }
}
