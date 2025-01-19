package site.danjam.mate.mate_service.utils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import site.danjam.mate.mate_service.domain.MateProfile;
import site.danjam.mate.mate_service.enums.MateType;
import site.danjam.mate.mate_service.service.MateProfileService;

@Component
public class MateProfileServiceRegistry {

    private final Map<MateType, MateProfileService> serviceMap;

    // 모든 MateProfileService 구현체를 주입받아 Map으로 변환합니다.
    public MateProfileServiceRegistry(List<MateProfileService> services) {
        this.serviceMap = services.stream()
                .collect(Collectors.toMap(
                        MateProfileService::getMateType,
                        Function.identity()
                ));
    }

    // MateType에 해당하는 MateProfileService를 반환합니다.
    public MateProfileService getService(MateType mateType) {
        MateProfileService service = serviceMap.get(mateType);
        if (service == null) {
            throw new IllegalArgumentException("해당 MateType과 일치하는 서비스가 존재하지 않습니다 : " + mateType);
        }
        return service;
    }
}
