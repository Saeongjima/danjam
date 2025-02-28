package site.danjam.mate.mate_service.study_mate.service;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.danjam.mate.mate_service.study_mate.dto.StudyMateFilterOutputDTO;
import site.danjam.mate.mate_service.study_mate.repository.StudyMateProfileRepository;

@Service
@RequiredArgsConstructor
public class StudyMateProfileFilterService {

    private final StudyMateProfileRepository studyMateProfileRepository;

    public Set<StudyMateFilterOutputDTO> getStudyMateFilterOutputDTOList(Long userId, String role) {
        return null;
    }
}
