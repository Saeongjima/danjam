package site.danjam.mate.mate_service.global.feign.dto;

import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import site.danjam.mate.mate_service.study_mate.dto.StudyMateFilterInputDTO;

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

    public UserFilterInputDTO(StudyMateFilterInputDTO studyMateFilterInputDTO, Long requesterId, List<Long> userIds) {
        this.requesterId = requesterId;
        this.userIds = userIds;
        this.mbti = studyMateFilterInputDTO.getMbti();

        if(studyMateFilterInputDTO.getGender()!=null&&studyMateFilterInputDTO.getGender().size()==1){
            this.gender=studyMateFilterInputDTO.getGender().get(0);
        }
        else
            this.gender=null;

        this.minBirthYear = studyMateFilterInputDTO.getMinBirthYear();
        this.maxBirthYear = studyMateFilterInputDTO.getMaxBirthYear();
        this.minEntryYear = studyMateFilterInputDTO.getMinEntryYear();
        this.maxEntryYear = studyMateFilterInputDTO.getMaxEntryYear();
        this.colleges = studyMateFilterInputDTO.getColleges();
    }
}
