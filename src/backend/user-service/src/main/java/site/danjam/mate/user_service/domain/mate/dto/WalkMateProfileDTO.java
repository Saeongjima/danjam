package site.danjam.mate.user_service.domain.mate.dto;

import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WalkMateProfileDTO {
    private Long profileId;
    private String preferredWalkTime;
    private Set<String> preferredWalkTimeZones;
    private Set<String> preferredWalkIntensities;
}
