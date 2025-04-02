package site.danjam.mate.mate_service.study_mate;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.danjam.mate.mate_service.study_mate.dto.StudyMateFilterInputDTO;
import site.danjam.mate.mate_service.study_mate.enums.StudyType;
import site.danjam.mate.mate_service.study_mate.repository.StudyMateProfileRepository;
import site.danjam.mate.mate_service.study_mate.service.StudyMateFilterService;

@SpringBootTest
public class FilterTest {

    @Autowired
    private StudyMateFilterService studyMateFilterService;

    @Autowired
    private StudyMateProfileRepository studyMateProfileRepository;

    @Test
    @DisplayName("PreferredStudyTypes에 따른 필터링 테스트")
    void testFilterByStudyTypes(){

        // Given
        StudyMateFilterInputDTO filterInput = StudyMateFilterInputDTO.builder()
                .preferredStudyTypes(Set.of(StudyType.MAJOR, StudyType.LANGUAGE))
                .build();

        // When
        studyMateFilterService.getStudyMateFilterOutputDTOList(filterInput, 1L, "AUTH_USER");

        // Then
    }
}
