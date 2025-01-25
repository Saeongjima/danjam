package site.danjam.mate.user_service.domain.mate.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MateProfileDTO {
    //    private RoomMateProfileDTO roomMateProfileDTO;
    private FoodMateProfileDTO foodMateProfileDTO;
    private StudyMateProfileDTO studyMateProfileDTO;
    private WalkMateProfileDTO walkMateProfileDTO;
//    private WorkoutMateProfileDTO workoutMateProfileDTO;
}