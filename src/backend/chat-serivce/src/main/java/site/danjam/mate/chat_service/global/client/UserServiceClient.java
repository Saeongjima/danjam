package site.danjam.mate.chat_service.global.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.danjam.mate.chat_service.global.client.dto.UserGenderDTO;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/gender")
    UserGenderDTO getUserGender(@RequestParam("userId") Long userId, @RequestParam("friendName") String friendName);
}
