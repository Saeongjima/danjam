package site.danjam.mate.user_service.domain.user.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.danjam.mate.user_service.domain.user.dto.UserFilterInputDTO;
import site.danjam.mate.user_service.domain.user.dto.UserFilterOutputDTO;
import site.danjam.mate.user_service.domain.user.repository.UserRepository;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;

@Service
@RequiredArgsConstructor
public class UserFilterService {

    private final UserRepository userRepository;

    @MethodDescription(description = "전달받은 쿼리파라미터를 바탕으로 필터링")
    public List<UserFilterOutputDTO> getUserListByFilters(UserFilterInputDTO originUserFilterInputDTO) {
        UserFilterInputDTO userFilterInputDTO = fixFilterlingCondition(originUserFilterInputDTO);
        return userRepository.filterUsers(userFilterInputDTO);
    }

    @MethodDescription(description = "필터링 항목 값 수정(mbti, 생년월일등)")
    private UserFilterInputDTO fixFilterlingCondition(UserFilterInputDTO userFilterInputDTO) {

        String mbti = fixFilterlingMBTI(userFilterInputDTO.getMbti());
        String minBirthYear = convertoYear4Digits(userFilterInputDTO.getMinBirthYear());
        String maxBirthYear = convertoYear4Digits(userFilterInputDTO.getMaxBirthYear());
        String minEntryYear = convertoYear4Digits(userFilterInputDTO.getMinEntryYear());
        String maxEntryYear = convertoYear4Digits(userFilterInputDTO.getMaxEntryYear());

        return UserFilterInputDTO.builder()
                // 기존값 그대로
                .requesterId(userFilterInputDTO.getRequesterId())
                .userIds(userFilterInputDTO.getUserIds())
                .gender(userFilterInputDTO.getGender())
                .colleges(userFilterInputDTO.getColleges())
                //필터링에 맞게 변환
                .mbti(mbti)
                .minBirthYear(minBirthYear)
                .maxBirthYear(maxBirthYear)
                .minEntryYear(minEntryYear)
                .maxEntryYear(maxEntryYear)
                .build();

    }

    @MethodDescription(description = "MBTI 필터링 수정(반대되는 mbti를 모두 가지고 있을경우 조건 제거)")
    private String fixFilterlingMBTI(String originMbti) {


        if(originMbti==null || originMbti.isEmpty()){
            return null;
        }

        String mbti = originMbti;
        mbti = mbti.toLowerCase();
        mbti = mbti.chars().distinct().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.joining()); // 중복된 문자 제거
        if (mbti == null) return "";

        // i와e를 모두 포함하는 경우 모두 제거
        if (mbti.contains("i") && mbti.contains("e")) {
            mbti = mbti.replace("i", "").replace("e", "");
        }

        // n과s를 모두 포함하는 경우 모두 제거
        if (mbti.contains("n") && mbti.contains("s")) {
            mbti = mbti.replace("n", "").replace("s", "");
        }

        // t와f를 모두 포함하는 경우 모두 제거
        if (mbti.contains("t") && mbti.contains("f")) {
            mbti = mbti.replace("t", "").replace("f", "");
        }

        // j와p를 모두 포함하는 경우 모두 제거
        if (mbti.contains("j") && mbti.contains("p")) {
            mbti = mbti.replace("j", "").replace("p", "");
        }

        //MBTI 필터 값에 와일드카드(%)를 추가한 패턴 생성. ex) ej => %e%j%
        StringBuilder sb = new StringBuilder("%");
        for (char c : mbti.toCharArray()) {
            sb.append(c).append("%");
        }
        return sb.toString();
    }

    @MethodDescription(description = "필터링의 2자리수로 입력된 생년을 4자리수로 변경. 50년 이전은 20년대로, 50년 이후는 19년대로 변경")
    private String convertoYear4Digits(String num) {
        if(num==null || num.isEmpty()){
            return null;
        }
        if (Integer.parseInt(num) > 50)
            num = "19" + num;
        else
            num = "20" + num;
        return num;
    }



}
