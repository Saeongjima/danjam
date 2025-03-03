package site.danjam.mate.user_service.domain.user.dto;

import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserFilterInputDTO {
    private Long requesterId;
    private List<Long> userIds;
    private String mbti;
    private Integer gender;
    private String minBirthYear; //2자리
    private String maxBirthYear; //2자리
    private String minEntryYear; //2자리
    private String maxEntryYear; //2자리
    private Set<String> colleges;
}
