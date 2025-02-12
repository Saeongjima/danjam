package site.danjam.mate.mate_service.workout_mate.dto;

import java.util.Set;

public class WorkoutMateProfileDTO {
    private Long id;
    private String preferredWorkoutIntensity;
    private Set<String> preferredWorkoutTypes;
    private Set<String> preferredWorkoutTimeZones;
    private Set<String> preferredWorkoutTimes;
}
