package site.danjam.mate.user_service.domain.school.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.danjam.mate.user_service.domain.school.domain.College;
import site.danjam.mate.user_service.domain.school.domain.Major;
import site.danjam.mate.user_service.domain.school.dto.MajorResponseDto;
import site.danjam.mate.user_service.domain.school.repository.CollegeRepository;
import site.danjam.mate.user_service.domain.school.repository.MajorRepository;

@Service
@RequiredArgsConstructor
public class MajorService {

    private final MajorRepository majorRepository;
    private final CollegeRepository collegeRepository;

    public List<MajorResponseDto> getAllMajors(Long schoolId) {
        List<College> collegeList = collegeRepository.findAllBySchoolId(schoolId);
        List<Major> majorList = majorRepository.findAllByCollegeIn(collegeList);
        return majorList.stream()
                .map(MajorResponseDto::from)
                .toList();
    }
}
