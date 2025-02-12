package site.danjam.mate.mate_service.study_mate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.mate_service.study_mate.domain.PreferredStudyType;

@Repository
@RequiredArgsConstructor
public class PreferredStudyTypeRepository{

    private final PreferredStudyTypeJpaRepository preferredStudyTypeJpaRepository;

    public void deleteAllByStudyMateProfileId(Long id){
        preferredStudyTypeJpaRepository.deleteAllByStudyMateProfileId(id);
    }

    public void save(PreferredStudyType preferredStudyType){
        preferredStudyTypeJpaRepository.save(preferredStudyType);
    }

}
