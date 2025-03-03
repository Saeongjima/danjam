package site.danjam.mate.user_service.domain.user.dto;

import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserFilterInputDTO {
    private Long requesterId;
    private List<Long> userIds;
    private String mbti;
    private Integer gender;
    private String minBirthYear;
    private String maxBirthYear;
    private Integer minEntryYear;
    private Integer maxEntryYear;
    private Set<String> colleges;
}
