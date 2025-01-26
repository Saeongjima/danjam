package site.danjam.mate.user_service.domain.mate.dto;

import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FoodMateProfileDTO {
    private Long profileId;
    private Set<String> mateTime;
    private Set<String> hopeMenu;
    private String mealTime;
    private String diningManner;
    private String allergies;
}