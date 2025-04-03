package site.danjam.mate.user_service.domain.school.dto;

import lombok.Getter;
import site.danjam.mate.user_service.domain.school.domain.School;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SchoolDto {
    private final Long schoolId;
    private final String schoolName;

    private SchoolDto(Long schoolId, String schoolName) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
    }

    public static SchoolDto from(School school) {
        return new SchoolDto(school.getId(), school.getKorName());
    }

    public static List<SchoolDto> fromList(List<School> schools) {
        return schools.stream()
                .map(SchoolDto::from)
                .collect(Collectors.toList());
    }
}