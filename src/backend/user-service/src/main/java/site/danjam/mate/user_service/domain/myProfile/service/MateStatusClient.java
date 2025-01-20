package site.danjam.mate.user_service.domain.myProfile.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import site.danjam.mate.user_service.domain.mate.dto.MateProfileDTO;

@FeignClient("mate-service")
public interface MateStatusClient {
    @RequestMapping("/mate")
    public MateProfileDTO getMateProfile(String userName);
}
