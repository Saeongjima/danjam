package site.danjam.mate.mate_service.service;

import site.danjam.mate.mate_service.enums.MateType;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;

public interface MateProfileService {

    @MethodDescription(description = "메이트 프로필을 저장하는 메서드")
    void createMateProfile(Object dto, String username);

    @MethodDescription(description = "MateProfileServiceRegistry에서 각 MateType별로 해당하는 Service 구현체를 반환하기 위해 구별하는 용도로 사용")
    MateType getMateType(); // 각 구현체의 MateType 반환

    @MethodDescription(description = "Object의 타입이 해당 mateProfile의 dto가 맞는지 확인하는 메서드")
    boolean isValidInputDTO(Object inputDTO);
}
