package site.danjam.mate.mate_service.study_mate.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.danjam.mate.mate_service.global.util.RequiredAuthUser;
import site.danjam.mate.mate_service.study_mate.domain.StudyMateProfile;
import site.danjam.mate.mate_service.study_mate.dto.StudyMateFilterInputDTO;
import site.danjam.mate.mate_service.study_mate.dto.StudyMateFilterOutputDTO;
import site.danjam.mate.mate_service.study_mate.repository.StudyMateProfileRepository;

@Service
@RequiredArgsConstructor
public class StudyMateFilterService {

    private final StudyMateProfileRepository studyMateProfileRepository;

    @RequiredAuthUser
    public Set<StudyMateFilterOutputDTO> getStudyMateFilterOutputDTOList(StudyMateFilterInputDTO studyMateFilterInputDTO, Long userId, String role) {
        List<StudyMateProfile> profilesByFilters = studyMateProfileRepository.findProfilesByFilters(studyMateFilterInputDTO);
        List<Long> userIds = profilesByFilters.stream()
                .map(StudyMateProfile::getUserId)
                .collect(Collectors.toList());
        for(StudyMateProfile studyMateProfile: profilesByFilters){
            System.out.println("studyMateProfile.getId() = " + studyMateProfile.getId());
            System.out.println("studyMateProfile.getUserId() = " + studyMateProfile.getUserId());
            System.out.println("studyMateProfile.getStudyTypes() = " + studyMateProfile.getPreferredStudyTypes().stream().map(preferredStudyType -> preferredStudyType.getStudyType().toString()));
            System.out.println("studyMateProfile.getStudyTime() = " + studyMateProfile.getStudyTime());
            System.out.println("studyMateProfile.getAverageGrade() = " + studyMateProfile.getAverageGrade());
            System.out.println("---------------------------------------------------------");
        }
        return null;
    }
}
