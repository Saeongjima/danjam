package site.danjam.mate.user_service.domain.school.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.danjam.mate.user_service.domain.school.domain.School;
import site.danjam.mate.user_service.domain.school.dto.SchoolDto;
import site.danjam.mate.user_service.domain.school.repository.SchoolRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public List<SchoolDto> getAllSchools() {
        List<School> schoolList = schoolRepository.findAll();
        return schoolList.stream()
                .map(SchoolDto::from)
                .collect(Collectors.toList());
    }
}