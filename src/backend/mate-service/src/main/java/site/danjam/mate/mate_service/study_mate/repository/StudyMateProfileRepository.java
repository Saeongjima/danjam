package site.danjam.mate.mate_service.study_mate.repository;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.global.exception.CanNotFindResourceException;
import site.danjam.mate.mate_service.global.exception.Code;
import site.danjam.mate.mate_service.study_mate.domain.StudyMateProfile;
import site.danjam.mate.mate_service.study_mate.dto.StudyMateFilterInputDTO;
import site.danjam.mate.mate_service.study_mate.enums.AverageGrade;
import site.danjam.mate.mate_service.study_mate.enums.StudyTime;
import site.danjam.mate.mate_service.study_mate.enums.StudyType;

@Repository
@RequiredArgsConstructor
public class StudyMateProfileRepository{

    private final StudyMateProfileJpaRepository studyMateProfileJpaRepository;

    @MethodDescription(description = "userId를 바탕으로 StudyMateProfile 찾기")
    public StudyMateProfile findByUserId(Long userId){
        return studyMateProfileJpaRepository.findByUserId(userId)
                .orElseThrow(()-> new CanNotFindResourceException(Code.CAN_NOT_FIND_RESOURCE.getMessage() + " 해당 프로필을 찾을 수 없습니다."));
    }

    @MethodDescription(description = "해당 프로필이 존재하는지 확인")
    public boolean existsStudyMateProfileByUserId(Long userId){
        return studyMateProfileJpaRepository.existsStudyMateProfileByUserId(userId);
    }

    @MethodDescription(description = "프로필 id로 프로필 조회")
    public StudyMateProfile findById(Long profileId){
        return studyMateProfileJpaRepository.findById(profileId)
                .orElseThrow(()-> new CanNotFindResourceException(Code.CAN_NOT_FIND_RESOURCE.getMessage() + " 해당 프로필을 찾을 수 없습니다."));
    }

    @MethodDescription(description = "프로필 저장")
    public StudyMateProfile save(StudyMateProfile studyMateProfile){
        return studyMateProfileJpaRepository.save(studyMateProfile);
    }

    @MethodDescription(description = "프로필 필터링")
    public List<StudyMateProfile> findProfilesByFilters(
            StudyMateFilterInputDTO studyMateFilterInputDTO
    ){
        return studyMateProfileJpaRepository.findProfilesByFilters(studyMateFilterInputDTO.getPreferredStudyTypes(), studyMateFilterInputDTO.getPreferredStudyTimes(), studyMateFilterInputDTO.getPreferredAverageGrades());
    }

    @MethodDescription(description = "유저Id리스트를 바탕으로 StudyMateProfile List를 조회합니다.")
    public List<StudyMateProfile> findByUserIds(List<Long> userIdsByUserService) {
        return studyMateProfileJpaRepository.findAllByUserIdIn(userIdsByUserService);
    }
}
