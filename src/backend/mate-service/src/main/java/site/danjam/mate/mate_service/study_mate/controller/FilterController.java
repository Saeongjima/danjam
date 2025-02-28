package site.danjam.mate.mate_service.study_mate.controller;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.global.common.dto.ApiResponseData;
import site.danjam.mate.mate_service.study_mate.dto.StudyMateFilterInputDTO;
import site.danjam.mate.mate_service.study_mate.dto.StudyMateFilterOutputDTO;
import site.danjam.mate.mate_service.study_mate.enums.AverageGrade;
import site.danjam.mate.mate_service.study_mate.enums.StudyTime;
import site.danjam.mate.mate_service.study_mate.enums.StudyType;
import site.danjam.mate.mate_service.study_mate.service.StudyMateProfileFilterService;

@RestController
@RequiredArgsConstructor
@RequestMapping("mate-service/study-mate")
public class FilterController {

    private final StudyMateProfileFilterService studyMateProfileFilterService;

    @MethodDescription(description = "스터디 메이트 프로필들을 조회한다.")
    public ResponseEntity<ApiResponseData<Set<StudyMateFilterOutputDTO>>> getStudyMateFilterOutputDTOList(
            @RequestHeader("userId") Long userId,
            @RequestHeader("role") String role,
            @RequestParam(value = "mbti", required = false) String mbti,
            @RequestParam(value = "gender", required = false) Set<Integer> gender,
            @RequestParam(value = "minBirthYear", required = false) String minBirthYear,
            @RequestParam(value = "maxBirthYear", required = false) String maxBirthYear,
            @RequestParam(value = "minEntryYear", required = false) String minEntryYear,
            @RequestParam(value = "maxEntryYear", required = false) String maxEntryYear,
            @RequestParam(value = "colleges", required = false) Set<String> colleges,
            @RequestParam(value = "preferredStudyTypes", required = false) Set<StudyType> preferredStudyTypes,
            @RequestParam(value = "preferredStudyTimes", required = false) Set<StudyTime> preferredStudyTimes,
            @RequestParam(value = "preferredAverageGrades", required = false) Set<AverageGrade> preferredAverageGrades

    ){
        StudyMateFilterInputDTO studyMateFilterInputDTO = StudyMateFilterInputDTO.builder()
                        .mbti(mbti)
                        .gender(gender)
                        .minBirthYear(minBirthYear)
                        .maxBirthYear(maxBirthYear)
                        .minEntryYear(minEntryYear)
                        .maxEntryYear(maxEntryYear)
                        .colleges(colleges)
                        .preferredStudyTypes(preferredStudyTypes)
                        .preferredStudyTimes(preferredStudyTimes)
                        .preferredAverageGrades(preferredAverageGrades)
                        .build();
        studyMateProfileFilterService.getStudyMateFilterOutputDTOList(userId, role);
        return null;
    }
}
