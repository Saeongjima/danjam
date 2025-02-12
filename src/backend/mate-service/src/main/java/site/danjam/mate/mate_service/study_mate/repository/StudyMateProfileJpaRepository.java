package site.danjam.mate.mate_service.study_mate.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.study_mate.domain.StudyMateProfile;
import site.danjam.mate.mate_service.study_mate.enums.AverageGrade;
import site.danjam.mate.mate_service.study_mate.enums.StudyTime;
import site.danjam.mate.mate_service.study_mate.enums.StudyType;

public interface StudyMateProfileJpaRepository extends JpaRepository<StudyMateProfile, Long> {
    Optional<StudyMateProfile> findByUserId(Long userId);

    boolean existsStudyMateProfileByUserId(Long userId);

    Optional<StudyMateProfile> findById(Long profileId);

    @Query("SELECT DISTINCT sp FROM StudyMateProfile sp " +
            "JOIN sp.preferredStudyTypes pst " +
            "WHERE(:userIds IS NULL OR sp.userId IN :userIds) "  +
            "AND (:preferredStudyTypes IS NULL OR pst.studyType IN :preferredStudyTypes)"+
            "AND (:preferredStudyTimes IS NULL OR sp.preferredStudyTime IN :preferredStudyTimes) " +
            "AND (:preferredAverageGrades IS NULL OR sp.averageGrade IN :preferredAverageGrades) ")
    List<StudyMateProfile> findProfilesByFilters(
            @Param("preferredStudyTypes") Set<StudyType> preferredStudyTypes,
            @Param("preferredStudyTimes") Set<StudyTime> preferredStudyTimes,
            @Param("preferredAverageGrades") Set<AverageGrade> preferredAverageGrades,
            @Param("userId") Set<Long> userIds
    );
}
