package site.danjam.mate.mate_service.mate.service;

import site.danjam.mate.mate_service.mate.enums.MateType;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;

public interface MateProfileService {

    @MethodDescription(description = "메이트 프로필을 저장하는 메서드")
    void createMateProfile(Object inputDTO, String username, String role);

    @MethodDescription(description = "메이트 프로필을 조회하는 메서드. 해당 메이트 프로필DTO가 return 되어야한다.")
    Object getMateProfile(String username, String role);

    @MethodDescription(description = "MateProfileServiceRegistry에서 각 MateType별로 해당하는 Service 구현체를 반환하기 위해 구별하는 용도로 사용")
    MateType getMateType(); // 각 구현체의 MateType 반환

}
