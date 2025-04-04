package site.danjam.mate.user_service.domain.school.dto;

import lombok.Getter;
import site.danjam.mate.user_service.domain.school.domain.Major;

@Getter
public class MajorResponseDto {

    private String majorName;
    private String collegeName;

    public static MajorResponseDto from(Major major) {
        MajorResponseDto dto = new MajorResponseDto();
        dto.majorName = major.getMajorName();
        dto.collegeName = major.getCollege().getCollegeName();
        return dto;
    }
}
