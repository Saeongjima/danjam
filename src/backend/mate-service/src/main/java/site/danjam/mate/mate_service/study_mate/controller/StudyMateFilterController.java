package site.danjam.mate.mate_service.study_mate.controller;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import site.danjam.mate.mate_service.study_mate.service.StudyMateFilterService;

@RestController
@RequiredArgsConstructor
@RequestMapping("mate-service/api/studymate")
public class StudyMateFilterController {

    private final StudyMateFilterService studyMateFilterService;


    //todo - pagination 적용
    @MethodDescription(description = "스터디 메이트 프로필들을 조회한다.")
    @GetMapping("/profiles")
    public ResponseEntity<ApiResponseData<List<StudyMateFilterOutputDTO>>> getStudyMateFilterOutputDTOList(
            @RequestHeader("userId") Long userId,
            @RequestHeader("role") String role,
            @RequestParam(value = "mbti", required = false) String mbti,
            @RequestParam(value = "gender", required = false) List<Integer> gender,
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
        return ResponseEntity.ok(ApiResponseData.of(studyMateFilterService.getStudyMateFilterOutputDTOList(studyMateFilterInputDTO,userId, role),"성공적으로 스터디 메이트 프로필이 필터링 되었습니다."));

    }
}
